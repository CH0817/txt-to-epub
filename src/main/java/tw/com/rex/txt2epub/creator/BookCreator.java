package tw.com.rex.txt2epub.creator;

import tw.com.rex.txt2epub.factory.ChapterFinderFactory;
import tw.com.rex.txt2epub.frame.MainFrame;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.utils.ListUtil;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class BookCreator {

    public static Book[] create(MainFrame frame) {
        List<TxtContent> txtContentList = ChapterFinderFactory.getFinder(frame).getTxtContents();
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
        String fileName = Paths.get(frame.getTxtFilePath()).toAbsolutePath().getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf(".")) + episodeBuilder;
    }

}
