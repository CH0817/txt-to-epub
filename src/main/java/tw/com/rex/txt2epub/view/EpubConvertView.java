package tw.com.rex.txt2epub.view;

/**
 * 畫面取得/操作 interface
 */
public interface EpubConvertView {

    // ---- 獲取使用者輸入的資料 (Getters) ----

    /**
     * 取得 txt 檔案路徑
     */
    String getTxtFilePath();

    /**
     * 取得 EPUB 輸出路徑
     */
    String getOutputPath();

    /**
     * 取得 EPUB 封面圖片路徑
     */
    String getCoverPath();

    /**
     * 取得作者
     */
    String getAuthor();

    /**
     * 取得出版社
     */
    String getPublisher();

    public String getStyle();

    public String getChapterFinderType();

    public String getChapterFinder();

    public boolean isConvertSimplified();

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
