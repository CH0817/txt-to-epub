package tw.com.rex.txt2epub.handler;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.TxtContent;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * 正則表達式查詢章節
 */
public class RegexChapterFinder extends AbstractChapterFinder {

    public RegexChapterFinder(MainFrame frame) {
        super(frame);
    }

    @Override
    public List<TxtContent> getTxtContents() {
        List<String> allLines = getAllLines();
        List<Integer> titleIndexes = getTitleIndexes(allLines);
        return createTxtContentList(allLines, titleIndexes);
    }

    private List<String> getAllLines() {
        for (String charset : super.charsets) {
            try {
                return Files.readAllLines(Paths.get(this.frame.getTxtFilePath()), Charset.forName(charset)).stream()
                        .map(super::replaceSpecialSymbol)
                        .map(super::convertSimplified)
                        .collect(toList());
            } catch (IOException e) {
                System.out.println(charset + " 編碼取 txt 內容失敗");
            }
        }
        throw new RuntimeException("取得 txt 內容失敗");
    }

    private List<Integer> getTitleIndexes(List<String> allLines) {
        return IntStream.range(0, allLines.size())
                .filter(i -> StringUtils.isNotBlank(allLines.get(i)))
                .filter(i -> StringUtils.trimToEmpty(allLines.get(i)).matches(super.frame.getChapterFinder()))
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
