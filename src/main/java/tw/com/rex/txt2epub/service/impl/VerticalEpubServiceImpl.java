package tw.com.rex.txt2epub.service.impl;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.CssClass;
import tw.com.rex.txt2epub.service.impl.base.AbstractEpubServiceImpl;

import java.nio.file.Path;

public class VerticalEpubServiceImpl extends AbstractEpubServiceImpl {

    public VerticalEpubServiceImpl(Book book, Path outputPath) {
        super(book, outputPath);
    }

    @Override
    protected CssClass createCssClass() {
        return CssClass.builder()
                       .typesettingClass("vrtl")
                       .tocParagraphClass("m-top-2em")
                       .contentH2Class("gfont p-top-2em color-dimgray")
                       .contentFirstParagraphClass("m-right-5em")
                       .contentParagraphClass(StringUtils.EMPTY)
                       .build();
    }

    @Override
    protected String getTypesettingCssFolder() {
        return "vertical";
    }

}
