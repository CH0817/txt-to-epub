package tw.com.rex.txt2epub.factory;

import tw.com.rex.txt2epub.define.ChapterTypeEnum;
import tw.com.rex.txt2epub.handler.AbstractChapterFinder;
import tw.com.rex.txt2epub.handler.RegexChapterFinder;
import tw.com.rex.txt2epub.handler.WordCountChapterFinder;

/**
 * 章節查詢器工廠
 */
public class ChapterFinderFactory {

    public static AbstractChapterFinder getFinder(String chapterFinderType) {
        if (ChapterTypeEnum.REGEX.name().equals(chapterFinderType)) {
            return new RegexChapterFinder();
        }

        return new WordCountChapterFinder();
    }

}
