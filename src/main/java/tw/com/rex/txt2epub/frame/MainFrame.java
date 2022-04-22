package tw.com.rex.txt2epub.frame;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.factory.EpubServiceFactory;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.service.EpubService;
import tw.com.rex.txt2epub.service.TxtHandlerService;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;

public class MainFrame extends JFrame {

    private final Container pane;
    private final GridBagConstraints bag;
    private final JLabel selectedTxtLabel;
    private final JLabel outputFilePath;
    private final JLabel coverPath;
    private final JFileChooser chooser;
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

        chooser = new JFileChooser();
        chooser.setName("chooser");
        chooser.setMultiSelectionEnabled(false);

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
        pane.add(selectFileButton(), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 0;
        pane.add(selectedTxtLabel, bag);
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
        FileFilter txtFileFilter = new FileNameExtensionFilter("txt filter", "txt");
        chooser.setFileFilter(txtFileFilter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int dialog = chooser.showOpenDialog(pane);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            selectedTxtLabel.setText(selectedFile.getPath());
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
        chooser.setFileFilter(null);
        int dialog = chooser.showOpenDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            outputFilePath.setText(selectedFile.getPath());
        }
    }

    private void coverArea() {
        bag.weightx = 0.2;
        bag.gridx = 0;
        bag.gridy = 2;
        pane.add(selectCoverImageButton(), bag);

        bag.weightx = 0.8;
        bag.gridx = 1;
        bag.gridy = 2;
        pane.add(coverPath, bag);
    }

    private JButton selectCoverImageButton() {
        JButton result = new JButton("請選擇封面");
        result.setName("selectCoverImage");
        result.addActionListener(e -> selectCoverImage());
        return result;
    }

    private void selectCoverImage() {
        FileFilter imageFilesFilter = new FileNameExtensionFilter("image files", ImageIO.getReaderFileSuffixes());
        chooser.setFileFilter(imageFilesFilter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int dialog = chooser.showOpenDialog(pane);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            coverPath.setText(selectedFile.getPath());
        }
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
                Book book = createBook();
                Path outputPath = Paths.get(outputFilePath.getText());
                String typesetting = getTypesetting();
                EpubService service = EpubServiceFactory.getEpubService(book, outputPath, typesetting);
                String epub = service.process();
                int input = JOptionPane.showOptionDialog(pane, "轉換成功", "訊息", JOptionPane.DEFAULT_OPTION,
                                                         JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION) {
                    Desktop.getDesktop().open(Paths.get(epub).getParent().toFile());
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
        if (StringUtils.isNotBlank(error.toString())) {
            JOptionPane.showMessageDialog(pane, error);
            return false;
        }
        return true;
    }

    private Book createBook() {
        Book book = new Book();
        book.setName(getBookName());
        book.setCover(Paths.get(coverPath.getText()));
        book.setAuthor(authorField.getText());
        book.setPublisher(publishingHouseField.getText());
        book.setTxtContentList(getTxtContentList());
        return book;
    }

    private String getBookName() {
        String fileName = Paths.get(selectedTxtLabel.getText()).toAbsolutePath().getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    private List<TxtContent> getTxtContentList() {
        return new TxtHandlerService(selectedTxtLabel.getText()).getTxtContentList();
    }

    private String getTypesetting() {
        for (Enumeration<AbstractButton> buttons = typesettingGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getActionCommand();
            }
        }
        return null;
    }

}
