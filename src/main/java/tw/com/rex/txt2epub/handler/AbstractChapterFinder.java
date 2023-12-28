package tw.com.rex.txt2epub.handler;

import com.github.houbb.opencc4j.support.segment.impl.Segments;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import lombok.AllArgsConstructor;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.TxtContent;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractChapterFinder {

    /**
     * 用來讀取檔案的 encode
     */
    protected final Charset[] charsets = {StandardCharsets.UTF_8,
            Charset.forName("Big5"),
            Charset.forName("GBK"),
            StandardCharsets.UTF_16};

    protected MainFrame frame;

    /**
     * 取得章節內容
     *
     * @return 章節內容
     */
    public abstract List<TxtContent> getTxtContents();

    protected String getXhtmlName(int index) {
        return "p-" + getChapter(index) + ".xhtml";
    }

    protected String getChapter(int chapterIndex) {
        StringBuilder chapter = new StringBuilder(String.valueOf(chapterIndex));
        while (chapter.length() < 4) {
            chapter.insert(0, "0");
        }
        return chapter.toString();
    }

    /**
     * 簡轉繁
     *
     * @param origin 原始內容
     * @return 繁體
     */
    protected String convertSimplified(String origin) {
        if (this.frame.isConvertSimplified()) {
            String content = ZhConverterUtil.toTraditional(origin, Segments.defaults());
            content = replaceSpecificChar(content);
            content = replaceSpecificNoun(content);
            return content;
        }
        return origin;
    }

    /**
     * 簡轉繁後替換指定字
     *
     * @param content 簡轉繁後的 String
     * @return 替換後的 String
     */
    private String replaceSpecificChar(String content) {
        if (!this.frame.isConvertSimplified()) {
            return content;
        }
        return content.replaceAll("羣", "群")
                .replaceAll("孃", "娘")
                .replaceAll("裏", "裡")
                .replaceAll("纔", "才");
    }

    /**
     * 簡轉繁後替換指定名詞
     *
     * @param content 簡轉繁後的 String
     * @return 替換後的 String
     */
    private String replaceSpecificNoun(String content) {
        if (!this.frame.isConvertSimplified()) {
            return content;
        }
        return content.replaceAll("樑習", "梁習");
    }

    /**
     * 替換特殊符號
     *
     * @param content 內文
     * @return 替換後的內文
     */
    protected String replaceSpecialSymbol(String content) {
        return content.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&apos;");
    }

}
