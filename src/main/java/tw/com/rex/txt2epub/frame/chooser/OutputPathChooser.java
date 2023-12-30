package tw.com.rex.txt2epub.frame.chooser;

import lombok.Getter;
import tw.com.rex.txt2epub.frame.button.OutputPathChooserButton;

import javax.swing.*;
import java.awt.*;

/**
 * 輸出路徑選擇器
 */
@Getter
public class OutputPathChooser implements FileChooser {

    private final JTextField textField;
    private final OutputPathChooserButton button;

    public OutputPathChooser() {
        this.textField = new JTextField();
        this.textField.setPreferredSize(new Dimension(300, 25));
        this.textField.setEditable(false);

        this.button = new OutputPathChooserButton(this.textField);
    }
}
