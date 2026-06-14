package tw.com.rex.txt2epub.view;

public interface EpubConvertView {

    // ---- 獲取使用者輸入的資料 (Getters) ----
    String getTxtFilePath();

    String getOutputPath();

    String getCoverPath();

    String getAuthor();

    String getPublisher();

    public String getStyle();

    public String getChapterFinderType();

    public String getChapterFinder();

    public boolean isConvertSimplified();

    // ---- 控制畫面的顯示與反饋 (Actions/Setters) ----

    void showMessage(String message);

    void showErrorMessage(String error);

    void setProgressLoading(boolean isLoading); // 控制按鈕是否可點擊或顯示轉圈

}
