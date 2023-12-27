package tw.com.rex.txt2epub.service;

import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.ConvertInfo;
import tw.com.rex.txt2epub.utils.DateUtil;
import tw.com.rex.txt2epub.utils.FileUtil;

import java.nio.file.Path;
import java.util.UUID;

public class OpfService {

    private final Book book;
    private final Path output;

    public OpfService(ConvertInfo convertInfo, int index) {
        this.book = convertInfo.getBooks()[index];
        this.output = convertInfo.getTempDirectories()[index].getItemPath();
    }

    public void generate() {
        StringBuilder sb = new StringBuilder();
        appendPrefix(sb);
        // 書名
        appendBookName(sb);
        // 作者
        appendCreator(sb);
        // 出版社
        appendPublisher(sb);
        // 語言
        appendLanguage(sb);
        // 檔案 id
        appendUniqueId(sb);
        // 更新時間
        appendModifiedTime(sb);
        // iBook指定字體
        appendIBookMeta(sb);

        sb.append("</metadata>")
                .append(System.lineSeparator())
                .append("<manifest>")
                .append(System.lineSeparator());

        // navigation item
        appendNavigationItem(sb);
        // style
        appendStyleItems(sb);
        if (book.hasCover()) {
            // cover image item
            appendCoverImageItem(sb);
            // cover item
            appendCoverItem(sb);
        }
        // table of contents item
        appendTableOfContentsItem(sb);
        // main content items xhtml
        appendItems(sb);

        sb.append(System.lineSeparator())
                .append("</manifest>")
                .append(System.lineSeparator())
                // todo page-progression-direction???
                .append("<spine page-progression-direction=\"rtl\">")
                .append(System.lineSeparator());

        // cover xhtml ref
        if (book.hasCover()) {
            appendCoverItemRefs(sb);
        }
        // table of contents xhtml ref
        appendTableOfContentsItemRefs(sb);
        // xhtml ref
        appendItemRefs(sb);

        sb.append(System.lineSeparator())
                .append("</spine>")
                .append(System.lineSeparator())
                .append("</package>");

        FileUtil.write(output.resolve("standard.opf"), sb.toString());
    }

    private void appendPrefix(StringBuilder builder) {
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append(System.lineSeparator())
                .append("<package")
                .append(System.lineSeparator())
                .append("xmlns=\"http://www.idpf.org/2007/opf\"")
                .append(System.lineSeparator())
                .append("version=\"3.0\"")
                .append(System.lineSeparator())
                .append("xml:lang=\"zh-TW\"")
                .append(System.lineSeparator())
                .append("unique-identifier=\"unique-id\"")
                .append(System.lineSeparator())
                .append("prefix=\"ibooks: http://vocabulary.itunes.apple.com/rdf/ibooks/vocabulary-extensions-1.0/")
                .append(" rendition: http://www.idpf.org/vocab/rendition/#\"")
                .append(System.lineSeparator())
                .append(">")
                .append(System.lineSeparator())
                .append("<metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\">")
                .append(System.lineSeparator());
    }

    private void appendBookName(StringBuilder builder) {
        builder.append("<dc:title id=\"title\">")
                .append(book.getName())
                .append("</dc:title>")
                .append(System.lineSeparator());
    }

    private void appendCreator(StringBuilder builder) {
        if (StringUtils.isNotBlank(this.book.getAuthor())) {
            builder.append("<dc:creator id=\"creator01\">")
                    .append(this.book.getAuthor())
                    .append("</dc:creator>")
                    .append(System.lineSeparator())
                    .append("<meta refines=\"#creator01\" property=\"role\" scheme=\"marc:relators\">aut</meta>")
                    .append(System.lineSeparator())
                    .append("<meta refines=\"#creator01\" property=\"display-seq\">1</meta>")
                    .append(System.lineSeparator());
        }
    }

    private void appendPublisher(StringBuilder builder) {
        if (StringUtils.isNotBlank(this.book.getPublisher())) {
            builder.append("<dc:publisher id=\"publisher\">")
                    .append(this.book.getPublisher())
                    .append("</dc:publisher>")
                    .append(System.lineSeparator());
        }
    }

    private void appendLanguage(StringBuilder builder) {
        builder.append("<dc:language>zh-TW</dc:language>")
                .append(System.lineSeparator());
    }

    private void appendUniqueId(StringBuilder builder) {
        builder.append("<dc:identifier id=\"unique-id\">urn:uuid:")
                .append(UUID.randomUUID())
                .append("</dc:identifier>")
                .append(System.lineSeparator());
    }

    private void appendModifiedTime(StringBuilder builder) {
        builder.append("<meta property=\"dcterms:modified\">")
                .append(DateUtil.bookModifyDate())
                .append("</meta>")
                .append(System.lineSeparator());
    }

    private void appendIBookMeta(StringBuilder builder) {
        builder.append("<meta property=\"ibooks:specified-fonts\">true</meta>")
                .append(System.lineSeparator());
    }

    private void appendNavigationItem(StringBuilder builder) {
        builder.append("<item media-type=\"application/xhtml+xml\" id=\"toc\"")
                .append(" href=\"navigation-documents.xhtml\" properties=\"nav\"/>")
                .append(System.lineSeparator());
    }

    private void appendStyleItems(StringBuilder builder) {
        builder.append("<item media-type=\"text/css\" id=\"book-style\" href=\"style/book-style.css\"/>")
                .append(System.lineSeparator())
                .append("<item media-type=\"text/css\" id=\"style-reset\" href=\"style/style-reset.css\"/>")
                .append(System.lineSeparator())
                .append("<item media-type=\"text/css\" id=\"style-standard\" href=\"style/style-standard.css\"/>")
                .append(System.lineSeparator())
                .append("<item media-type=\"text/css\" id=\"style-advance\" href=\"style/style-advance.css\"/>")
                .append(System.lineSeparator())
                .append("<item media-type=\"text/css\" id=\"style-check\" href=\"style/style-check.css\"/>")
                .append(System.lineSeparator());
    }

    private void appendCoverImageItem(StringBuilder builder) {
        builder.append("<item media-type=\"image/jpeg\" id=\"cover\" href=\"image/")
                .append(book.getCover().getFileName())
                .append("\" properties=\"cover-image\"/>")
                .append(System.lineSeparator());
    }

    private void appendCoverItem(StringBuilder builder) {
        builder.append("<item media-type=\"application/xhtml+xml\" id=\"p-cover\"")
                .append(" href=\"xhtml/p-cover.xhtml\" properties=\"svg\"/>")
                .append(System.lineSeparator());
    }

    private void appendTableOfContentsItem(StringBuilder builder) {
        builder.append("<item media-type=\"application/xhtml+xml\" id=\"p-toc\" href=\"xhtml/p-toc.xhtml\"/>")
                .append(System.lineSeparator());
    }

    private void appendItems(StringBuilder builder) {
        book.getTxtContentList().forEach(txtContent -> {
            String xhtml = txtContent.getXhtmlName();
            builder.append("<item media-type=\"application/xhtml+xml\" id=\"")
                    .append(xhtml, 0, xhtml.lastIndexOf("."))
                    .append("\" href=\"xhtml/")
                    .append(xhtml)
                    .append("\"/>")
                    .append(System.lineSeparator());
        });
    }

    private void appendCoverItemRefs(StringBuilder builder) {
        builder.append("<itemref linear=\"yes\" idref=\"p-cover\" properties=\"rendition:layout-pre-paginated")
                .append(" rendition:spread-none rendition:page-spread-center\"/>")
                .append(System.lineSeparator());
    }

    private void appendTableOfContentsItemRefs(StringBuilder builder) {
        builder.append("<itemref linear=\"yes\" idref=\"p-toc\" properties=\"page-spread-left\"/>")
                .append(System.lineSeparator());
    }

    private void appendItemRefs(StringBuilder sb) {
        book.getTxtContentList().forEach(txtContent -> {
            String xhtml = txtContent.getXhtmlName();
            sb.append("<itemref linear=\"yes\" idref=\"")
                    .append(xhtml, 0, xhtml.lastIndexOf("."))
                    .append("\" properties=\"page-spread-left\"/>")
                    .append(System.lineSeparator());
        });
    }

}
