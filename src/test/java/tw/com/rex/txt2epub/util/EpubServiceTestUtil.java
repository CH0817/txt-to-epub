package tw.com.rex.txt2epub.util;

import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.frame.MainFrame;

import javax.swing.*;
import java.nio.file.Paths;

public class EpubServiceTestUtil {

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

}
