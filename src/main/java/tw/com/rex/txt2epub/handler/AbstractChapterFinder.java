package tw.com.rex.txt2epub.handler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import lombok.AllArgsConstructor;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.utils.SimplifiedToTraditionalConverter;

@AllArgsConstructor
public abstract class AbstractChapterFinder {

    /**
     * 用來讀取檔案的 encode
     */
    protected final Charset[] charsets = { StandardCharsets.UTF_8,
            Charset.forName("Big5"),
            Charset.forName("GBK"),
            StandardCharsets.UTF_16 };

    protected ConvertInfo convertInfo;

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
        return (convertInfo.isConvertSimplified()) ? SimplifiedToTraditionalConverter.convert(origin) : origin;
    }

}
