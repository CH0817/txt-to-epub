package tw.com.rex.txt2epub.frame.panel;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.define.TypesettingEnum;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

/**
 * 橫排、直排 panel
 */
@Getter
public class TypeSettingPanel extends JPanel {

    private final ButtonGroup typesettingGroup;

    public TypeSettingPanel() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.typesettingGroup = initTypesettingGroup();
        setButtonToPanel();
    }

    private ButtonGroup initTypesettingGroup() {
        ButtonGroup result = new ButtonGroup();
        result.add(createHorizontalButton());
        result.add(createVerticalButton());
        return result;
    }

    private JRadioButton createHorizontalButton() {
        JRadioButton result = new JRadioButton("橫排");
        result.setActionCommand(TypesettingEnum.HORIZONTAL.name());
        result.setSelected(true);
        return result;
    }

    private JRadioButton createVerticalButton() {
        JRadioButton result = new JRadioButton("直排");
        result.setActionCommand(TypesettingEnum.VERTICAL.name());
        return result;
    }

    private void setButtonToPanel() {
        Enumeration<AbstractButton> elements = this.typesettingGroup.getElements();
        while (elements.hasMoreElements()) {
            this.add(elements.nextElement());
        }
    }

    public String getStyle() {
        Enumeration<AbstractButton> elements = this.typesettingGroup.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton button = elements.nextElement();
            if (button.isSelected()) {
                return button.getActionCommand();
            }
        }
        return StringUtils.EMPTY;
    }

}
