package tw.com.rex.txt2epub;

import javax.swing.SwingUtilities;

import tw.com.rex.txt2epub.frame.EpubConvertFrame;
import tw.com.rex.txt2epub.presenter.EpubConvertPresenter;
import tw.com.rex.txt2epub.service.EpubService;

public class TxtToEpubApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. 建立 View 與 Model
            EpubConvertFrame view = new EpubConvertFrame();
            // EpubConverterModel model = new EpubConverterModel(); // 你的核心轉檔邏輯

            // 2. 建立 Presenter 並相互綁定
            EpubConvertPresenter presenter = new EpubConvertPresenter(view, new EpubService());
            view.setPresenter(presenter);
        });
    }

}
