package tw.com.rex.txt2epub.frame.button;

import javax.swing.*;
import java.io.File;

/**
 * 封面選擇
 */
public class OutputPathChooserButton extends JButton {

    private final JFileChooser chooser;
    private final JLabel display;

    public OutputPathChooserButton(JLabel display) {
        super("請選擇輸出路徑");
        this.setName("selectOutputPathBtn");
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
        result.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        result.setFileFilter(null);
        return result;
    }

}
