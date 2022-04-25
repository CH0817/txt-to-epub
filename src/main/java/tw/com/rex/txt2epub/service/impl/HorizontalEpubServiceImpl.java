package tw.com.rex.txt2epub.service.impl;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.CssClass;
import tw.com.rex.txt2epub.service.impl.base.AbstractEpubServiceImpl;

public class HorizontalEpubServiceImpl extends AbstractEpubServiceImpl {

    public HorizontalEpubServiceImpl(ConvertInfo convertInfo) {
        super(convertInfo);
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
