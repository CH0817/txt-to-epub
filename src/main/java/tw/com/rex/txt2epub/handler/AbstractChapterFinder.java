package tw.com.rex.txt2epub.handler;

import java.util.List;

import lombok.AllArgsConstructor;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TxtContent;

@AllArgsConstructor
public abstract class AbstractChapterFinder {

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

}
