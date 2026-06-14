package tw.com.rex.txt2epub.presenter;

import java.awt.Desktop;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.service.EpubService;
import tw.com.rex.txt2epub.view.EpubConvertView;

public class EpubConvertPresenter {

    private final EpubConvertView view;
    // private final EpubConverterModel model; // 負責實際轉檔邏輯的類別

    public EpubConvertPresenter(EpubConvertView view) {
        this.view = view;
    }

    // public EpubConvertPresenter(EpubConvertView view, EpubConverterModel model) {
    // this.view = view;
    // this.model = model;
    // }

    public void onStartConversion() {
        if (verify()) {
            try {
                new EpubService(new ConvertInfo(view)).process();
                int input = JOptionPane.showOptionDialog((JFrame) view, "轉換成功", "訊息", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION) {
                    Desktop.getDesktop().open(Paths.get(this.view.getOutputPath()).toFile());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(((JFrame) view), e.getMessage());
            }
        }
    }

    private boolean verify() {
        StringBuilder error = new StringBuilder();
        if (StringUtils.isBlank(view.getTxtFilePath())) {
            error.append("請選擇txt檔案\n");
        }
        if (StringUtils.isBlank(view.getOutputPath())) {
            error.append("請選擇輸出路徑\n");
        }
        if (StringUtils.isNotBlank(error.toString())) {
            JOptionPane.showMessageDialog(((JFrame) view), error);
            return false;
        }
        return true;
    }

}
