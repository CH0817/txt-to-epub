package tw.com.rex.txt2epub.service.impl;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.CssClass;
import tw.com.rex.txt2epub.service.impl.base.AbstractEpubServiceImpl;

import java.nio.file.Path;

public class HorizontalEpubServiceImpl extends AbstractEpubServiceImpl {

    public HorizontalEpubServiceImpl(Book book, Path outputPath) {
        super(book, outputPath);
    }

    @Override
    protected CssClass createCssClass() {
        return CssClass.builder()
                       .typesettingClass("hltr")
                       .tocParagraphClass(StringUtils.EMPTY)
                       .contentH2Class("gfont align-center m-top-3em")
                       .contentFirstParagraphClass("m-top-2em indent-2em")
                       .contentParagraphClass(StringUtils.SPACE + "class=\"indent-2em\"")
                       .build();
    }

    @Override
    protected String getTypesettingCssFolder() {
        return "horizontal";
    }

}
