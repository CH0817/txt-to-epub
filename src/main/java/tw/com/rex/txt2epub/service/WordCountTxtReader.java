package tw.com.rex.txt2epub.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;
import tw.com.rex.txt2epub.utils.TxtCharset;
import tw.com.rex.txt2epub.utils.SimplifiedToTraditionalConverter;
import tw.com.rex.txt2epub.utils.SpecialSymbolReplacer;

@Slf4j
public class WordCountTxtReader implements TxtRead<String> {

    @Override
    public String read(String txtPath, boolean isConvertSimplified) {
        for (Charset charset : TxtCharset.getCharsets()) {
            try {
                String result = Files.readString(Paths.get(txtPath), charset);
                result = SpecialSymbolReplacer.replace(result);
                result = (isConvertSimplified) ? SimplifiedToTraditionalConverter.convert(result) : result;
                return result;
            } catch (IOException e) {
                log.warn("{} 編碼取 txt 內容失敗", charset);
            }
        }

        throw new RuntimeException("讀取 txt 內容失敗");
    }

}
