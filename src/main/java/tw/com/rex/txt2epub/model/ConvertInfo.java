package tw.com.rex.txt2epub.model;

import lombok.Getter;
import tw.com.rex.txt2epub.creator.BookCreator;
import tw.com.rex.txt2epub.factory.StyleFactory;
import tw.com.rex.txt2epub.model.css.Style;
import tw.com.rex.txt2epub.panel.MainPanel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Getter
public class ConvertInfo {

    private final Book[] books;
    private final Path output;
    private final TempDirectory[] tempDirectories;
    private final Style style;

    public ConvertInfo(MainPanel panel) {
        this.books = BookCreator.create(panel);
        this.output = Paths.get(panel.getOutputPath());
        this.style = StyleFactory.getStyle(panel.getStyle());
        this.tempDirectories = createTempDirectories();
    }

    private TempDirectory[] createTempDirectories() {
        return Stream.of(books)
                .map(book -> new TempDirectory(book.getName()))
                .toArray(TempDirectory[]::new);
    }

}
