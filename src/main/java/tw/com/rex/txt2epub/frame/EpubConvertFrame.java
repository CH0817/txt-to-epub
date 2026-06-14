package tw.com.rex.txt2epub.frame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import lombok.Setter;
import tw.com.rex.txt2epub.builder.GBCBuilder;
import tw.com.rex.txt2epub.builder.helper.GridPositionTracker;
import tw.com.rex.txt2epub.frame.button.ConvertButton;
import tw.com.rex.txt2epub.frame.chapter.ChapterTypePanel;
import tw.com.rex.txt2epub.frame.chooser.CoverChooser;
import tw.com.rex.txt2epub.frame.chooser.FileChooser;
import tw.com.rex.txt2epub.frame.chooser.OutputPathChooser;
import tw.com.rex.txt2epub.frame.chooser.TxtChooser;
import tw.com.rex.txt2epub.frame.panel.TypeSettingPanel;
import tw.com.rex.txt2epub.presenter.EpubConvertPresenter;
import tw.com.rex.txt2epub.view.EpubConvertView;

public class EpubConvertFrame extends JFrame implements EpubConvertView {

    private final GridPositionTracker tracker = new GridPositionTracker();
    private final JTextField authorField = new JTextField();
    private final JTextField publishingHouseField = new JTextField();
    private final FileChooser txtChooser = new TxtChooser();
    private final FileChooser outputPathChooser = new OutputPathChooser();
    private final FileChooser coverChooser = new CoverChooser();
    private final TypeSettingPanel typeSettingPanel = new TypeSettingPanel();
    private final ChapterTypePanel chapterTypePanel = new ChapterTypePanel();
    private final JCheckBox convertSimplified = new JCheckBox("簡轉繁");

    @Setter
    private EpubConvertPresenter presenter;

    public EpubConvertFrame() {
        super("txt 轉 EPUB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());

        initTxtChooser(panel);
        initOutputChooser(panel);
        initCoverChooser(panel);

        // 作者
        panel.add(new JLabel("作者", SwingConstants.CENTER), new GBCBuilder(0, 3, tracker).build());
        panel.add(authorField, new GBCBuilder(1, 3, tracker)
                .fill(GridBagConstraints.HORIZONTAL)
                .build());
        // 出版社
        panel.add(new JLabel("出版社", SwingConstants.CENTER), new GBCBuilder(0, 4, tracker).build());
        panel.add(publishingHouseField, new GBCBuilder(1, 4, tracker)
                .fill(GridBagConstraints.HORIZONTAL)
                .build());

        // 章節判斷
        panel.add(chapterTypePanel, new GBCBuilder(0, 5, tracker).build());
        panel.add(chapterTypePanel.getTextField(), new GBCBuilder(1, 5, tracker).build());
        panel.add(typeSettingPanel, new GBCBuilder(0, 6, tracker).build());
        panel.add(convertSimplified, new GBCBuilder(1, 6, tracker)
                .fill(GridBagConstraints.HORIZONTAL)
                .build());

        // 開始轉換
        panel.add(new ConvertButton(this), new GBCBuilder(0, 7, tracker)
                .gridWidth(2)
                .build());

        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    private void initTxtChooser(JPanel panel) {
        // file choose button
        panel.add(txtChooser.getButton(), new GBCBuilder(0, 0, tracker)
                .fill(GridBagConstraints.HORIZONTAL)
                .build());
        // chose file path label
        panel.add(txtChooser.getTextField(), new GBCBuilder(1, 0, tracker).build());
    }

    private void initOutputChooser(JPanel panel) {
        // output choose button
        panel.add(outputPathChooser.getButton(), new GBCBuilder(0, 1, tracker).build());
        // chose output path label
        panel.add(outputPathChooser.getTextField(), new GBCBuilder(1, 1, tracker).build());
    }

    private void initCoverChooser(JPanel panel) {
        // cover choose button
        panel.add(coverChooser.getButton(), new GBCBuilder(0, 2, tracker).build());
        // chose cover label
        panel.add(coverChooser.getTextField(), new GBCBuilder(1, 2, tracker).build());
    }

    @Override
    public String getTxtFilePath() {
        return this.txtChooser.getTextField().getText();
    }

    @Override
    public String getOutputPath() {
        return this.outputPathChooser.getTextField().getText();
    }

    @Override
    public String getCoverPath() {
        return this.coverChooser.getTextField().getText();
    }

    @Override
    public String getAuthor() {
        return this.authorField.getText();
    }

    @Override
    public String getPublisher() {
        return this.publishingHouseField.getText();
    }

    @Override
    public String getStyle() {
        return typeSettingPanel.getStyle();
    }

    @Override
    public String getChapterFinderType() {
        return chapterTypePanel.getType();
    }

    @Override
    public String getChapterFinder() {
        return chapterTypePanel.getFinder();
    }

    @Override
    public boolean isConvertSimplified() {
        return convertSimplified.isSelected();
    }

    @Override
    public void showMessage(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showMessage'");
    }

    @Override
    public void showErrorMessage(String error) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showErrorMessage'");
    }

    @Override
    public void setProgressLoading(boolean isLoading) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setProgressLoading'");
    }

}
