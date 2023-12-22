package tw.com.rex.txt2epub.frame.chooser;

import tw.com.rex.txt2epub.frame.button.TxtChooserButton;
import tw.com.rex.txt2epub.frame.chooser.FileChooser;

import javax.swing.*;
import java.awt.*;

public class TxtChooser implements FileChooser {

    private final JLabel label;
    private final TxtChooserButton button;

    public TxtChooser() {
        this.label = new JLabel();
        this.label.setPreferredSize(new Dimension(300, 25));
        this.button = new TxtChooserButton(this.label);
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
