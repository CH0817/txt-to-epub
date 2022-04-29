package tw.com.rex.txt2epub.utils;

import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TempDirectory;
import tw.com.rex.txt2epub.utils.FileUtil;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.Arrays;

public class TestUtil {

    public static MainFrame createFrame(TypesettingEnum typesettingEnum, String outputPath) {
        MainFrame frame = new MainFrame();
        frame.setSelectedTxtLabel(new JLabel(Paths.get("src/test/resources/曹賊.txt").toAbsolutePath().toString()));
        frame.setOutputFilePath(new JLabel(outputPath));
        frame.setCoverPath(new JLabel(Paths.get("src/test/resources/cover.jpg").toAbsolutePath().toString()));
        frame.setAuthorField(new JTextField("庚新"));
        frame.setPublishingHouseField(new JTextField("典藏閣"));
        frame.setTypesettingGroup(createTypesettingGroup(typesettingEnum));
        return frame;
    }

    private static ButtonGroup createTypesettingGroup(TypesettingEnum typesetting) {
        ButtonGroup result = new ButtonGroup();
        JRadioButton horizontal = new JRadioButton("橫排");
        horizontal.setActionCommand(TypesettingEnum.HORIZONTAL.name());

        JRadioButton vertical = new JRadioButton("直排");
        vertical.setActionCommand(TypesettingEnum.VERTICAL.name());

        if (TypesettingEnum.HORIZONTAL.equals(typesetting)) {
            horizontal.setSelected(true);
        } else {
            vertical.setSelected(true);
        }

        result.add(horizontal);
        result.add(vertical);
        return result;
    }

    public static ConvertInfo createConvertInfo() {
        String outputPath = Paths.get(System.getProperty("java.io.tmpdir"), "測試").toAbsolutePath().toString();
        return new ConvertInfo(createFrame(TypesettingEnum.HORIZONTAL, outputPath));
    }

    public static void cleanUp(ConvertInfo convertInfo) {
        deleteTempFiles(convertInfo);
        deleteOutputFiles(convertInfo);
    }

    private static void deleteTempFiles(ConvertInfo convertInfo) {
        Arrays.stream(convertInfo.getTempDirectories())
              .map(TempDirectory::getBasePath)
              .forEach(FileUtil::deleteAll);
    }

    private static void deleteOutputFiles(ConvertInfo convertInfo) {
        FileUtil.deleteAll(convertInfo.getOutput());
    }

}
