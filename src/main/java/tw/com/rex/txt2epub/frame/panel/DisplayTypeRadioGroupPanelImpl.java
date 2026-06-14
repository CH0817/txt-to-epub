package tw.com.rex.txt2epub.frame.panel;

import java.awt.FlowLayout;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import lombok.Getter;
import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.factory.StyleFactory;
import tw.com.rex.txt2epub.frame.DisplayTypeRadioGroupPanel;
import tw.com.rex.txt2epub.model.css.DisplayStyle;

/**
 * 橫排、直排 panel
 */
@Getter
public class DisplayTypeRadioGroupPanelImpl extends JPanel implements DisplayTypeRadioGroupPanel {

    private final ButtonGroup group;

    public DisplayTypeRadioGroupPanelImpl() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.group = initTypesettingGroup();
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
        Enumeration<AbstractButton> elements = this.group.getElements();
        while (elements.hasMoreElements()) {
            this.add(elements.nextElement());
        }
    }

    @Override
    public String getSelected() {
        return group.getSelection().getActionCommand();
    }

    public DisplayStyle getStyle() {
        return StyleFactory.getStyle(this.getSelected());
    }

}
