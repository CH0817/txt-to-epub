package tw.com.rex.txt2epub.frame.button;

import javax.swing.*;
import java.io.File;

/**
 * 封面選擇
 */
public class OutputPathChooserButton extends JButton {

    private final JFileChooser chooser;
    private final JTextField display;

    public OutputPathChooserButton(JTextField display) {
        super("請選擇輸出路徑");
        this.setName("selectOutputPathBtn");
        this.addActionListener(e -> choose());
        this.display = display;
        this.chooser = createChooser();
    }

    private void choose() {
        int dialog = this.chooser.showOpenDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = this.chooser.getSelectedFile();
            this.display.setText(selectedFile.getPath());
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
