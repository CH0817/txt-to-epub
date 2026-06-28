package tw.com.rex.txt2epub.factory;

import java.util.List;

import tw.com.rex.txt2epub.service.RegexTxtReader;
import tw.com.rex.txt2epub.service.TxtRead;
import tw.com.rex.txt2epub.service.WordCountTxtReader;

public class TxtReadFactory {

    public static TxtRead<String> createWordCountTxtReader() {
        return new WordCountTxtReader();
    }

    public static TxtRead<List<String>> createRegexTxtReader() {
        return new RegexTxtReader();
    }

}
