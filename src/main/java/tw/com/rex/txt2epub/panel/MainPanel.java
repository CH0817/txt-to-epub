package tw.com.rex.txt2epub.panel;

import lombok.Getter;
import tw.com.rex.txt2epub.builder.GBCBuilder;
import tw.com.rex.txt2epub.builder.helper.GridPositionTracker;
import tw.com.rex.txt2epub.frame.button.ConvertButton;
import tw.com.rex.txt2epub.frame.chapter.ChapterTypePanel;
import tw.com.rex.txt2epub.frame.chooser.CoverChooser;
import tw.com.rex.txt2epub.frame.chooser.FileChooser;
import tw.com.rex.txt2epub.frame.chooser.OutputPathChooser;
import tw.com.rex.txt2epub.frame.chooser.TxtChooser;
import tw.com.rex.txt2epub.frame.panel.TypeSettingPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 主要容器
 */
public class MainPanel extends JPanel {

    private final GridPositionTracker tracker = new GridPositionTracker();
    @Getter
    private final JTextField authorField;
    @Getter
    private final JTextField publishingHouseField;
    private final FileChooser txtChooser = new TxtChooser();
    private final FileChooser outputPathChooser = new OutputPathChooser();
    private final FileChooser coverChooser = new CoverChooser();
    private final TypeSettingPanel typeSettingPanel = new TypeSettingPanel();
    private final ChapterTypePanel chapterTypePanel = new ChapterTypePanel();
    private final JCheckBox convertSimplified = new JCheckBox("簡轉繁");

    public MainPanel() {
        super(new GridBagLayout());

        authorField = new JTextField();

        publishingHouseField = new JTextField();

        initTxtChooser();
        initOutputChooser();
        initCoverChooser();

        // 作者
        add(new JLabel("作者", SwingConstants.CENTER), new GBCBuilder(0,3, tracker).build());
        add(authorField, new GBCBuilder(1,3, tracker)
                .fill(GridBagConstraints.HORIZONTAL)
                .build());
        // 出版社
        add(new JLabel("出版社", SwingConstants.CENTER), new GBCBuilder(0,4, tracker).build());
        add(publishingHouseField, new GBCBuilder(1,4, tracker)
                .fill(GridBagConstraints.HORIZONTAL)
                .build());

        // 章節判斷
        add(chapterTypePanel, new GBCBuilder(0,5, tracker).build());
        add(chapterTypePanel.getTextField(), new GBCBuilder(1,5, tracker).build());
        add(typeSettingPanel, new GBCBuilder(0,6, tracker).build());
        add(convertSimplified, new GBCBuilder(1,6, tracker)
                .fill(GridBagConstraints.HORIZONTAL)
                .build());

        // 開始轉換
        add(new ConvertButton(this), new GBCBuilder(0,7, tracker)
                .gridWidth(2)
                .build());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setSize(width / 2, height / 2);

    }

    private void initTxtChooser() {
        // file choose button
        add(txtChooser.getButton(), new GBCBuilder(0,0, tracker)
                .fill(GridBagConstraints.HORIZONTAL)
                .build());
        // chose file path label
        add(txtChooser.getTextField(), new GBCBuilder(1,0, tracker).build());
    }

    private void initOutputChooser() {
        // output choose button
        add(outputPathChooser.getButton(), new GBCBuilder(0,1, tracker).build());
        // chose output path label
        add(outputPathChooser.getTextField(), new GBCBuilder(1,1, tracker).build());
    }

    private void initCoverChooser() {
        // cover choose button
        add(coverChooser.getButton(), new GBCBuilder(0,2, tracker).build());
        // chose cover label
        add(coverChooser.getTextField(), new GBCBuilder(1,2, tracker).build());
    }

    /**
     * 取得文字檔路徑
     *
     * @return 文字檔路徑
     */
    public String getTxtFilePath() {
        return txtChooser.getTextField().getText();
    }

    /**
     * 取得輸出路徑
     *
     * @return 輸出路徑
     */
    public String getOutputPath() {
        return outputPathChooser.getTextField().getText();
    }

    /**
     * 取得封面路徑
     *
     * @return 封面路徑
     */
    public String getCoverPath() {
        return coverChooser.getTextField().getText();
    }

    /**
     * 取得轉換方式
     *
     * @return 橫式或直式
     */
    public String getStyle() {
        return typeSettingPanel.getStyle();
    }

    public String getChapterFinderType() {
        return chapterTypePanel.getType();
    }

    public String getChapterFinder() {
        return chapterTypePanel.getFinder();
    }

    public boolean isConvertSimplified() {
        return convertSimplified.isSelected();
    }

}
