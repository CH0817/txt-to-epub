package tw.com.rex.txt2epub.service;

import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import tw.com.rex.txt2epub.utils.TxtCharset;
import tw.com.rex.txt2epub.utils.SimplifiedToTraditionalConverter;
import tw.com.rex.txt2epub.utils.SpecialSymbolReplacer;

@Slf4j
public class RegexTxtReader implements TxtRead<List<String>> {

    @Override
    public List<String> read(String txtPath, boolean isConvertSimplified) {
        for (Charset charset : TxtCharset.getCharsets()) {
            try {
                return Files.readAllLines(Paths.get(txtPath), charset).stream()
                        .map(SpecialSymbolReplacer::replace)
                        .map(origin -> (isConvertSimplified)
                                ? SimplifiedToTraditionalConverter.convert(origin)
                                : origin)
                        .collect(toList());
            } catch (IOException e) {
                log.warn("{} 編碼取 txt 內容失敗", charset);
            }
        }
        throw new RuntimeException("讀取 txt 內容失敗");
    }

}
