package tw.com.rex.txt2epub.utils;

public class SpecialSymbolReplacer {

    /**
     * 替換特殊符號
     *
     * @param source 內文
     * @return 替換後的內文
     */
    public static String replace(String source) {
        return source.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&apos;");
    }

}
