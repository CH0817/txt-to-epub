package tw.com.rex.txt2epub.frame.chooser;

import lombok.Getter;
import tw.com.rex.txt2epub.frame.button.TxtChooserButton;

import javax.swing.*;
import java.awt.*;

@Getter
public class TxtChooser implements FileChooser {

    private final JTextField textField;
    private final TxtChooserButton button;

    public TxtChooser() {
        this.textField = new JTextField();
        this.textField.setPreferredSize(new Dimension(300, 25));
        this.textField.setEditable(false);

        this.button = new TxtChooserButton(this.textField);
    }

}
