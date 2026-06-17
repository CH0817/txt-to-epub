package tw.com.rex.txt2epub.factory;

import tw.com.rex.txt2epub.define.ChapterTypeEnum;
import tw.com.rex.txt2epub.handler.AbstractChapterFinder;
import tw.com.rex.txt2epub.handler.RegexChapterFinder;
import tw.com.rex.txt2epub.handler.WordCountChapterFinder;
import tw.com.rex.txt2epub.model.ConvertInfo;

/**
 * 章節查詢器工廠
 */
public class ChapterFinderFactory {

    public static AbstractChapterFinder getFinder(ConvertInfo convertInfo) {
        if (ChapterTypeEnum.REGEX.name().equals(convertInfo.getChapterFinderType())) {
            return new RegexChapterFinder(convertInfo);
        }

        return new WordCountChapterFinder(convertInfo);
    }

}
