package tw.com.rex.txt2epub.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.utils.FileUtil;

/**
 * 字數查詢章節
 */
@Slf4j
public class WordCountChapterFinder extends AbstractChapterFinder {

    @Override
    public List<TxtContent> getTxtContents(ConvertInfo convertInfo) {
        String allContent = FileUtil.readTxtString(convertInfo);
        List<TxtContent> result = new ArrayList<>();
        int wordCount = Integer.parseInt(convertInfo.getChapterFinder());
        int chapterCount = getChapterCount(allContent, wordCount);

        for (int i = 0; i < chapterCount; i++) {
            String title = "第" + (i + 1) + "章";
            String xhtmlName = getXhtmlName(i + 1);
            int start = i * wordCount;
            int end = (i + 1) * wordCount;
            if (end > allContent.length()) {
                end = allContent.length();
            }
            String content = allContent.substring(start, end);
            List<String> contentList = Arrays.asList(content.split(System.lineSeparator()));
            result.add(new TxtContent(title, contentList, xhtmlName));
        }
        return result;
    }

    /**
     * 取得章節數量
     *
     * @param allContent 全部文字內容
     * @param wordCount  輸入的字數
     * @return 章節數量
     */
    private int getChapterCount(String allContent, int wordCount) {
        int result = allContent.length() / wordCount;
        if (allContent.length() % wordCount != 0) {
            result += 1;
        }
        return result;
    }

}
