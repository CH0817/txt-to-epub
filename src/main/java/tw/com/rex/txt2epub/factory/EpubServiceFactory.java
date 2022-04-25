package tw.com.rex.txt2epub.factory;

import tw.com.rex.txt2epub.define.TypesettingEnum;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.service.EpubService;
import tw.com.rex.txt2epub.service.impl.HorizontalEpubServiceImpl;
import tw.com.rex.txt2epub.service.impl.VerticalEpubServiceImpl;

public class EpubServiceFactory {

    public static EpubService getEpubService(ConvertInfo convertInfo, String typesetting) {
        TypesettingEnum typesettingEnum = TypesettingEnum.valueOf(typesetting);
        switch (typesettingEnum) {
            case VERTICAL:
                return new VerticalEpubServiceImpl(convertInfo);
            case HORIZONTAL:
                return new HorizontalEpubServiceImpl(convertInfo);
            default:
                throw new RuntimeException("get EpubService from " + typesetting + " failure!");
        }
    }

}
