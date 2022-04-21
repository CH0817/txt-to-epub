package tw.com.rex.txt2epub.service.impl;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.service.impl.base.AbstractEpubServiceImpl;

import java.nio.file.Path;

public class HorizontalEpubServiceImpl extends AbstractEpubServiceImpl {

    public HorizontalEpubServiceImpl(Book book, Path outputPath) {
        super(book, outputPath);
    }

    @Override
    protected String getTypesettingClass() {
        return "hltr";
    }

    @Override
    protected String getTocParagraphClass() {
        return StringUtils.EMPTY;
    }

    @Override
    protected String getContentH2Class() {
        return "gfont align-center m-top-3em";
    }

    @Override
    protected String getContentFirstParagraphClass() {
        return "m-top-5em indent-2em";
    }

    @Override
    protected String getContentParagraphClass() {
        return StringUtils.SPACE + "class=\"indent-2em\"";
    }

    @Override
    protected String getTypesettingCssFolder() {
        return "horizontal";
    }

}
