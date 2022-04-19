package tw.com.rex.txt2epub.service;

import com.adobe.epubcheck.tool.EpubChecker;
import tw.com.rex.txt2epub.model.Book;
import tw.com.rex.txt2epub.model.TempDirectory;
import tw.com.rex.txt2epub.model.TxtContent;
import tw.com.rex.txt2epub.model.epub.Container;
import tw.com.rex.txt2epub.utils.DateUtil;
import tw.com.rex.txt2epub.utils.FileUtil;
import tw.com.rex.txt2epub.utils.XmlUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EpubService {

    private final Book book;
    private final Path outputPath;
    private final TempDirectory tempDirectory;

    public EpubService(Book book, Path outputPath) {
        this.book = book;
        this.outputPath = outputPath;
        this.tempDirectory = new TempDirectory(book.getName());
    }

    public String process() throws Exception {
        createCover();
        copyCss();
        createToc();
        createNavigationDocuments();
        createOpf();
        createContainerXml();
        createMineType();
        return covertToEpub();
    }

    private void createCover() {
        FileUtil.copy(book.getCover(), tempDirectory.getImagePath().resolve(book.getCover().getFileName()));
        Path path = tempDirectory.getXhtmlPath().resolve("p-cover.xhtml");
        FileUtil.write(path, createCoverXhtmlContent());
    }

    private String createCoverXhtmlContent() {
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
          .append("class=\"hltr\"")
          .append(System.lineSeparator())
          .append(">")
          .append(System.lineSeparator())
          .append("<head>")
          .append(System.lineSeparator())
          .append("<meta charset=\"UTF-8\"/>")
          .append(System.lineSeparator())
          .append("<meta name=\"viewport\" content=\"width=1444,height=2048\" />")
          .append(System.lineSeparator())
          .append("<style type=\"text/css\">")
          .append(System.lineSeparator())
          .append("html, body { margin: 0; padding: 0; width: 100%; height: 100%;}")
          .append(System.lineSeparator())
          .append("</style>")
          .append(System.lineSeparator())
          .append("<title>")
          .append(book.getName())
          .append("</title>")
          .append(System.lineSeparator())
          .append("</head>")
          .append(System.lineSeparator())
          .append("<body epub:type=\"cover\" class=\"p-cover\">")
          .append(System.lineSeparator())
          .append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"")
          .append(System.lineSeparator())
          .append("xmlns:xlink=\"http://www.w3.org/1999/xlink\"")
          .append(System.lineSeparator())
          .append("width=\"100%\" height=\"100%\" viewBox=\"0 0 1444 2048\">")
          .append(System.lineSeparator())
          .append("<image width=\"1444\" height=\"2048\" xlink:href=\"../image/")
          .append(book.getCover().getFileName())
          .append("\"/>")
          .append(System.lineSeparator())
          .append("</svg>")
          .append(System.lineSeparator())
          .append("</body>")
          .append(System.lineSeparator())
          .append("</html>");
        return sb.toString();
    }

    private void copyCss() {
        FileUtil.copyAll(Paths.get("src/main/resources/style"), tempDirectory.getStylePath());
    }

    /**
     * 產生目錄
     */
    private void createToc() {
        List<String> titles = book.getTxtContentList().stream().map(TxtContent::getTitle).collect(Collectors.toList());
        // todo 產生目錄
    }

    private void createNavigationDocuments() {
        String content = createNavigationDocumentsXhtmlContent();
        FileUtil.write(tempDirectory.getItemPath().resolve("navigation-documents.xhtml"), content);
    }

    private String createNavigationDocumentsXhtmlContent() {
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
          .append(">")
          .append(System.lineSeparator())
          .append("<head>")
          .append(System.lineSeparator())
          .append("<meta charset=\"UTF-8\"/>")
          .append(System.lineSeparator())
          .append("<title>目錄</title>")
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
          .append(System.lineSeparator())
          .append("<li><a href=\"xhtml/p-cover.xhtml\">封面</a></li>")
          .append(System.lineSeparator())
          .append("</ol>")
          .append(System.lineSeparator())
          .append("</nav>")
          .append(System.lineSeparator())
          .append("</body>")
          .append(System.lineSeparator())
          .append("</html>");
        return sb.toString();
    }

    private void createOpf() {
        FileUtil.write(tempDirectory.getItemPath().resolve("standard.opf"), createOpfContent());
    }

    private String createOpfContent() {
        // @formatter:off
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
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
          .append("prefix=\"ibooks: http://vocabulary.itunes.apple.com/rdf/ibooks/vocabulary-extensions-1.0/ rendition: http://www.idpf.org/vocab/rendition/#\"")
          .append(System.lineSeparator())
          .append(">")
          .append(System.lineSeparator())
          .append("<metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\">")
          .append(System.lineSeparator())
          // 書名
          .append("<dc:title id=\"title\">")
          .append(book.getName())
          .append("</dc:title>")
          .append(System.lineSeparator())
          // 作者
          .append("<dc:creator id=\"creator01\">")
          .append(book.getAuthor())
          .append("</dc:creator>")
          .append(System.lineSeparator())
          .append("<meta refines=\"#creator01\" property=\"role\" scheme=\"marc:relators\">aut</meta>")
          .append(System.lineSeparator())
          .append("<meta refines=\"#creator01\" property=\"display-seq\">1</meta>")
          .append(System.lineSeparator())
          // todo 出版社
          .append("<dc:publisher id=\"publisher\">Rex Yu</dc:publisher>")
          .append(System.lineSeparator())
          // 語言
          .append("<dc:language>zh-TW</dc:language>")
          .append(System.lineSeparator())
          // 檔案 id
          .append("<dc:identifier id=\"unique-id\">urn:uuid:")
          .append(UUID.randomUUID())
          .append("</dc:identifier>")
          .append(System.lineSeparator())
          // 更新時間
          .append("<meta property=\"dcterms:modified\">")
          .append(DateUtil.bookModifyDate())
          .append("</meta>")
          .append(System.lineSeparator())
          // iBook指定字體
          .append("<meta property=\"ibooks:specified-fonts\">true</meta>")
          .append(System.lineSeparator())
          .append("</metadata>")
          .append(System.lineSeparator())
          .append("<manifest>")
          .append(System.lineSeparator())
          // navigation
          .append("<item media-type=\"application/xhtml+xml\" id=\"toc\" href=\"navigation-documents.xhtml\" properties=\"nav\"/>")
          .append(System.lineSeparator())
          // style
          .append("<item media-type=\"text/css\" id=\"book-style\" href=\"style/book-style.css\"/>")
          .append(System.lineSeparator())
          .append("<item media-type=\"text/css\" id=\"style-reset\" href=\"style/style-reset.css\"/>")
          .append(System.lineSeparator())
          .append("<item media-type=\"text/css\" id=\"style-standard\" href=\"style/style-standard.css\"/>")
          .append(System.lineSeparator())
          .append("<item media-type=\"text/css\" id=\"style-advance\" href=\"style/style-advance.css\"/>")
          .append(System.lineSeparator())
          .append("<item media-type=\"text/css\" id=\"style-check\" href=\"style/style-check.css\"/>")
          .append(System.lineSeparator())
          // 封面
          .append("<item media-type=\"image/jpeg\" id=\"cover\" href=\"image/")
          .append(book.getCover().getFileName())
          .append("\" properties=\"cover-image\"/>")
          .append(System.lineSeparator())
          // todo xhtml
          .append("<item media-type=\"application/xhtml+xml\" id=\"p-cover\" href=\"xhtml/p-cover.xhtml\" properties=\"svg\"/>")
          .append(System.lineSeparator())
          .append("</manifest>")
          // font
          .append(System.lineSeparator())
          .append("<spine page-progression-direction=\"rtl\">")
          .append(System.lineSeparator())
          .append("<itemref linear=\"yes\" idref=\"p-cover\" properties=\"rendition:layout-pre-paginated rendition:spread-none rendition:page-spread-center\"/>")
          .append(System.lineSeparator())
          .append("</spine>")
          .append(System.lineSeparator())
          .append("</package>");
        return sb.toString();
        // @formatter:on
    }

    private void createContainerXml() {
        XmlUtil.convertToXmlFile(new Container(), tempDirectory.getMetaInfPath().resolve("container.xml"));
    }

    private void createMineType() {
        FileUtil.write(tempDirectory.getBasePath().resolve("mimetype"), "application/epub+zip");
    }

    private String covertToEpub() throws Exception {
        String[] args = {tempDirectory.getBasePath().toAbsolutePath().toString(), "--mode", "exp", "--save"};
        if (0 != new EpubChecker().run(args)) {
            throw new Exception("EPUB 轉換失敗");
        }
        return moveEpub();
    }

    private String moveEpub() {
        Path path = tempDirectory.getBasePath().getParent().resolve(book.getName() + ".epub");
        Path output = outputPath.resolve(path.getFileName());
        FileUtil.move(path, output);
        return output.toAbsolutePath().toString();
    }

}
