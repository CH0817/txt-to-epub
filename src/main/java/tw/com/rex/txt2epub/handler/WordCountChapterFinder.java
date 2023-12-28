package tw.com.rex.txt2epub.handler;

import lombok.extern.slf4j.Slf4j;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.TxtContent;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字數查詢章節
 */
@Slf4j
public class WordCountChapterFinder extends AbstractChapterFinder {

    public WordCountChapterFinder(MainFrame frame) {
        super(frame);
    }

    @Override
    public List<TxtContent> getTxtContents() {
        List<TxtContent> result = new ArrayList<>();
        String allContent = getAllContent();
        int wordCount = Integer.parseInt(super.frame.getChapterFinder());
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
     * 取得 txt 檔案內容
     *
     * @return txt 檔案內容
     */
    private String getAllContent() {
        for (Charset charset : super.charsets) {
            try {
                String allContents = Files.readString(Paths.get(super.frame.getTxtFilePath()), charset);
                allContents = super.replaceSpecialSymbol(allContents);
                allContents = super.convertSimplified(allContents);
                return allContents;
            } catch (IOException e) {
                log.warn("{} 編碼取 txt 內容失敗", charset);
            }
        }
        throw new RuntimeException("取得 txt 內容失敗");
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
