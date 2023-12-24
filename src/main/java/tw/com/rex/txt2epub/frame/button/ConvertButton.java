package tw.com.rex.txt2epub.frame.button;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.service.EpubService;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class ConvertButton extends JButton {

    private final MainFrame frame;

    public ConvertButton(MainFrame frame) {
        super("開始轉換");
        this.frame = frame;
        super.addActionListener(e -> convertToEpub());
    }

    private void convertToEpub() {
        if (verify()) {
            try {
                new EpubService(new ConvertInfo(this.frame)).process();
                int input = JOptionPane.showOptionDialog(this.frame.getPane(), "轉換成功", "訊息", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION) {
                    Desktop.getDesktop().open(Paths.get(this.frame.getOutputPath()).toFile());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this.frame.getPane(), e.getMessage());
            }
        }
    }

    private boolean verify() {
        StringBuilder error = new StringBuilder();
        if (StringUtils.isBlank(this.frame.getTxtFilePath())) {
            error.append("請選擇txt檔案\n");
        }
        if (StringUtils.isBlank(this.frame.getOutputPath())) {
            error.append("請選擇輸出路徑\n");
        }
        if (StringUtils.isNotBlank(error.toString())) {
            JOptionPane.showMessageDialog(this.frame.getPane(), error);
            return false;
        }
        return true;
    }

}
