package tw.com.rex.txt2epub.service;

import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.model.CssClass;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.utils.FileUtil;

import java.nio.file.Path;
import java.util.List;

public class ContentXhtmlService {

    private final Book book;
    private final CssClass css;
    private final Path output;

    public ContentXhtmlService(ConvertInfo convertInfo, CssClass css) {
        this.css = css;
        this.book = convertInfo.getBook();
        this.output = convertInfo.getTempDirectory().getXhtmlPath();
    }

    public void generate() {
        book.getTxtContentList().forEach(content -> {
            StringBuilder sb = new StringBuilder();
            appendPrefixContents(sb, content);
            appendMainContents(sb, content.getContentList());
            appendSuffix(sb);
            FileUtil.write(output.resolve(content.getXhtmlName()), sb.toString());
        });
    }

    private void appendPrefixContents(StringBuilder builder, TxtContent txtContent) {
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
               .append("<body class=\"p-text\">")
               .append(System.lineSeparator())
               .append("<div class=\"main\">")
               .append(System.lineSeparator())
               .append("<h2 class=\"")
               .append(css.getContentH2Class())
               .append("\">")
               .append(txtContent.getTitle())
               .append("</h2>")
               .append(System.lineSeparator());
    }

    private void appendMainContents(StringBuilder builder, List<String> contentList) {
        for (int i = 0; i < contentList.size(); i++) {
            if (i == 0) {
                builder.append("<p class=\"")
                       .append(css.getContentFirstParagraphClass())
                       .append("\">");
            } else {
                builder.append("<p")
                       .append(css.getContentParagraphClass())
                       .append(">");
            }
            builder.append(contentList.get(i))
                   .append("</p>")
                   .append(System.lineSeparator());
        }
    }

    private void appendSuffix(StringBuilder builder) {
        builder.append("</div>")
               .append(System.lineSeparator())
               .append("</body>")
               .append(System.lineSeparator())
               .append("</html>");
    }

}
