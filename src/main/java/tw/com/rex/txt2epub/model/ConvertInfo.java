package tw.com.rex.txt2epub.model;

import lombok.Getter;
import tw.com.rex.txt2epub.factory.StyleFactory;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.css.Style;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Getter
public class ConvertInfo {

    private final Book[] books;
    private final Path output;
    private final TempDirectory[] tempDirectories;
    private final Style style;
    private final MainFrame frame;

    public ConvertInfo(MainFrame frame) {
        this.frame = frame;
        this.books = Book.create(frame);
        this.output = Paths.get(frame.getOutputPathChooser().getLabel().getText());
        this.style = StyleFactory.getStyle(frame.getTypeSettingPanel().getStyle());
        this.tempDirectories = createTempDirectories();
    }

    private TempDirectory[] createTempDirectories() {
        return Stream.of(books)
                     .map(book -> new TempDirectory(book.getName()))
                     .toArray(TempDirectory[]::new);
    }

}
