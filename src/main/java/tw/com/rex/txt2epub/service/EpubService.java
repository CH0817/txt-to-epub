package tw.com.rex.txt2epub.service;

import com.adobe.epubcheck.tool.EpubChecker;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.xml.Container;
import tw.com.rex.txt2epub.utils.FileUtil;
import tw.com.rex.txt2epub.utils.XmlUtil;

import java.nio.file.Path;
import java.util.stream.IntStream;

public class EpubService {

    private final ConvertInfo convertInfo;

    public EpubService(ConvertInfo convertInfo) {
        this.convertInfo = convertInfo;
    }

    public void process() {
        IntStream.range(0, convertInfo.getBooks().length)
                 .forEach(i -> {
                     createContentXhtml(i);
                     createCover(i);
                     copyCssFiles(i);
                     createTableOfContents(i);
                     createNavigationDocuments(i);
                     createOpf(i);
                     createContainerXml(i);
                     createMineType(i);
                     convert(i);
                     removeTemp(i);
                     moveEpub(i);
                 });
    }

    private void createContentXhtml(int index) {
        new ContentXhtmlService(convertInfo, index).generate();
    }

    private void createCover(int index) {
        new CoverXhtmlService(convertInfo, index).generate();
    }

    private void copyCssFiles(int index) {
        new StyleFileService(convertInfo, index).copy();
    }

    private void createTableOfContents(int index) {
        new TableOfContentsService(convertInfo, index).generate();
    }

    private void createNavigationDocuments(int index) {
        new NavigationDocumentsXhtmlService(convertInfo, index).generate();
    }

    private void createOpf(int index) {
        new OpfService(convertInfo, index).generate();
    }

    private void createContainerXml(int index) {
        XmlUtil.convertToXmlFile(new Container(),
                                 convertInfo.getTempDirectories()[index].getMetaInfPath().resolve("container.xml"));
    }

    private void createMineType(int index) {
        FileUtil.write(convertInfo.getTempDirectories()[index]
                               .getBasePath()
                               .resolve("mimetype"), "application/epub+zip");
    }

    private void convert(int index) {
        String tempFilePath = convertInfo.getTempDirectories()[index].getBasePath().toAbsolutePath().toString();
        String[] args = {tempFilePath, "--mode", "exp", "--save"};
        int checkResult = new EpubChecker().run(args);
        if (0 != checkResult) {
            throw new RuntimeException("EPUB 轉換失敗");
        }
    }

    private void removeTemp(int index) {
        FileUtil.deleteAll(convertInfo.getTempDirectories()[index].getBasePath());
    }

    private void moveEpub(int index) {
        Path path = convertInfo.getTempDirectories()[index].getFinalFilePath();
        Path output = convertInfo.getOutput().resolve(path.getFileName());
        FileUtil.move(path, output);
    }

}
