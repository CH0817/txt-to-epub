package tw.com.rex.txt2epub.frame.chapter;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

public class ChapterTypePanel extends JPanel {

    private final ButtonGroup buttonGroup;
    @Getter
    private final JTextField textField;
    private final KeyAdapter onlyNumberInputKeyAdapter;

    public ChapterTypePanel() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.textField = initTextField();
        this.buttonGroup = initButtonGroup();
        this.onlyNumberInputKeyAdapter = initOnlyNumberInputKeyAdapter();
        setButtonToPanel();
    }

    private ButtonGroup initButtonGroup() {
        ButtonGroup result = new ButtonGroup();
        result.add(createRegexButton());
        result.add(createWordCountButton());
        return result;
    }

    private JRadioButton createRegexButton() {
        JRadioButton result = new JRadioButton("正則");
        result.setActionCommand("regex");
        result.setSelected(true);
        result.addActionListener(e -> {
            this.textField.setText("^第[0-9]{1,4}章 .*$");
            this.textField.removeKeyListener(onlyNumberInputKeyAdapter);
        });
        return result;
    }

    private JRadioButton createWordCountButton() {
        JRadioButton result = new JRadioButton("字數");
        result.setActionCommand("wordCount");
        result.addActionListener(e -> {
            this.textField.setText("");
            this.textField.addKeyListener(onlyNumberInputKeyAdapter);
        });
        return result;
    }

    private void setButtonToPanel() {
        Enumeration<AbstractButton> elements = this.buttonGroup.getElements();
        while (elements.hasMoreElements()) {
            this.add(elements.nextElement());
        }
    }

    private JTextField initTextField() {
        JTextField result = new JTextField();
        result.setText("^第[0-9]{1,4}章 .*$");
        result.setPreferredSize(new Dimension(300, 25));
        return result;
    }

    private KeyAdapter initOnlyNumberInputKeyAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!isNumber(e.getKeyChar())) {
                    JOptionPane.showMessageDialog(null, "請輸入數字");
                    e.consume();
                }
            }
        };
    }

    private boolean isNumber(int keyChar) {
        return keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9;
    }

    public String getType() {
        Enumeration<AbstractButton> elements = this.buttonGroup.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton button = elements.nextElement();
            if (button.isSelected()) {
                return button.getActionCommand();
            }
        }
        return StringUtils.EMPTY;
    }

    public String getFinder() {
        return this.textField.getText();
    }

}
