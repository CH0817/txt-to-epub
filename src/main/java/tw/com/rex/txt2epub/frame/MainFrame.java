package tw.com.rex.txt2epub.frame;

import lombok.Getter;
import tw.com.rex.txt2epub.frame.button.ConvertButton;
import tw.com.rex.txt2epub.frame.chapter.ChapterTypePanel;
import tw.com.rex.txt2epub.frame.chooser.CoverChooser;
import tw.com.rex.txt2epub.frame.chooser.FileChooser;
import tw.com.rex.txt2epub.frame.chooser.OutputPathChooser;
import tw.com.rex.txt2epub.frame.chooser.TxtChooser;
import tw.com.rex.txt2epub.frame.panel.TypeSettingPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final GridBagConstraints bag = new GridBagConstraints();
    @Getter
    private final Container pane;
    @Getter
    private final JTextField authorField;
    @Getter
    private final JTextField publishingHouseField;
    private final FileChooser txtChooser = new TxtChooser();
    private final FileChooser outputPathChooser = new OutputPathChooser();
    private final FileChooser coverChooser = new CoverChooser();
    private final TypeSettingPanel typeSettingPanel;
    private final ChapterTypePanel chapterTypePanel = new ChapterTypePanel();

    public MainFrame() throws HeadlessException {
        pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());

        authorField = new JTextField();

        publishingHouseField = new JTextField();

        initTxtChooser();
        initOutputChooser();
        initCoverChooser();

        // 作者
        bag.gridx = 0;
        bag.gridy = 3;
        pane.add(new JLabel("作者", SwingConstants.CENTER), bag);

        bag.gridx = 1;
        bag.gridy = 3;
        pane.add(authorField, bag);

        // 出版社
        bag.gridx = 0;
        bag.gridy = 4;
        pane.add(new JLabel("出版社", SwingConstants.CENTER), bag);

        bag.gridx = 1;
        bag.gridy = 4;
        pane.add(publishingHouseField, bag);

        // 章節判斷
        this.bag.gridx = 0;
        this.bag.gridy = 5;
        this.pane.add(this.chapterTypePanel, this.bag);

        this.bag.gridx = 1;
        this.bag.gridy = 5;
        this.pane.add(this.chapterTypePanel.getTextField(), this.bag);

        this.typeSettingPanel = initTypeSetting();

        // 開始轉換
        bag.gridx = 0;
        bag.gridy = 7;
        bag.gridwidth = 2;

        pane.add(new ConvertButton(this), bag);

        setTitle("txt轉EPUB");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setSize(width / 2, height / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private void initTxtChooser() {
        // file choose button
        this.bag.fill = GridBagConstraints.HORIZONTAL;
        this.bag.gridx = 0;
        this.bag.gridy = 0;
        this.pane.add(this.txtChooser.getButton(), this.bag);
        // choosed file path label
        this.bag.gridx = 1;
        this.bag.gridy = 0;
        this.pane.add(this.txtChooser.getLabel(), this.bag);
    }

    private void initOutputChooser() {
        // output choose button
        bag.gridx = 0;
        bag.gridy = 1;
        pane.add(this.outputPathChooser.getButton(), bag);
        // choosed output path label
        bag.gridx = 1;
        bag.gridy = 1;
        pane.add(this.outputPathChooser.getLabel(), bag);
    }

    private void initCoverChooser() {
        // cover choose button
        bag.gridx = 0;
        bag.gridy = 2;
        pane.add(this.coverChooser.getButton(), bag);
        // choosed cover label
        bag.gridx = 1;
        bag.gridy = 2;
        pane.add(this.coverChooser.getLabel(), bag);
    }

    private TypeSettingPanel initTypeSetting() {
        TypeSettingPanel result = new TypeSettingPanel();

        this.bag.gridx = 0;
        this.bag.gridy = 6;
        this.bag.gridwidth = 2;
        this.pane.add(result, this.bag);

        return result;
    }

    /**
     * 取得文字檔路徑
     *
     * @return 文字檔路徑
     */
    public String getTxtFilePath() {
        return this.txtChooser.getLabel().getText();
    }

    /**
     * 取得輸出路徑
     *
     * @return 輸出路徑
     */
    public String getOutputPath() {
        return this.outputPathChooser.getLabel().getText();
    }

    /**
     * 取得封面路徑
     *
     * @return 封面路徑
     */
    public String getCoverPath() {
        return this.coverChooser.getLabel().getText();
    }

    /**
     * 取得轉換方式
     *
     * @return 橫式或直式
     */
    public String getStyle() {
        return this.typeSettingPanel.getStyle();
    }

    public String getChapterFinderType() {
        return this.chapterTypePanel.getType();
    }

    public String getChapterFinder() {
        return this.chapterTypePanel.getFinder();
    }

}
