package tw.com.rex.txt2epub.service.impl.base;

import com.adobe.epubcheck.tool.EpubChecker;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.CssClass;
import tw.com.rex.txt2epub.model.epub.Container;
import tw.com.rex.txt2epub.service.*;
import tw.com.rex.txt2epub.utils.FileUtil;
import tw.com.rex.txt2epub.utils.XmlUtil;

import java.nio.file.Path;

public abstract class AbstractEpubServiceImpl implements EpubService {

    private final ConvertInfo convertInfo;

    public AbstractEpubServiceImpl(ConvertInfo convertInfo) {
        this.convertInfo = convertInfo;
    }

    @Override
    public String process() throws Exception {
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
        return moveEpub();
    }

    private void createContentXhtml() {
        // new ContentXhtmlService(book, createCssClass(), tempDirectory.getXhtmlPath()).generate();
        new ContentXhtmlService(convertInfo, createCssClass()).generate();
    }

    private void createCover() {
        // new CoverXhtmlService(book, tempDirectory.getXhtmlPath().resolve("p-cover.xhtml")).generate();
        new CoverXhtmlService(convertInfo).generate();
    }

    private void copyCssFiles() {
        // new CssFileService(tempDirectory, getTypesettingCssFolder()).copy();
        new CssFileService(convertInfo.getTempDirectory(), getTypesettingCssFolder()).copy();
    }

    private void createTableOfContents() {
        // new TableOfContentsService(book, createCssClass(), tempDirectory.getXhtmlPath()).generate();
        new TableOfContentsService(convertInfo, createCssClass()).generate();
    }

    private void createNavigationDocuments() {
        // new NavigationDocumentsXhtmlService(book, tempDirectory.getItemPath()).generate();
        new NavigationDocumentsXhtmlService(convertInfo).generate();
    }

    private void createOpf() {
        // new OpfService(book, tempDirectory.getItemPath()).generate();
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

    private String moveEpub() {
        Path path = convertInfo.getTempDirectory().getFinalFilePath();
        Path output = convertInfo.getOutput().resolve(path.getFileName());
        FileUtil.move(path, output);
        return output.toAbsolutePath().toString();
    }

    protected abstract CssClass createCssClass();

    protected abstract String getTypesettingCssFolder();

}
