package tw.com.rex.txt2epub.service.impl.base;

import com.adobe.epubcheck.tool.EpubChecker;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.CssClass;
import tw.com.rex.txt2epub.model.TempDirectory;
import tw.com.rex.txt2epub.model.epub.Container;
import tw.com.rex.txt2epub.service.*;
import tw.com.rex.txt2epub.utils.FileUtil;
import tw.com.rex.txt2epub.utils.XmlUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractEpubServiceImpl implements EpubService {

    private final Book book;
    private final Path outputPath;
    protected final TempDirectory tempDirectory;
    private Map<String, String> contentXhtmlMap;

    public AbstractEpubServiceImpl(Book book, Path outputPath) {
        this.book = book;
        this.outputPath = outputPath;
        this.tempDirectory = new TempDirectory(book.getName());
    }

    @Override
    public String process() throws Exception {
        CssClass css = createCssClass();
        createContentXhtml(css);
        createCover();
        copyCssFiles();
        createToc(css);
        createNavigationDocuments();
        createOpf();
        createContainerXml();
        createMineType();
        return covertToEpub();
    }

    private void createContentXhtml(CssClass css) {
        contentXhtmlMap = new ContentXhtmlService(book, css, tempDirectory.getXhtmlPath()).generate();
    }

    private void createCover() {
        FileUtil.copy(book.getCover(), tempDirectory.getImagePath().resolve(book.getCover().getFileName()));
        Path path = tempDirectory.getXhtmlPath().resolve("p-cover.xhtml");
        FileUtil.write(path, new CoverXhtmlService(book).generate());
    }

    private void copyCssFiles() {
        String[] cssNames = {"style-advance.css", "style-check.css", "style-reset.css", "style-standard.css"};
        for (String cssName : cssNames) {
            Path cssPath = tempDirectory.getStylePath().resolve(cssName).toAbsolutePath();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            try (InputStream input = classloader.getResourceAsStream("style/" + cssName)) {
                FileUtil.copy(input, cssPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("copy " + cssName + " to " + cssPath + " failure!");
            }
        }
        copyTypesettingCss();
    }

    private void copyTypesettingCss() {
        String cssName = "book-style.css";
        Path cssPath = tempDirectory.getStylePath().resolve(cssName).toAbsolutePath();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classloader.getResourceAsStream("style/" + getTypesettingCssFolder() + "/" + cssName)) {
            FileUtil.copy(input, cssPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("copy vertical book-style.css to " + cssPath + " failure!");
        }
    }

    /**
     * 產生目錄
     */
    private void createToc(CssClass css) {
        new TableOfContentsService(book, css, contentXhtmlMap, tempDirectory.getXhtmlPath()).generate();
    }

    private void createNavigationDocuments() {
        new NavigationDocumentsXhtmlService(book, contentXhtmlMap, tempDirectory.getItemPath()).generate();
    }

    private void createOpf() {
        new OpfService(book, tempDirectory.getItemPath(), contentXhtmlMap).generate();
    }

    private void createContainerXml() {
        XmlUtil.convertToXmlFile(new Container(), tempDirectory.getMetaInfPath().resolve("container.xml"));
    }

    private void createMineType() {
        FileUtil.write(tempDirectory.getBasePath().resolve("mimetype"), "application/epub+zip");
    }

    private String covertToEpub() throws Exception {
        String[] args = {tempDirectory.getBasePath().toAbsolutePath().toString(), "--mode", "exp", "--save"};
        int checkResult = new EpubChecker().run(args);
        removeTemp();
        if (0 != checkResult) {
            throw new Exception("EPUB 轉換失敗");
        }
        return moveEpub();
    }

    private void removeTemp() {
        FileUtil.deleteAll(tempDirectory.getBasePath());
    }

    private String moveEpub() {
        Path path = tempDirectory.getBasePath().getParent().resolve(book.getName() + ".epub");
        Path output = outputPath.resolve(path.getFileName());
        FileUtil.move(path, output);
        return output.toAbsolutePath().toString();
    }

    protected abstract CssClass createCssClass();

    protected abstract String getTypesettingCssFolder();

}
