package tw.com.rex.txt2epub.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.adobe.epubcheck.tool.EpubChecker;

import tw.com.rex.txt2epub.creator.BookCreator;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TempDirectory;
import tw.com.rex.txt2epub.model.css.DisplayStyle;
import tw.com.rex.txt2epub.model.xml.Container;
import tw.com.rex.txt2epub.utils.FileUtil;
import tw.com.rex.txt2epub.utils.XmlUtil;

public class EpubService {

    public void process(ConvertInfo convertInfo) {
        Book[] books = BookCreator.create(convertInfo);
        Stream.of(books)
                .forEach(book -> {
                    createContentXhtml(book, convertInfo.getStyle());
                    createCover(book);
                    copyCssFiles(book, convertInfo.getStyle());
                    createTableOfContents(book, convertInfo.getStyle());
                    createNavigationDocuments(book);
                    createOpf(book);
                    createContainerXml(book);
                    createMineType(book);
                    convert(book);
                    removeTemp(book);
                    moveEpub(book, convertInfo.getOutputPath());
                });
    }

    private void createContentXhtml(Book book, DisplayStyle displayStyle) {
        new ContentXhtmlService(displayStyle, book).generate();
    }

    private void createCover(Book book) {
        new CoverXhtmlService(book).generate();
    }

    private void copyCssFiles(Book book, DisplayStyle displayStyle) {
        new StyleFileService(displayStyle, book).copy();
    }

    private void createTableOfContents(Book book, DisplayStyle displayStyle) {
        new TableOfContentsService(displayStyle, book).generate();
    }

    private void createNavigationDocuments(Book book) {
        new NavigationDocumentsXhtmlService(book).generate();
    }

    private void createOpf(Book book) {
        new OpfService(book).generate();
    }

    private void createContainerXml(Book book) {
        XmlUtil.convertToXmlFile(new Container(),
                new TempDirectory(book.getName()).getMetaInfPath().resolve("container.xml"));
    }

    private void createMineType(Book book) {
        FileUtil.write(new TempDirectory(book.getName())
                .getBasePath()
                .resolve("mimetype"), "application/epub+zip");
    }

    private void convert(Book book) {
        String tempFilePath = new TempDirectory(book.getName()).getBasePath().toAbsolutePath().toString();
        String[] args = { tempFilePath, "--mode", "exp", "--save" };
        int checkResult = new EpubChecker().run(args);
        if (0 != checkResult) {
            throw new RuntimeException("EPUB 轉換失敗");
        }
    }

    private void removeTemp(Book book) {
        FileUtil.deleteAll(new TempDirectory(book.getName()).getBasePath());
    }

    private void moveEpub(Book book, String outputPath) {
        Path path = new TempDirectory(book.getName()).getFinalFilePath();
        Path output = Paths.get(outputPath).resolve(path.getFileName());
        FileUtil.move(path, output);
    }

}
