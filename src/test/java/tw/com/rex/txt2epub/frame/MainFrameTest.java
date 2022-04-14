package tw.com.rex.txt2epub.frame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

public class MainFrameTest {

    private FrameFixture window;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() {
        MainFrame frame = GuiActionRunner.execute(MainFrame::new);
        window = new FrameFixture(frame);
        window.show();
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }

    @Test
    public void executeTxtToEpub() {
        // 驗證選擇 txt 後是否將路徑放入 label
        window.button("selectFileBtn").click();
        JFileChooserFixture chooser = window.fileChooser("chooser");
        String filePath = Paths.get("src/test/resources/曹賊.txt").toAbsolutePath().toString();
        chooser.selectFile(new File(filePath)).approve();
        window.label("selectedFilePath").requireText(filePath);
        // 驗證選擇輸出路徑後後是否將路徑放入 label
        window.button("selectOutputPathBtn").click();
        String outputPath = Paths.get("D:/Temp").toAbsolutePath().toString();
        chooser.selectFile(new File(outputPath)).approve();
        window.label("outputFilePath").requireText(outputPath);
    }

}