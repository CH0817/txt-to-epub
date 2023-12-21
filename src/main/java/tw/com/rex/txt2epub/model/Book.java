package tw.com.rex.txt2epub.model;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.service.TxtHandlerService;
import tw.com.rex.txt2epub.utils.ListUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Getter
public class Book {

    private final String UNKNOWN = "未知";

    private final String name;
    private final String author;
    private final String publisher;
    private final List<TxtContent> txtContentList;
    private final Path cover;

    private Book(MainFrame frame, String name, List<TxtContent> txtContentList) {
        this.name = name;
        this.cover = Paths.get(frame.getCoverPath().getText());
        this.author = frame.getAuthorField().getText();
        this.publisher = frame.getPublishingHouseField().getText();
        this.txtContentList = txtContentList;
    }

    public static Book[] create(MainFrame frame) {
        List<TxtContent> txtContentList = getTxtContentList(frame);
        List<List<TxtContent>> episodes = ListUtil.separateDataList(txtContentList, 100);
        return IntStream.range(0, episodes.size())
                .mapToObj(i -> new Book(frame, getBookName(frame, i + 1), episodes.get(i)))
                .toArray(Book[]::new);
    }

    private static String getBookName(MainFrame frame, int episode) {
        StringBuilder episodeBuilder = new StringBuilder(String.valueOf(episode));
        while (episodeBuilder.length() < 2) {
            episodeBuilder.insert(0, "0");
        }
        episodeBuilder.insert(0, "-");
        String fileName = Paths.get(frame.getTxtChooser().getLabel().getText()).toAbsolutePath().getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf(".")) + episodeBuilder;
    }

    private static List<TxtContent> getTxtContentList(MainFrame frame) {
        return new TxtHandlerService(frame.getTxtChooser().getLabel().getText()).getTxtContentList();
    }

    public String getAuthor() {
        return StringUtils.isNotBlank(author) ? author : UNKNOWN;
    }

    public String getPublisher() {
        return StringUtils.isNotBlank(publisher) ? publisher : UNKNOWN;
    }

    public boolean hasCover() {
        return Objects.nonNull(cover) && Files.exists(cover) && Files.isRegularFile(cover);
    }

}
