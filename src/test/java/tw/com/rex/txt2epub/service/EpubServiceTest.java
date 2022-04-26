package tw.com.rex.txt2epub.service;

import org.junit.After;
import org.junit.Test;
import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.util.EpubServiceTestUtil;
import tw.com.rex.txt2epub.utils.FileUtil;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class EpubServiceTest {

    private static final String OUTPUT_PATH = "D:/Temp/曹賊";


    @Test
    public void horizontalProcess() throws Exception {
        MainFrame frame = EpubServiceTestUtil.createFrame(TypesettingEnum.HORIZONTAL, OUTPUT_PATH);
        ConvertInfo convertInfo = new ConvertInfo(frame);
        new EpubService(convertInfo).process();
        verifyResult();
    }

    @Test
    public void verticalProcess() throws Exception {
        MainFrame frame = EpubServiceTestUtil.createFrame(TypesettingEnum.VERTICAL, OUTPUT_PATH);
        ConvertInfo convertInfo = new ConvertInfo(frame);
        new EpubService(convertInfo).process();
        verifyResult();
    }

    @Test
    public void processNoCover() throws Exception {
        MainFrame frame = EpubServiceTestUtil.createFrame(TypesettingEnum.HORIZONTAL, OUTPUT_PATH);
        frame.setCoverPath(new JLabel());
        ConvertInfo convertInfo = new ConvertInfo(frame);
        new EpubService(convertInfo).process();
        verifyResult();
    }

    private void verifyResult() {
        assertTrue(Files.exists(Paths.get(OUTPUT_PATH).resolve("曹賊.epub")));
    }

    @After
    public void cleanUp() {
        FileUtil.deleteAll(Paths.get(OUTPUT_PATH));
    }

}
