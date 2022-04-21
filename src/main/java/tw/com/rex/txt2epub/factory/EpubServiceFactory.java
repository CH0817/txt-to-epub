package tw.com.rex.txt2epub.factory;

import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.service.EpubService;
import tw.com.rex.txt2epub.service.impl.HorizontalEpubServiceImpl;
import tw.com.rex.txt2epub.service.impl.VerticalEpubServiceImpl;

import java.nio.file.Path;

public class EpubServiceFactory {

    public static EpubService getEpubService(Book book, Path outputPath, String typesetting) {
        TypesettingEnum typesettingEnum = TypesettingEnum.valueOf(typesetting);
        switch (typesettingEnum) {
            case VERTICAL:
                return new VerticalEpubServiceImpl(book, outputPath);
            case HORIZONTAL:
                return new HorizontalEpubServiceImpl(book, outputPath);
            default:
                throw new RuntimeException("get EpubService from " + typesetting + " failure!");
        }
    }

}
