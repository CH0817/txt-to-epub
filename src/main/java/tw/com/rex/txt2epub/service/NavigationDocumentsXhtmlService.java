package tw.com.rex.txt2epub.service;

import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.utils.FileUtil;

import java.nio.file.Path;

public class NavigationDocumentsXhtmlService {

    private final Book book;
    private final Path output;

    public NavigationDocumentsXhtmlService(ConvertInfo convertInfo) {
        this.book = convertInfo.getBook();
        this.output = convertInfo.getTempDirectory().getItemPath();
    }

    public void generate() {
        StringBuilder sb = new StringBuilder();
        appendPrefixContents(sb);
        appendMainContents(sb);
        appendSuffixContents(sb);
        FileUtil.write(output.resolve("navigation-documents.xhtml"), sb.toString());
    }

    private void appendPrefixContents(StringBuilder builder) {
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
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
               .append("</head>")
               .append(System.lineSeparator())
               .append("<body>")
               .append(System.lineSeparator())
               .append("<nav epub:type=\"toc\" id=\"toc\">")
               .append(System.lineSeparator())
               .append("<h1>目錄</h1>")
               .append(System.lineSeparator())
               .append("<ol>")
               .append(System.lineSeparator());
        // 封面
        if (book.hasCover()) {
            builder.append("<li><a href=\"xhtml/p-cover.xhtml\">封面</a></li>");
        }
        // 目錄
        builder.append("<li><a href=\"xhtml/p-toc.xhtml\">目錄</a></li>")
               .append(System.lineSeparator());
    }


    private void appendMainContents(StringBuilder builder) {
        book.getTxtContentList().forEach(txtContent -> builder.append("<li><a href=\"xhtml/")
                                                              .append(txtContent.getXhtmlName())
                                                              .append("\">")
                                                              .append(txtContent.getTitle())
                                                              .append("</a></li>")
                                                              .append(System.lineSeparator()));
    }

    private void appendSuffixContents(StringBuilder builder) {
        builder.append("</ol>")
               .append(System.lineSeparator())
               .append("</nav>")
               .append(System.lineSeparator())
               .append("</body>")
               .append(System.lineSeparator())
               .append("</html>");
    }

}
