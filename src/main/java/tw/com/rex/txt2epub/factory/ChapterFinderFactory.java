package tw.com.rex.txt2epub.factory;

import tw.com.rex.txt2epub.define.ChapterTypeEnum;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.handler.AbstractChapterFinder;
import tw.com.rex.txt2epub.handler.RegexChapterFinder;
import tw.com.rex.txt2epub.handler.WordCountChapterFinder;

/**
 * 章節查詢器工廠
 */
public class ChapterFinderFactory {

    public static AbstractChapterFinder getFinder(MainFrame frame) {
        if(ChapterTypeEnum.REGEX.name().equals(frame.getChapterFinderType())) {
            return new RegexChapterFinder(frame);
        }

        return new WordCountChapterFinder(frame);
    }

}
