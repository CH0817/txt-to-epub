package tw.com.rex.txt2epub.service;

import lombok.AllArgsConstructor;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.CssClass;
import tw.com.rex.txt2epub.utils.FileUtil;

import java.nio.file.Path;
import java.util.Map;

@AllArgsConstructor
public class TableOfContentsService {

    private Book book;
    private CssClass css;
    private Map<String, String> contentXhtmlMap;
    private Path output;

    public void generate() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
          .append(System.lineSeparator())
          .append("<!DOCTYPE html>")
          .append(System.lineSeparator())
          .append("<html")
          .append(System.lineSeparator())
          .append("xmlns=\"http://www.w3.org/1999/xhtml\"")
          .append(System.lineSeparator())
          .append("xmlns:epub=\"http://www.idpf.org/2007/ops\"")
          .append(System.lineSeparator())
          .append("xml:lang=\"zh-TW\" lang=\"zh-TW\"")
          .append(System.lineSeparator())
          .append("class=\"")
          .append(css.getTypesettingClass())
          .append("\"")
          .append(System.lineSeparator())
          .append(">")
          .append(System.lineSeparator())
          .append("<head>")
          .append(System.lineSeparator())
          .append("<meta charset=\"UTF-8\"/>")
          .append(System.lineSeparator())
          .append("<title>")
          .append(book.getName())
          .append("</title>")
          .append(System.lineSeparator())
          .append("<link rel=\"stylesheet\" type=\"text/css\" href=\"../style/book-style.css\"/>")
          .append(System.lineSeparator())
          .append("</head>")
          .append(System.lineSeparator())
          .append("<body class=\"p-toc\">")
          .append(System.lineSeparator())
          .append("<div class=\"main\"><h1>目錄</h1>")
          .append(System.lineSeparator());

        appendMainTableOfContents(sb);

        sb.append("</div>")
          .append(System.lineSeparator())
          .append("</body>")
          .append(System.lineSeparator())
          .append("</html>");
        FileUtil.write(output.resolve("p-toc.xhtml"), sb.toString());
    }

    private void appendMainTableOfContents(StringBuilder builder) {
        for (String title : contentXhtmlMap.keySet()) {
            builder.append("<p class=\"")
                   .append(css.getTocParagraphClass())
                   .append("\"><a href=\"")
                   .append(contentXhtmlMap.get(title))
                   .append("\">")
                   .append(title)
                   .append("</a></p>")
                   .append(System.lineSeparator());
        }
    }

}
