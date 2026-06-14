package tw.com.rex.txt2epub.presenter;

import org.apache.commons.lang3.StringUtils;

import tw.com.rex.txt2epub.service.EpubService;
import tw.com.rex.txt2epub.view.EpubConvertView;

public class EpubConvertPresenter {

    private final EpubConvertView view;
    private final EpubService service;

    public EpubConvertPresenter(EpubConvertView view, EpubService service) {
        this.view = view;
        this.service = service;
    }

    public void onStartConversion() {
        if (verify()) {
            this.view.setProgressLoading(true);
            try {
                this.service.process(view);
                this.view.showSuccess();
            } catch (Exception e) {
                this.view.setProgressLoading(false);
                this.view.showErrorMessage("EPUB 轉換失敗！");
                e.printStackTrace();
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
            this.view.showErrorMessage(error.toString());
            return false;
        }
        return true;
    }

}
