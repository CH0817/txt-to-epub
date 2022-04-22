package tw.com.rex.txt2epub.service;

import lombok.AllArgsConstructor;
import tw.com.rex.txt2epub.model.Book;

@AllArgsConstructor
public class CoverXhtmlService {

    private Book book;

    public String generate() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                System.lineSeparator() +
                "<!DOCTYPE html>" +
                System.lineSeparator() +
                "<html" +
                System.lineSeparator() +
                "xmlns=\"http://www.w3.org/1999/xhtml\"" +
                System.lineSeparator() +
                "xmlns:epub=\"http://www.idpf.org/2007/ops\"" +
                System.lineSeparator() +
                "xml:lang=\"zh-TW\" lang=\"zh-TW\"" +
                System.lineSeparator() +
                "class=\"hltr\"" +
                System.lineSeparator() +
                ">" +
                System.lineSeparator() +
                "<head>" +
                System.lineSeparator() +
                "<meta charset=\"UTF-8\"/>" +
                System.lineSeparator() +
                "<meta name=\"viewport\" content=\"width=1444,height=2048\" />" +
                System.lineSeparator() +
                "<style type=\"text/css\">" +
                System.lineSeparator() +
                "html, body { margin: 0; padding: 0; width: 100%; height: 100%;}" +
                System.lineSeparator() +
                "</style>" +
                System.lineSeparator() +
                "<title>" +
                book.getName() +
                "</title>" +
                System.lineSeparator() +
                "</head>" +
                System.lineSeparator() +
                "<body epub:type=\"cover\" class=\"p-cover\">" +
                System.lineSeparator() +
                "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"" +
                System.lineSeparator() +
                "xmlns:xlink=\"http://www.w3.org/1999/xlink\"" +
                System.lineSeparator() +
                "width=\"100%\" height=\"100%\" viewBox=\"0 0 1444 2048\">" +
                System.lineSeparator() +
                "<image width=\"1444\" height=\"2048\" xlink:href=\"../image/" +
                book.getCover().getFileName() +
                "\"/>" +
                System.lineSeparator() +
                "</svg>" +
                System.lineSeparator() +
                "</body>" +
                System.lineSeparator() +
                "</html>";
    }

}
