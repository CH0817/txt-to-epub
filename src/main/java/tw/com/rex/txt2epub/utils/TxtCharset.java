package tw.com.rex.txt2epub.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TxtCharset {

    public static Charset[] getCharsets() {
        Charset[] result = { StandardCharsets.UTF_8,
                Charset.forName("Big5"),
                Charset.forName("GBK"),
                StandardCharsets.UTF_16 };
        return result;
    }

}
