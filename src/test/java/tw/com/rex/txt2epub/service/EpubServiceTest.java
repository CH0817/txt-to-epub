package tw.com.rex.txt2epub.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.utils.TestUtil;
import tw.com.rex.txt2epub.utils.FileUtil;
import tw.com.rex.txt2epub.utils.ListUtil;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EpubServiceTest {

    private static final String OUTPUT_PATH = "D:/Temp/曹賊";

    @Test
    public void horizontalProcess() {
        MainFrame frame = TestUtil.createFrame(TypesettingEnum.HORIZONTAL, OUTPUT_PATH);
        ConvertInfo convertInfo = new ConvertInfo(frame);
        new EpubService(convertInfo).process();
        verifyResult();
    }

    @Test
    public void verticalProcess() {
        MainFrame frame = TestUtil.createFrame(TypesettingEnum.VERTICAL, OUTPUT_PATH);
        ConvertInfo convertInfo = new ConvertInfo(frame);
        new EpubService(convertInfo).process();
        verifyResult();
    }

    @Test
    public void processNoCover() {
        MainFrame frame = TestUtil.createFrame(TypesettingEnum.HORIZONTAL, OUTPUT_PATH);
        frame.setCoverPath(new JLabel());
        ConvertInfo convertInfo = new ConvertInfo(frame);
        new EpubService(convertInfo).process();
        verifyResult();
    }

    private void verifyResult() {
        String txtPath = Paths.get("src/test/resources/曹賊.txt").toAbsolutePath().toString();
        TxtHandlerService service = new TxtHandlerService(txtPath);
        List<TxtContent> txtContentList = service.getTxtContentList();
        int episodes = ListUtil.separateDataList(txtContentList, 100).size();
        for (int i = 0; i < episodes; i++) {
            StringBuilder epubName = new StringBuilder(String.valueOf(episodes));
            while (epubName.length() < 2) {
                epubName.insert(0, "0");
            }
            epubName.insert(0, "曹賊-");
            epubName.append(".epub");
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH).resolve(epubName.toString())));
        }
    }

    @AfterAll
    public static void cleanUp() {
        FileUtil.deleteAll(Paths.get(OUTPUT_PATH));
    }

}
