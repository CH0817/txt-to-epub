package tw.com.rex.txt2epub.view;

import tw.com.rex.txt2epub.model.ConvertInfo;

/**
 * 畫面取得/操作 interface
 */
public interface EpubConvertView {

    ConvertInfo getConvertInfo();

    /**
     * 顯示轉換成功訊息
     */
    void showSuccess();

    /**
     * 顯示提示訊息
     * 
     * @param message 訊息
     */
    void showMessage(String message);

    /**
     * 顯示錯誤訊息
     * 
     * @param error 訊息
     */
    void showErrorMessage(String error);

    /**
     * 控制 "開始轉換" 是否能使用
     * 
     * @param isLoading 是否正在轉換中
     */
    void setProgressLoading(boolean isLoading);

}
