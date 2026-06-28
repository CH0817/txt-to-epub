package tw.com.rex.txt2epub.service;

public interface TxtRead<R> {

    R read(String txtPath, boolean isConvertSimplified);

}
