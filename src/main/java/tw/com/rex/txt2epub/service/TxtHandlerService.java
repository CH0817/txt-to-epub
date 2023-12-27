package tw.com.rex.txt2epub.service;

import com.github.houbb.opencc4j.support.segment.impl.Segments;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
public class TxtHandlerService {

    private MainFrame frame;

    public List<TxtContent> getTxtContentList() {
        List<String> allLines = getAllLines();
        List<Integer> titleIndexes = getTitleIndexes(allLines);
        return createTxtContentList(allLines, titleIndexes);
    }

    private List<String> getAllLines() {
        String[] charsets = {"UTF-8", "Big5", "GBK"};
        for (String charset : charsets) {
            try {
                return Files.readAllLines(Paths.get(this.frame.getTxtFilePath()), Charset.forName(charset)).stream()
                        .map(this::convertSimplified)
                        .collect(toList());
            } catch (IOException e) {
                System.out.println(charset + " 編碼取 txt 內容失敗");
            }
        }
        throw new RuntimeException("取得 txt 內容失敗");
    }

    private String convertSimplified(String origin) {
        if (this.frame.isConvertSimplified()) {
            return ZhConverterUtil.toTraditional(origin, Segments.defaults());
        }
        return origin;
    }

    private List<Integer> getTitleIndexes(List<String> allLines) {
        if ("regex".equals(this.frame.getChapterFinderType())) {
            return IntStream.range(0, allLines.size())
                    .filter(i -> StringUtils.isNotBlank(allLines.get(i)))
                    .filter(i -> StringUtils.trimToEmpty(allLines.get(i)).matches(this.frame.getChapterFinder()))
                    .boxed()
                    .collect(toList());
        } else {
            throw new RuntimeException("字數分章節未完成");
        }
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

    private String getXhtmlName(int index) {
        return "p-" + getChapter(index) + ".xhtml";
    }

    private String getChapter(int chapterIndex) {
        StringBuilder chapter = new StringBuilder(String.valueOf(chapterIndex));
        while (chapter.length() < 4) {
            chapter.insert(0, "0");
        }
        return chapter.toString();
    }

    private List<String> getContentList(List<String> allLines, int startIndex, int endIndex) {
        return IntStream.range(startIndex, endIndex)
                .mapToObj(allLines::get)
                .filter(StringUtils::isNotBlank)
                .map(s -> s.replaceAll("　", ""))
                .map(this::replaceSpecialSymbol)
                .map(this::replaceSpecificChar)
                .map(this::replaceSpecificNoun)
                .collect(toList());
    }

    private String replaceSpecificChar(String content) {
        if (!this.frame.isConvertSimplified()) {
            return content;
        }
        return content.replaceAll("羣", "群")
                .replaceAll("孃", "娘")
                .replaceAll("裏", "裡")
                .replaceAll("纔", "才");
    }

    private String replaceSpecialSymbol(String content) {
        return content.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&apos;");
    }

    private String replaceSpecificNoun(String content) {
        if (!this.frame.isConvertSimplified()) {
            return content;
        }
        return content.replaceAll("樑習", "梁習");
    }

}
