package tw.com.rex.txt2epub.define;

/**
 * 章節判斷類型 enum
 */
public enum ChapterTypeEnum {

    REGEX("正則"), WORD_COUNT("字數");

    public final String text;

    ChapterTypeEnum(String text) {
        this.text = text;
    }
}
