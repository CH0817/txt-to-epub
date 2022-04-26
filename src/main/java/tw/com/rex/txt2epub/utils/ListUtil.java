package tw.com.rex.txt2epub.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtil {

    public static <T> List<List<T>> separateDataList(List<T> dataList, final int separateNumber) {
        int remainder = dataList.size() % separateNumber;
        int maxLoop = dataList.size() / separateNumber;

        if (remainder != 0) {
            maxLoop += 1;
        }

        return Stream.iterate(0, n -> n + 1).limit(maxLoop)
                     .map(loop -> dataList.stream()
                                          .skip((long) loop * separateNumber)
                                          .limit(separateNumber)//
                                          .collect(Collectors.toList()))
                     .collect(Collectors.toList());
    }

}