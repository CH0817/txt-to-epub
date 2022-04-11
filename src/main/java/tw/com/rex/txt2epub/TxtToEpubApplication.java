package tw.com.rex.txt2epub;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Enumeration;
import java.util.Objects;

public class TxtToEpubApplication {

    private static JLabel selectedLabel;
    private final static GridBagConstraints bag;
    private final static JLabel outputFilePath;
    private final static JTextField lineField;
    private final static JTextField regexField;
    private final static JCheckBox utf8CbBtn;
    private final static JCheckBox transferChineseCbBtn;
    private final static ButtonGroup splitTypeGroup;
    // private final static JRadioButton lineSplitButton;
    // private final static JRadioButton regexSplitButton;

    static {
        bag = new GridBagConstraints();
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(3, 3, 3, 3);
        outputFilePath = new JLabel("請選擇");
        lineField = new JTextField(30);
        regexField = new JTextField(30);
        utf8CbBtn = new JCheckBox("轉換文字編碼 UTF-8");
        transferChineseCbBtn = new JCheckBox("簡轉繁");
        // lineSplitButton = new JRadioButton("以行數分割");
        // lineSplitButton.setActionCommand("1");
        // regexSplitButton = new JRadioButton("以正則分割");
        // regexSplitButton.setActionCommand("2");
        splitTypeGroup = new ButtonGroup();
        // splitTypeGroup.add(lineSplitButton);
        // splitTypeGroup.add(regexSplitButton);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("txt to EPUB");
        setPane(frame.getContentPane());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void setPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        selectFileArea(pane);
        outputFileArea(pane);
        optionArea(pane);
        splitArea(pane);
        executeArea(pane);
    }

    private static void selectFileArea(Container pane) {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 0;
        pane.add(selectFileButton(), bag);

        selectedLabel = new JLabel("請選擇");
        selectedLabel.setPreferredSize(new Dimension(300, 25));

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 0;
        pane.add(selectedLabel, bag);
    }

    private static void outputFileArea(Container pane) {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 1;
        pane.add(selectOutputPathButton(), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 1;
        pane.add(outputFilePath, bag);
    }

    private static void optionArea(Container pane) {
        bag.weightx = 0.5;
        bag.gridx = 0;
        bag.gridy = 2;
        pane.add(utf8CbBtn, bag);

        bag.weightx = 0.5;
        bag.gridx = 1;
        bag.gridy = 2;
        pane.add(transferChineseCbBtn, bag);
    }

    private static void splitArea(Container pane) {
        JRadioButton lineSplitButton = new JRadioButton("以行數分割");
        lineSplitButton.setActionCommand("1");
        JRadioButton regexSplitButton = new JRadioButton("以正則分割");
        regexSplitButton.setActionCommand("2");

        splitTypeGroup.add(lineSplitButton);
        splitTypeGroup.add(regexSplitButton);

        bag.weightx = 0.5;
        bag.gridx = 0;
        bag.gridy = 3;
        bag.gridwidth = 1;
        pane.add(lineSplitButton, bag);

        bag.weightx = 0.5;
        bag.gridx = 1;
        bag.gridy = 3;
        pane.add(lineField, bag);

        bag.weightx = 0.5;
        bag.gridx = 0;
        bag.gridy = 4;
        pane.add(regexSplitButton, bag);

        bag.weightx = 0.5;
        bag.gridx = 1;
        bag.gridy = 4;
        pane.add(regexField, bag);
    }

    private static JButton selectFileButton() {
        JButton result = new JButton("請選擇檔案");
        result.addActionListener(e -> selectFile());
        return result;
    }

    private static void selectFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        FileNameExtensionFilter txtFileFilter = new FileNameExtensionFilter("", "txt");
        chooser.setFileFilter(txtFileFilter);
        int dialog = chooser.showOpenDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            selectedLabel.setText(selectedFile.getPath());
        }
    }

    private static JButton selectOutputPathButton() {
        JButton result = new JButton("請選擇輸出路徑");
        result.addActionListener(e -> selectOutputPath());
        return result;
    }

    private static void selectOutputPath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int dialog = chooser.showOpenDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            outputFilePath.setText(selectedFile.getPath());
        }
    }

    private static void executeArea(Container pane) {
        bag.weightx = 0.0;
        bag.gridx = 0;
        bag.gridy = 5;
        bag.gridwidth = 2;

        JButton doExecuteBtn = new JButton("開始轉換");
        doExecuteBtn.addActionListener(e -> {
            StringBuilder error = new StringBuilder();
            String selectedFile = selectedLabel.getText().replace("請選擇", "");
            if (selectedFile.isBlank()) {
                error.append("請選擇檔案\n");
            }
            String outputPath = outputFilePath.getText().replace("請選擇", "");
            if (outputPath.isBlank()) {
                error.append("請選擇輸出路徑\n");
            }
            if (utf8CbBtn.isSelected()) {
                // todo 轉 UTF-8
            }
            if (transferChineseCbBtn.isSelected()) {
                // todo 簡轉繁
            }
            String splitType = null;
            for (Enumeration<AbstractButton> buttons = splitTypeGroup.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    splitType = button.getActionCommand();
                }
            }
            if (Objects.isNull(splitType) || splitType.isBlank()) {
                error.append("請先選擇分割方式\n");
            } else {
                switch (splitType) {
                    case "1":
                        // todo 行分割
                        int lines = 0;
                        try {
                            lines = Integer.parseInt(lineField.getText());
                            if (lines == 0) {
                                error.append("行數不能為0\n");
                            }
                        } catch (NumberFormatException ex) {
                            error.append("請輸入行數\n");
                        }
                        break;
                    case "2":
                        // todo 正則分割
                        String regex = regexField.getText();
                        if (Objects.isNull(regex) || regex.isBlank()) {
                            error.append("請輸入正則\n");
                        }
                        break;
                }
            }
            if (!error.toString().isBlank()) {
                JOptionPane.showMessageDialog(pane, error);
            } else {
                // todo 開始轉換
            }
        });

        pane.add(doExecuteBtn, bag);
    }

}
