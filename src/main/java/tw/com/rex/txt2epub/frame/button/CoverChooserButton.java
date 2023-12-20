package tw.com.rex.txt2epub.frame.button;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * 封面選擇
 */
public class CoverChooserButton extends JButton {

    private final JFileChooser chooser;
    private final JLabel display;

    public CoverChooserButton(JLabel display) {
        super("請選擇封面");
        this.setName("selectCoverImage");
        this.addActionListener(e -> choose());
        this.display = display;
        this.chooser = createChooser();
    }

    private void choose() {
        int dialog = chooser.showOpenDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            display.setText(selectedFile.getPath());
        }
    }

    private JFileChooser createChooser() {
        JFileChooser result = new JFileChooser();
        result.setMultiSelectionEnabled(false);
        result.setFileFilter(imageFileFilter());
        result.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return result;
    }

    private FileFilter imageFileFilter() {
        return new FileNameExtensionFilter("image files", ImageIO.getReaderFileSuffixes());
    }

}
