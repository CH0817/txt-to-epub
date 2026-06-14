package tw.com.rex.txt2epub.factory;

import tw.com.rex.txt2epub.define.ChapterTypeEnum;
import tw.com.rex.txt2epub.handler.AbstractChapterFinder;
import tw.com.rex.txt2epub.handler.RegexChapterFinder;
import tw.com.rex.txt2epub.handler.WordCountChapterFinder;
import tw.com.rex.txt2epub.view.EpubConvertView;

/**
 * 章節查詢器工廠
 */
public class ChapterFinderFactory {

    public static AbstractChapterFinder getFinder(EpubConvertView view) {
        if (ChapterTypeEnum.REGEX.name().equals(view.getChapterFinderType())) {
            return new RegexChapterFinder(view);
        }

        return new WordCountChapterFinder(view);
    }

}
