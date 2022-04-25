package tw.com.rex.txt2epub.service;

import lombok.AllArgsConstructor;
import tw.com.rex.txt2epub.model.TempDirectory;
import tw.com.rex.txt2epub.utils.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@AllArgsConstructor
public class CssFileService {

    private TempDirectory tempDirectory;
    private String typesettingCssFolder;

    public void copy() {
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
        try (InputStream input = classloader.getResourceAsStream("style/" + typesettingCssFolder + "/" + cssName)) {
            FileUtil.copy(input, cssPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("copy vertical book-style.css to " + cssPath + " failure!");
        }
    }

}
