package tw.com.rex.txt2epub.service;

import com.adobe.epubcheck.tool.EpubChecker;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.xml.Container;
import tw.com.rex.txt2epub.utils.FileUtil;
import tw.com.rex.txt2epub.utils.XmlUtil;

import java.nio.file.Path;

public class EpubService {

    private final ConvertInfo convertInfo;

    public EpubService(ConvertInfo convertInfo) {
        this.convertInfo = convertInfo;
    }

    public void process() throws Exception {
        createContentXhtml();
        createCover();
        copyCssFiles();
        createTableOfContents();
        createNavigationDocuments();
        createOpf();
        createContainerXml();
        createMineType();
        convert();
        removeTemp();
        moveEpub();
    }

    private void createContentXhtml() {
        new ContentXhtmlService(convertInfo).generate();
    }

    private void createCover() {
        new CoverXhtmlService(convertInfo).generate();
    }

    private void copyCssFiles() {
        new StyleFileService(convertInfo).copy();
    }

    private void createTableOfContents() {
        new TableOfContentsService(convertInfo).generate();
    }

    private void createNavigationDocuments() {
        new NavigationDocumentsXhtmlService(convertInfo).generate();
    }

    private void createOpf() {
        new OpfService(convertInfo).generate();
    }

    private void createContainerXml() {
        XmlUtil.convertToXmlFile(new Container(),
                                 convertInfo.getTempDirectory().getMetaInfPath().resolve("container.xml"));
    }

    private void createMineType() {
        FileUtil.write(convertInfo.getTempDirectory().getBasePath().resolve("mimetype"), "application/epub+zip");
    }

    private void convert() throws Exception {
        String tempFilePath = convertInfo.getTempDirectory().getBasePath().toAbsolutePath().toString();
        String[] args = {tempFilePath, "--mode", "exp", "--save"};
        int checkResult = new EpubChecker().run(args);
        if (0 != checkResult) {
            throw new Exception("EPUB 轉換失敗");
        }
    }

    private void removeTemp() {
        FileUtil.deleteAll(convertInfo.getTempDirectory().getBasePath());
    }

    private void moveEpub() {
        Path path = convertInfo.getTempDirectory().getFinalFilePath();
        Path output = convertInfo.getOutput().resolve(path.getFileName());
        FileUtil.move(path, output);
    }

}
