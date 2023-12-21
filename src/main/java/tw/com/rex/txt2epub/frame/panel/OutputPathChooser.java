package tw.com.rex.txt2epub.frame.panel;

import tw.com.rex.txt2epub.frame.button.OutputPathChooserButton;
import tw.com.rex.txt2epub.frame.chooser.FileChooser;

import javax.swing.*;
import java.awt.*;

/**
 * 輸出路徑選擇器
 */
public class OutputPathChooser implements FileChooser {

    private final JLabel label;
    private final OutputPathChooserButton button;

    public OutputPathChooser() {
        this.label = new JLabel();
        this.label.setPreferredSize(new Dimension(300, 25));
        this.button = new OutputPathChooserButton(this.label);
    }

    @Override
    public JLabel getLabel() {
        return this.label;
    }

    @Override
    public JButton getButton() {
        return this.button;
    }

}
