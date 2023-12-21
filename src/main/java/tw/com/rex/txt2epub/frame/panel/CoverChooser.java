package tw.com.rex.txt2epub.frame.panel;

import tw.com.rex.txt2epub.frame.button.CoverChooserButton;
import tw.com.rex.txt2epub.frame.chooser.FileChooser;

import javax.swing.*;
import java.awt.*;

/**
 * 封面選擇器
 */
public class CoverChooser implements FileChooser {

    private final JLabel label;
    private final CoverChooserButton button;

    public CoverChooser() {
        this.label = new JLabel();
        this.label.setPreferredSize(new Dimension(300, 25));
        this.button = new CoverChooserButton(this.label);
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
