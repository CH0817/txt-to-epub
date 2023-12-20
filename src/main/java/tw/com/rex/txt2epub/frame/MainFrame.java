package tw.com.rex.txt2epub.frame;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.frame.button.CoverChooserButton;
import tw.com.rex.txt2epub.frame.button.OutputPathChooserButton;
import tw.com.rex.txt2epub.frame.button.TxtChooserButton;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.service.EpubService;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.Enumeration;

@Getter
public class MainFrame extends JFrame {

    private final Container pane;
    private final GridBagConstraints bag;
    private final JLabel selectedTxtLabel;
    private final JLabel outputFilePath;
    private final JLabel coverPath;
    private final JTextField authorField;
    private final JTextField publishingHouseField;
    private final ButtonGroup typesettingGroup;

    public MainFrame() throws HeadlessException {
        pane = this.getContentPane();

        bag = new GridBagConstraints();
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(3, 3, 3, 3);

        selectedTxtLabel = new JLabel();
        selectedTxtLabel.setName("selectedFilePath");
        selectedTxtLabel.setPreferredSize(new Dimension(300, 25));

        outputFilePath = new JLabel();
        outputFilePath.setName("outputFilePath");

        coverPath = new JLabel();
        coverPath.setName("coverPath");

        authorField = new JTextField();
        publishingHouseField = new JTextField();

        typesettingGroup = new ButtonGroup();

        setTitle("txt轉EPUB");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setSize(width / 2, height / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPane(getContentPane());
        pack();
    }

    private void setPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        selectFileArea();
        outputFileArea();
        coverArea();
        inputArea();
        typesettingArea();
        executeArea();
    }

    private void selectFileArea() {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 0;
        pane.add(new TxtChooserButton(selectedTxtLabel), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 0;
        pane.add(selectedTxtLabel, bag);
    }

    private void outputFileArea() {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 1;
        pane.add(new OutputPathChooserButton(outputFilePath), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 1;
        pane.add(outputFilePath, bag);
    }

    private void coverArea() {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 2;
        pane.add(new CoverChooserButton(coverPath), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 2;
        pane.add(coverPath, bag);
    }

    private void inputArea() {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 3;
        pane.add(new JLabel("作者"), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 3;
        pane.add(authorField, bag);

        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 4;
        pane.add(new JLabel("出版社"), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 4;
        pane.add(publishingHouseField, bag);
    }

    private void typesettingArea() {
        JRadioButton horizontal = new JRadioButton("橫排");
        horizontal.setActionCommand(TypesettingEnum.HORIZONTAL.name());
        horizontal.setSelected(true);

        JRadioButton vertical = new JRadioButton("直排");
        vertical.setActionCommand(TypesettingEnum.VERTICAL.name());

        typesettingGroup.add(horizontal);
        typesettingGroup.add(vertical);

        bag.weightx = 0.5;
        bag.gridx = 0;
        bag.gridy = 5;
        pane.add(horizontal, bag);

        bag.weightx = 0.5;
        bag.gridx = 1;
        bag.gridy = 5;
        pane.add(vertical, bag);
    }

    private void executeArea() {
        bag.weightx = 0.0;
        bag.gridx = 0;
        bag.gridy = 6;
        bag.gridwidth = 2;

        JButton doExecuteBtn = new JButton("開始轉換");
        doExecuteBtn.addActionListener(e -> convertToEpub());

        pane.add(doExecuteBtn, bag);
    }

    private void convertToEpub() {
        if (verify()) {
            try {
                new EpubService(new ConvertInfo(this)).process();
                int input = JOptionPane.showOptionDialog(pane, "轉換成功", "訊息", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION) {
                    Desktop.getDesktop().open(Paths.get(outputFilePath.getText()).toFile());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(pane, e.getMessage());
            }
        }
    }

    private boolean verify() {
        StringBuilder error = new StringBuilder();
        if (StringUtils.isBlank(selectedTxtLabel.getText())) {
            error.append("請選擇檔案\n");
        }
        if (StringUtils.isBlank(outputFilePath.getText())) {
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
        Enumeration<AbstractButton> elements = typesettingGroup.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton button = elements.nextElement();
            if (button.isSelected()) {
                return button.getActionCommand();
            }
        }
        return StringUtils.EMPTY;
    }

}
