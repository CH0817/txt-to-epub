package tw.com.rex.txt2epub.service;

import com.github.houbb.opencc4j.util.ZhTwConverterUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

    private String filePath;

    public List<TxtContent> getTxtContentList() {
        List<String> allLines = getAllLines();
        List<Integer> titleIndexes = getTitleIndexes(allLines);
        return createTxtContentList(allLines, titleIndexes);
    }

    private List<String> getAllLines() {
        String[] charsets = {"UTF-8", "Big5", "GBK"};
        for (String charset : charsets) {
            try {
                return Files.readAllLines(Paths.get(filePath), Charset.forName(charset))
                            .stream()
                            .map(ZhTwConverterUtil::toTraditional)
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
                        .filter(i -> StringUtils.trimToEmpty(allLines.get(i)).matches("^第\\d{1,4}章 .*$"))
                        .boxed()
                        .collect(toList());
    }

    private List<TxtContent> createTxtContentList(List<String> allLines, List<Integer> titleIndexes) {
        List<TxtContent> result = new ArrayList<>();
        for (int i = 0; i < titleIndexes.size(); i++) {
            String title = allLines.get(titleIndexes.get(i));
            int startIndex = titleIndexes.get(i) + 1;
            int endIndex = (i == titleIndexes.size() - 1) ? allLines.size() : titleIndexes.get(i + 1);
            List<String> contentList = getContentList(allLines, startIndex, endIndex);
            result.add(new TxtContent(title, contentList));
        }
        return result;
    }

    private List<String> getContentList(List<String> allLines, int startIndex, int endIndex) {
        return IntStream.range(startIndex, endIndex)
                        .mapToObj(allLines::get)
                        .filter(StringUtils::isNotBlank)
                        .collect(toList());
    }

}