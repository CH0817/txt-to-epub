package tw.com.rex.txt2epub.handler;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.utils.FileUtil;

/**
 * 正則表達式查詢章節
 */
@Slf4j
public class RegexChapterFinder extends AbstractChapterFinder {

    @Override
    public List<TxtContent> getTxtContents(ConvertInfo convertInfo) {
        List<String> allLines = FileUtil.readTxtLines(convertInfo);
        List<Integer> titleIndexes = getTitleIndexes(allLines, convertInfo.getChapterFinder());
        return createTxtContentList(allLines, titleIndexes);
    }

    private List<Integer> getTitleIndexes(List<String> allLines, String chapterFinder) {
        return IntStream.range(0, allLines.size())
                .filter(i -> StringUtils.isNotBlank(allLines.get(i)))
                .filter(i -> StringUtils.trimToEmpty(allLines.get(i)).matches(chapterFinder))
                .boxed()
                .collect(toList());
    }

    private List<TxtContent> createTxtContentList(List<String> allLines, List<Integer> titleIndexes) {
        List<TxtContent> result = new ArrayList<>();
        for (int i = 0; i < titleIndexes.size(); i++) {
            String title = allLines.get(titleIndexes.get(i));
            String xhtmlName = getXhtmlName(i + 1);
            int startIndex = titleIndexes.get(i) + 1;
            int endIndex = (i == titleIndexes.size() - 1) ? allLines.size() : titleIndexes.get(i + 1);
            List<String> contentList = getContentList(allLines, startIndex, endIndex);
            result.add(new TxtContent(title, contentList, xhtmlName));
        }
        return result;
    }

    private List<String> getContentList(List<String> allLines, int startIndex, int endIndex) {
        return IntStream.range(startIndex, endIndex)
                .mapToObj(allLines::get)
                .filter(StringUtils::isNotBlank)
                .map(s -> s.replaceAll("　", ""))
                .collect(toList());
    }

}
