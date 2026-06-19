package tw.com.rex.txt2epub.utils;

import com.github.houbb.opencc4j.support.segment.impl.Segments;
import com.github.houbb.opencc4j.util.ZhConverterUtil;

public class SimplifiedToTraditionalConverter {

    public static String convert(String source) {
        String content = ZhConverterUtil.toTraditional(source, Segments.defaults());
        content = replaceSpecificChar(content);
        content = replaceSpecificNoun(content);
        return content;
    }

    /**
     * 簡轉繁後替換指定字
     *
     * @param content 簡轉繁後的 String
     * @return 替換後的 String
     */
    private static String replaceSpecificChar(String content) {
        return content.replaceAll("羣", "群")
                .replaceAll("孃", "娘")
                .replaceAll("裏", "裡")
                .replaceAll("纔", "才");
    }

    /**
     * 簡轉繁後替換指定名詞
     *
     * @param content 簡轉繁後的 String
     * @return 替換後的 String
     */
    private static String replaceSpecificNoun(String content) {
        return content.replaceAll("樑習", "梁習");
    }

}
