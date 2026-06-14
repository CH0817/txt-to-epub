package tw.com.rex.txt2epub.presenter;

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
        // TODO 轉換邏輯
    }

}
