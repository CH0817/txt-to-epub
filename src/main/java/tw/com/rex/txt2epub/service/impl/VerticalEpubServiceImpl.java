package tw.com.rex.txt2epub.service.impl;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.service.impl.base.AbstractEpubServiceImpl;

import java.nio.file.Path;

public class VerticalEpubServiceImpl extends AbstractEpubServiceImpl {

    public VerticalEpubServiceImpl(Book book, Path outputPath) {
        super(book, outputPath);
    }

    @Override
    protected String getTypesettingClass() {
        return "vrtl";
    }

    @Override
    protected String getTocParagraphClass() {
        return "m-top-2em";
    }

    @Override
    protected String getContentH2Class() {
        return "gfont p-top-2em color-dimgray";
    }

    @Override
    protected String getContentFirstParagraphClass() {
        return "m-right-5em";
    }

    @Override
    protected String getContentParagraphClass() {
        return StringUtils.EMPTY;
    }

    @Override
    protected String getTypesettingCssFolder() {
        return "vertical";
    }

}
