package tw.com.rex.txt2epub.factory;

import tw.com.rex.txt2epub.define.ChapterTypeEnum;
import tw.com.rex.txt2epub.handler.AbstractChapterFinder;
import tw.com.rex.txt2epub.handler.RegexChapterFinder;
import tw.com.rex.txt2epub.handler.WordCountChapterFinder;
import tw.com.rex.txt2epub.panel.MainPanel;

/**
 * 章節查詢器工廠
 */
public class ChapterFinderFactory {

    public static AbstractChapterFinder getFinder(MainPanel panel) {
        if(ChapterTypeEnum.REGEX.name().equals(panel.getChapterFinderType())) {
            return new RegexChapterFinder(panel);
        }

        return new WordCountChapterFinder(panel);
    }

}
