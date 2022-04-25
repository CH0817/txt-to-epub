package tw.com.rex.txt2epub.model;

import lombok.AccessLevel;
import lombok.Getter;
import tw.com.rex.txt2epub.utils.FileUtil;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class TempDirectory implements Serializable {

    @Getter(AccessLevel.NONE)
    private final String bookName;
    private Path basePath;
    private Path metaInfPath;
    private Path itemPath;
    private Path imagePath;
    private Path stylePath;
    private Path xhtmlPath;

    public TempDirectory(String bookName) {
        this.bookName = bookName;
        initPaths();
        createDirectories();
    }

    private void initPaths() {
        this.basePath = Paths.get(System.getProperty("java.io.tmpdir"), bookName);
        this.metaInfPath = basePath.resolve("META-INF");
        this.itemPath = basePath.resolve("item");
        this.imagePath = itemPath.resolve("image");
        this.stylePath = itemPath.resolve("style");
        this.xhtmlPath = itemPath.resolve("xhtml");
    }

    private void createDirectories() {
        FileUtil.createDirectories(basePath, metaInfPath, itemPath, imagePath, xhtmlPath, stylePath);
    }

    public Path getFinalFilePath() {
        return getBasePath().getParent().resolve(bookName + ".epub");
    }

}
