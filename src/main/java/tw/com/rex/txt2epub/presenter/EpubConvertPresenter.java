package tw.com.rex.txt2epub.presenter;

import org.apache.commons.lang3.StringUtils;

import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.service.EpubService;
import tw.com.rex.txt2epub.view.EpubConvertView;

public class EpubConvertPresenter {

    private final EpubConvertView view;
    private final EpubService service;

    public EpubConvertPresenter(EpubConvertView view, EpubService service) {
        this.view = view;
        this.service = service;
    }

    /**
     * 執行 txt 轉 EPUB 流程
     */
    public void onStartConversion() {
        ConvertInfo convertInfo = view.getConvertInfo();
        if (verify(convertInfo)) {
            view.setProgressLoading(true);
            try {
                service.process(convertInfo);
                view.showSuccess();
            } catch (Exception e) {
                view.setProgressLoading(false);
                view.showErrorMessage("EPUB 轉換失敗！");
                e.printStackTrace();
            }
        }
    }

    /**
     * 必要資料驗證
     * 
     * @return 是否驗證通過
     */
    private boolean verify(ConvertInfo convertInfo) {
        StringBuilder error = new StringBuilder();
        if (StringUtils.isBlank(convertInfo.getTxtPath())) {
            error.append("請選擇txt檔案\n");
        }
        if (StringUtils.isBlank(convertInfo.getOutputPath())) {
            error.append("請選擇輸出路徑\n");
        }
        if (StringUtils.isNotBlank(error.toString())) {
            view.showErrorMessage(error.toString());
            return false;
        }
        return true;
    }

}
