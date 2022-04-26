package tw.com.rex.txt2epub.model;

import lombok.Getter;
import tw.com.rex.txt2epub.factory.StyleFactory;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.css.Style;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class ConvertInfo {

    private final Book book;
    private final Path output;
    private final TempDirectory tempDirectory;
    private final Style style;
    private final MainFrame frame;

    public ConvertInfo(MainFrame frame) {
        this.frame = frame;
        this.book = new Book(frame);
        this.output = Paths.get(frame.getOutputFilePath().getText());
        this.style = StyleFactory.getStyle(frame.getTypesetting());
        this.tempDirectory = new TempDirectory(book.getName());
    }

}
