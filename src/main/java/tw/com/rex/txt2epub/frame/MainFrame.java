package tw.com.rex.txt2epub.frame;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.frame.chooser.FileChooser;
import tw.com.rex.txt2epub.frame.panel.CoverChooser;
import tw.com.rex.txt2epub.frame.panel.OutputPathChooser;
import tw.com.rex.txt2epub.frame.panel.TxtChooser;
import tw.com.rex.txt2epub.frame.panel.TypeSettingPanel;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.service.EpubService;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.Enumeration;

@Getter
public class MainFrame extends JFrame {

    private final GridBagConstraints bag = new GridBagConstraints();
    private final Container pane;
    private final JTextField authorField;
    private final JTextField publishingHouseField;
    private final FileChooser txtChooser = new TxtChooser();
    private final FileChooser outputPathChooser = new OutputPathChooser();
    private final FileChooser coverChooser = new CoverChooser();
    private final TypeSettingPanel typeSettingPanel;

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

        this.typeSettingPanel = initTypeSetting();

        // 開始轉換
        bag.gridx = 0;
        bag.gridy = 6;
        bag.gridwidth = 2;

        JButton doExecuteBtn = new JButton("開始轉換");
        doExecuteBtn.addActionListener(e -> convertToEpub());

        pane.add(doExecuteBtn, bag);

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
        this.bag.gridy = 5;
        this.bag.gridwidth = 2;
        this.pane.add(result, this.bag);

        return result;
    }

    private void convertToEpub() {
        if (verify()) {
            try {
                new EpubService(new ConvertInfo(this)).process();
                int input = JOptionPane.showOptionDialog(pane, "轉換成功", "訊息", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION) {
                    Desktop.getDesktop().open(Paths.get(this.outputPathChooser.getLabel().getText()).toFile());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(pane, e.getMessage());
            }
        }
    }

    private boolean verify() {
        StringBuilder error = new StringBuilder();
        if (StringUtils.isBlank(txtChooser.getLabel().getText())) {
            error.append("請選擇檔案\n");
        }
        if (StringUtils.isBlank(this.outputPathChooser.getLabel().getText())) {
            error.append("請選擇輸出路徑\n");
        }
        if (StringUtils.isBlank(getTypesetting())) {
            error.append("未選擇直排或橫排\n");
        }
        if (StringUtils.isNotBlank(error.toString())) {
            JOptionPane.showMessageDialog(pane, error);
            return false;
        }
        return true;
    }

    public String getTypesetting() {
        Enumeration<AbstractButton> elements = this.typeSettingPanel.getTypesettingGroup().getElements();
        while (elements.hasMoreElements()) {
            AbstractButton button = elements.nextElement();
            if (button.isSelected()) {
                return button.getActionCommand();
            }
        }
        return StringUtils.EMPTY;
    }

}
