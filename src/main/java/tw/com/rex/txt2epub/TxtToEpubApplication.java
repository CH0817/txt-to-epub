package tw.com.rex.txt2epub;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class TxtToEpubApplication {

    private static JLabel selectedLabel;
    private final static JLabel outputFilePath = new JLabel("請選擇");
    private final static JTextField lineField = new JTextField(10);
    private final static JTextField regexField = new JTextField(10);

    public static void main(String[] args) {
        JFrame frame = new JFrame("txt to EPUB");
        setPane(frame.getContentPane());
        // frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(600, 400);
        // frame.add(selectFileButton(), selectFileButtonBag());
        // frame.add(selectedLabel, selectedFileLabelBag());
        frame.pack();
        frame.setVisible(true);
    }

    private static void setPane(Container pane) {
        pane.setLayout(new GridBagLayout());

        GridBagConstraints bag = new GridBagConstraints();
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(3, 3, 3, 3);

        selectFileArea(pane, bag);
        outputFileArea(pane, bag);
        optionArea(pane, bag);
        splitArea(pane, bag);

        // JPanel panel = new JPanel();
        // panel.add(new JLabel("XD"));
        //
        // bag.ipady = 40;
        // bag.weightx = 0.0;
        // bag.gridwidth = 3;
        // bag.gridx = 0;
        // bag.gridy = 1;
        // pane.add(panel, bag);
        //
        // bag.ipady = 0;       //reset to default
        // bag.weighty = 1.0;   //request any extra vertical space
        // bag.anchor = GridBagConstraints.PAGE_END; //bottom of space
        // bag.insets = new Insets(10, 0, 0, 0);  //top padding
        // bag.gridx = 0;       //aligned with button 2
        // bag.gridwidth = 3;   //2 columns wide
        // bag.gridy = 2;       //third row
        // pane.add(new JButton("開始轉換"), bag);

    }

    private static void selectFileArea(Container pane, GridBagConstraints bag) {
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

    private static void outputFileArea(Container pane, GridBagConstraints bag) {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 1;
        pane.add(selectOutputPathButton(), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 1;
        pane.add(outputFilePath, bag);
    }

    private static void optionArea(Container pane, GridBagConstraints bag) {
        bag.weightx = 0.0;
        bag.gridx = 0;
        bag.gridy = 2;
        bag.gridwidth = 2;
        pane.add(new JCheckBox("轉換文字編碼 UTF-8"), bag);
    }

    private static void splitArea(Container pane, GridBagConstraints bag) {
        JRadioButton lineSplitButton = new JRadioButton("以行數分割");
        JRadioButton regexSplitButton = new JRadioButton("以正則分割");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(lineSplitButton);
        buttonGroup.add(regexSplitButton);

        bag.weightx = 0.5;
        bag.gridx = 0;
        bag.gridy = 3;
        pane.add(lineSplitButton, bag);

        bag.weightx = 0.5;
        bag.gridx = 1;
        bag.gridy = 3;
        pane.add(lineField, bag);
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

}
