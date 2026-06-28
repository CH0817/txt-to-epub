package tw.com.rex.txt2epub.handler;

import java.util.List;

import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TxtContent;

public abstract class AbstractChapterFinder {

    /**
     * 取得章節內容
     *
     * @param convertInfo {@link ConvertInfo}
     * @return 章節內容
     */
    public abstract List<TxtContent> getTxtContents(ConvertInfo convertInfo);

    protected String getXhtmlName(int index) {
        StringBuilder chapter = new StringBuilder(String.valueOf(index));
        while (chapter.length() < 4) {
            chapter.insert(0, "0");
        }
        return "p-" + chapter.toString() + ".xhtml";
    }

}
