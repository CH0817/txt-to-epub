package tw.com.rex.txt2epub.frame.button;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * txt 檔案選擇
 */
public class TxtChooserButton extends JButton {

    private final JFileChooser chooser;
    private final JLabel display;

    public TxtChooserButton(JLabel display) {
        super("請選擇.txt檔案");
        this.setName("selectFileBtn");
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
        result.setFileFilter(xtxFileFilter());
        result.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return result;
    }

    private FileFilter xtxFileFilter() {
        return new FileNameExtensionFilter("txt filter", "txt");
    }

}
