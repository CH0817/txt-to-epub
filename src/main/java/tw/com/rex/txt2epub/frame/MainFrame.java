package tw.com.rex.txt2epub.frame;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.service.TxtHandlerService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MainFrame extends JFrame {

    private final Container pane;
    private final JLabel selectedLabel;
    private final GridBagConstraints bag;
    private final JLabel outputFilePath;
    private final JFileChooser chooser;

    public MainFrame() throws HeadlessException {
        pane = this.getContentPane();

        selectedLabel = new JLabel("請選擇");
        selectedLabel.setName("selectedFilePath");
        // selectedLabel = new JLabel("D:/Rex/Downloads/曹賊.txt");
        selectedLabel.setPreferredSize(new Dimension(300, 25));

        bag = new GridBagConstraints();
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(3, 3, 3, 3);

        outputFilePath = new JLabel("請選擇");
        outputFilePath.setName("outputFilePath");
        // outputFilePath = new JLabel("D:/Temp/曹賊.txt");

        chooser = new JFileChooser();
        chooser.setName("chooser");
        chooser.setMultiSelectionEnabled(false);

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
        executeArea();
    }

    private void selectFileArea() {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 0;
        pane.add(selectFileButton(), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 0;
        pane.add(selectedLabel, bag);
    }

    private void outputFileArea() {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 1;
        pane.add(selectOutputPathButton(), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 1;
        pane.add(outputFilePath, bag);
    }

    private JButton selectFileButton() {
        JButton result = new JButton("請選擇檔案");
        result.setName("selectFileBtn");
        result.addActionListener(e -> selectFile());
        return result;
    }

    private void selectFile() {
        FileNameExtensionFilter txtFileFilter = new FileNameExtensionFilter("", "txt");
        chooser.setFileFilter(txtFileFilter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int dialog = chooser.showOpenDialog(pane);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            selectedLabel.setText(selectedFile.getPath());
        }
    }

    private JButton selectOutputPathButton() {
        JButton result = new JButton("請選擇輸出路徑");
        result.setName("selectOutputPathBtn");
        result.addActionListener(e -> selectOutputPath());
        return result;
    }

    private void selectOutputPath() {
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int dialog = chooser.showOpenDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            outputFilePath.setText(selectedFile.getPath());
        }
    }

    private void executeArea() {
        bag.weightx = 0.0;
        bag.gridx = 0;
        bag.gridy = 5;
        bag.gridwidth = 2;

        JButton doExecuteBtn = new JButton("開始轉換");
        doExecuteBtn.addActionListener(e -> convertToEpub());

        pane.add(doExecuteBtn, bag);
    }

    private void convertToEpub() {
        if (verify()) {
            TxtHandlerService service = new TxtHandlerService(selectedLabel.getText());
            List<TxtContent> txtContentList = service.getTxtContentList();
            // todo 分割並轉換
            JOptionPane.showMessageDialog(pane, "轉換成功");
        }
    }

    private boolean verify() {
        StringBuilder error = new StringBuilder();
        String selectedFile = selectedLabel.getText().replace("請選擇", "");
        if (selectedFile.isBlank()) {
            error.append("請選擇檔案\n");
        }
        String outputPath = outputFilePath.getText().replace("請選擇", "");
        if (outputPath.isBlank()) {
            error.append("請選擇輸出路徑\n");
        }
        if (StringUtils.isNotBlank(error.toString())) {
            JOptionPane.showMessageDialog(pane, error);
            return false;
        }
        return true;
    }

}
