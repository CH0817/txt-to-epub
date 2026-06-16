package tw.com.rex.txt2epub.frame;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import lombok.Setter;
import tw.com.rex.txt2epub.builder.GBCBuilder;
import tw.com.rex.txt2epub.builder.helper.GridPositionTracker;
import tw.com.rex.txt2epub.define.ChapterTypeEnum;
import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.factory.StyleFactory;
import tw.com.rex.txt2epub.frame.chooser.CoverChooser;
import tw.com.rex.txt2epub.frame.chooser.FileChooser;
import tw.com.rex.txt2epub.frame.chooser.OutputPathChooser;
import tw.com.rex.txt2epub.frame.chooser.TxtChooser;
import tw.com.rex.txt2epub.model.css.DisplayStyle;
import tw.com.rex.txt2epub.presenter.EpubConvertPresenter;
import tw.com.rex.txt2epub.view.EpubConvertView;

/**
 * 畫面取得/操作 implement
 */
public class EpubConvertFrame extends JFrame implements EpubConvertView {

    private final GridPositionTracker tracker = new GridPositionTracker();
    private final JTextField authorField = new JTextField();
    private final JTextField publishingHouseField = new JTextField();
    private final FileChooser txtChooser = new TxtChooser();
    private final FileChooser outputPathChooser = new OutputPathChooser();
    private final FileChooser coverChooser = new CoverChooser();
    private final JCheckBox convertSimplified = new JCheckBox("簡轉繁");
    private final JButton convertButton = new JButton("開始轉換");
    private final ButtonGroup chapterTypeRadioButtonGroup = new ButtonGroup();
    private final ButtonGroup displayTypeRadioButtonGroup = new ButtonGroup();
    private final JTextField chapterTypeTextField;
    private final KeyAdapter onlyNumberInputKeyAdapter;
    private final JRadioButton regexRadioButton;
    private final JRadioButton wordCountRadioButton;
    private final JRadioButton horizontalRadioButton;
    private final JRadioButton verticalRadioButton;

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
        chapterTypeTextField = initChapterTypeTextField();
        onlyNumberInputKeyAdapter = initOnlyNumberInputKeyAdapter();
        regexRadioButton = createRegexButton();
        wordCountRadioButton = createWordCountButton();
        chapterTypeRadioButtonGroup.add(regexRadioButton);
        chapterTypeRadioButtonGroup.add(wordCountRadioButton);

        JPanel chapterTypePanel = new JPanel();
        chapterTypePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        chapterTypePanel.add(regexRadioButton);
        chapterTypePanel.add(wordCountRadioButton);

        panel.add(chapterTypePanel, new GBCBuilder(0, 5, tracker).build());
        panel.add(chapterTypeTextField, new GBCBuilder(1, 5, tracker).build());

        // 排版模式判斷
        horizontalRadioButton = createHorizontalButton();
        verticalRadioButton = createVerticalButton();
        displayTypeRadioButtonGroup.add(horizontalRadioButton);
        displayTypeRadioButtonGroup.add(verticalRadioButton);
        JPanel displayTypePanel = new JPanel();

        displayTypePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        displayTypePanel.add(horizontalRadioButton);
        displayTypePanel.add(verticalRadioButton);

        panel.add(displayTypePanel, new GBCBuilder(0, 6, tracker).build());

        // 簡轉繁 checkbox
        panel.add(convertSimplified, new GBCBuilder(1, 6, tracker)
                .fill(GridBagConstraints.HORIZONTAL)
                .build());

        // 開始轉換
        this.convertButton.addActionListener(e -> {
            if (Objects.nonNull(this.presenter)) {
                this.presenter.onStartConversion();
            }
        });

        panel.add(this.convertButton, new GBCBuilder(0, 7, tracker)
                .gridWidth(2)
                .build());

        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    private JRadioButton createHorizontalButton() {
        JRadioButton result = new JRadioButton("橫排");
        result.setActionCommand(TypesettingEnum.HORIZONTAL.name());
        result.setSelected(true);
        return result;
    }

    private JRadioButton createVerticalButton() {
        JRadioButton result = new JRadioButton("直排");
        result.setActionCommand(TypesettingEnum.VERTICAL.name());
        return result;
    }

    private JTextField initChapterTypeTextField() {
        JTextField result = new JTextField();
        result.setText("^第[0-9]{1,4}章 .*$");
        result.setPreferredSize(new Dimension(300, 25));
        return result;
    }

    private JRadioButton createRegexButton() {
        ChapterTypeEnum regexEnum = ChapterTypeEnum.REGEX;
        JRadioButton result = new JRadioButton(regexEnum.text);
        result.setActionCommand(regexEnum.name());
        result.setSelected(true);
        result.addActionListener(e -> {
            chapterTypeTextField.setText("^第[0-9]{1,4}章 .*$");
            chapterTypeTextField.removeKeyListener(onlyNumberInputKeyAdapter);
        });
        return result;
    }

    private JRadioButton createWordCountButton() {
        ChapterTypeEnum wordCountEnum = ChapterTypeEnum.WORD_COUNT;
        JRadioButton result = new JRadioButton(wordCountEnum.text);
        result.setActionCommand(wordCountEnum.name());
        result.addActionListener(e -> {
            chapterTypeTextField.setText("5000");
            chapterTypeTextField.addKeyListener(onlyNumberInputKeyAdapter);
        });
        return result;
    }

    private KeyAdapter initOnlyNumberInputKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if (!isNumber(keyChar) && !isIgnoreShowTipKey(keyChar)) {
                    JOptionPane.showMessageDialog(null, "請輸入數字");
                    e.consume();
                }
            }
        };
    }

    private boolean isNumber(int keyChar) {
        return keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9;
    }

    private boolean isIgnoreShowTipKey(int keyChar) {
        switch (keyChar) {
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_BACK_SPACE:
                return true;
            default:
                return false;
        }
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
    public DisplayStyle getDisplayStyle() {
        return StyleFactory.getStyle(displayTypeRadioButtonGroup.getSelection().getActionCommand());
    }

    @Override
    public String getChapterFinderType() {
        return chapterTypeRadioButtonGroup.getSelection().getActionCommand();
    }

    @Override
    public String getChapterFinder() {
        return chapterTypeTextField.getText();
    }

    @Override
    public boolean isConvertSimplified() {
        return convertSimplified.isSelected();
    }

    @Override
    public void showSuccess() {
        this.setProgressLoading(false);
        int input = JOptionPane.showOptionDialog(this, "轉換成功", "訊息", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if (input == JOptionPane.OK_OPTION) {
            try {
                Desktop.getDesktop().open(Paths.get(this.getOutputPath()).toFile());
            } catch (IOException e) {
                this.showErrorMessage("開啟 " + this.getOutputPath() + "失敗！");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showErrorMessage(String error) {
        JOptionPane.showMessageDialog(this, error, "錯誤", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setProgressLoading(boolean isLoading) {
        this.convertButton.setEnabled(!isLoading);
        this.convertButton.setText(isLoading ? "轉換中..." : "開始轉換");
    }

}
