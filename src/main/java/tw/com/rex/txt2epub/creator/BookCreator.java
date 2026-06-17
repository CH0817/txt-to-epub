package tw.com.rex.txt2epub.creator;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

import tw.com.rex.txt2epub.factory.ChapterFinderFactory;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.utils.ListUtil;

public class BookCreator {

    public static Book[] create(ConvertInfo convertInfo) {
        List<TxtContent> txtContentList = ChapterFinderFactory.getFinder(convertInfo).getTxtContents();
        List<List<TxtContent>> episodes = ListUtil.separateDataList(txtContentList, 100);
        return IntStream.range(0, episodes.size())
                .mapToObj(i -> new Book(convertInfo, getBookName(convertInfo, i + 1), episodes.get(i)))
                .toArray(Book[]::new);
    }

    private static String getBookName(ConvertInfo convertInfo, int episode) {
        StringBuilder episodeBuilder = new StringBuilder(String.valueOf(episode));
        while (episodeBuilder.length() < 2) {
            episodeBuilder.insert(0, "0");
        }
        episodeBuilder.insert(0, "-");
        String fileName = Paths.get(convertInfo.getTxtPath()).toAbsolutePath().getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf(".")) + episodeBuilder;
    }

}
