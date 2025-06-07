package tw.com.rex.txt2epub.frame.chooser;

import lombok.Getter;
import tw.com.rex.txt2epub.frame.button.CoverChooserButton;

import javax.swing.*;
import java.awt.*;

/**
 * 封面選擇器
 */
@Getter
public class CoverChooser implements FileChooser {

    private final JTextField textField;
    private final CoverChooserButton button;

    public CoverChooser() {
        this.textField = new JTextField();
        this.textField.setPreferredSize(new Dimension(300, 25));
        this.textField.setEditable(false);

        this.button = new CoverChooserButton(this.textField);
    }

}
