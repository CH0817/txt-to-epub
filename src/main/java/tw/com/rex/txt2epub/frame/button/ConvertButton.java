package tw.com.rex.txt2epub.frame.button;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.panel.MainPanel;
import tw.com.rex.txt2epub.service.EpubService;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class ConvertButton extends JButton {

    private final MainPanel panel;

    public ConvertButton(MainPanel panel) {
        super("開始轉換");
        this.panel = panel;
        super.addActionListener(e -> convertToEpub());
    }

    private void convertToEpub() {
        if (verify()) {
            try {
                new EpubService(new ConvertInfo(panel)).process();
                int input = JOptionPane.showOptionDialog(panel, "轉換成功", "訊息", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION) {
                    Desktop.getDesktop().open(Paths.get(this.panel.getOutputPath()).toFile());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panel, e.getMessage());
            }
        }
    }

    private boolean verify() {
        StringBuilder error = new StringBuilder();
        if (StringUtils.isBlank(panel.getTxtFilePath())) {
            error.append("請選擇txt檔案\n");
        }
        if (StringUtils.isBlank(panel.getOutputPath())) {
            error.append("請選擇輸出路徑\n");
        }
        if (StringUtils.isNotBlank(error.toString())) {
            JOptionPane.showMessageDialog(panel, error);
            return false;
        }
        return true;
    }

}
