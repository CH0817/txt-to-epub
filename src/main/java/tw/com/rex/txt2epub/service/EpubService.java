package tw.com.rex.txt2epub.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.Container;
import tw.com.rex.txt2epub.utils.DateUtil;
import tw.com.rex.txt2epub.utils.XmlUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
public class EpubService {

    private String selectedCover;
    private final Path directoryPath = Paths.get(System.getProperty("java.io.tmpdir"), "曹賊");

    public void process() {
        createCover();
        copyCss();
        createToc();
        createNavigationDocumentsXhtml();
        createOpf();
        Path filePath = directoryPath.resolve("曹賊.epub");
        createBasicFiles();
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SneakyThrows
    private void createOpf() {
        Path item = directoryPath.resolve("item");
        if (!Files.exists(item)) {
            Files.createDirectories(item);
        }
        Path opf = item.resolve("standard.opf");
        String content = createOpfContent();
        Files.write(opf, content.getBytes(StandardCharsets.UTF_8));
    }

    private String createOpfContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append(System.lineSeparator());
        sb.append("<package");
        sb.append(System.lineSeparator());
        sb.append("xmlns=\"http://www.idpf.org/2007/opf\"");
        sb.append(System.lineSeparator());
        sb.append("version=\"3.0\"");
        sb.append(System.lineSeparator());
        sb.append("xml:lang=\"zh-TW\"");
        sb.append(System.lineSeparator());
        sb.append("unique-identifier=\"unique-id\"");
        sb.append(System.lineSeparator());
        sb.append("prefix=\"ibooks: http://vocabulary.itunes.apple.com/rdf/ibooks/vocabulary-extensions-1.0/ rendition: http://www.idpf.org/vocab/rendition/#\"");
        sb.append(System.lineSeparator());
        sb.append(">");
        sb.append(System.lineSeparator());
        sb.append("<metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\">");
        sb.append(System.lineSeparator());
        // todo 書名
        sb.append("<dc:title id=\"title\">曹賊</dc:title>");
        sb.append(System.lineSeparator());
        // todo 作者名
        sb.append("<dc:creator id=\"creator01\">庚新</dc:creator>");
        sb.append(System.lineSeparator());
        sb.append("<meta refines=\"#creator01\" property=\"role\" scheme=\"marc:relators\">aut</meta>");
        sb.append(System.lineSeparator());
        sb.append("<meta refines=\"#creator01\" property=\"display-seq\">1</meta>");
        sb.append(System.lineSeparator());
        // todo 出版社
        sb.append("<dc:publisher id=\"publisher\">Rex Yu</dc:publisher>");
        sb.append(System.lineSeparator());
        // 語言
        sb.append("<dc:language>zh-TW</dc:language>");
        sb.append(System.lineSeparator());
        // 檔案 id
        sb.append("<dc:identifier id=\"unique-id\">urn:uuid:");
        sb.append(UUID.randomUUID());
        sb.append("</dc:identifier>");
        sb.append(System.lineSeparator());
        // 更新時間
        sb.append("<meta property=\"dcterms:modified\">");
        sb.append(DateUtil.bookModifyDate());
        sb.append("</meta>");
        sb.append(System.lineSeparator());
        // iBook指定字體
        sb.append("<meta property=\"ibooks:specified-fonts\">true</meta>");
        sb.append(System.lineSeparator());
        sb.append("</metadata>");
        sb.append(System.lineSeparator());
        sb.append("<manifest>");
        sb.append(System.lineSeparator());
        // navigation
        sb.append("<item media-type=\"application/xhtml+xml\" id=\"toc\" href=\"navigation-documents.xhtml\" properties=\"nav\"/>");
        sb.append(System.lineSeparator());
        // style
        sb.append("<item media-type=\"text/css\" id=\"book-style\" href=\"style/book-style.css\"/>");
        sb.append(System.lineSeparator());
        sb.append("<item media-type=\"text/css\" id=\"style-reset\" href=\"style/style-reset.css\"/>");
        sb.append(System.lineSeparator());
        sb.append("<item media-type=\"text/css\" id=\"style-standard\" href=\"style/style-standard.css\"/>");
        sb.append(System.lineSeparator());
        sb.append("<item media-type=\"text/css\" id=\"style-advance\" href=\"style/style-advance.css\"/>");
        sb.append(System.lineSeparator());
        sb.append("<item media-type=\"text/css\" id=\"style-check\" href=\"style/style-check.css\"/>");
        sb.append(System.lineSeparator());
        // todo cover
        sb.append("<item media-type=\"image/jpeg\" id=\"cover\" href=\"image/cover.jpeg\" properties=\"cover-image\"/>");
        sb.append(System.lineSeparator());
        // todo xhtml
        sb.append("<item media-type=\"application/xhtml+xml\" id=\"p-cover\" href=\"xhtml/p-cover.xhtml\" properties=\"svg\"/>");
        sb.append(System.lineSeparator());
        sb.append("</manifest>");
        // font
        sb.append(System.lineSeparator());
        sb.append("<spine page-progression-direction=\"rtl\">");
        sb.append(System.lineSeparator());
        sb.append("<itemref linear=\"yes\" idref=\"p-cover\" properties=\"rendition:layout-pre-paginated rendition:spread-none rendition:page-spread-center\"/>");
        sb.append(System.lineSeparator());
        // sb.append("<itemref linear=\\\"yes\\\" idref=\\\"p-toc\\\"         properties=\\\"page-spread-left\\\"/>");
        sb.append(System.lineSeparator());
        sb.append("</spine>");
        sb.append(System.lineSeparator());
        sb.append("</package>");
        return sb.toString();
    }

    @SneakyThrows
    private void createNavigationDocumentsXhtml() {
        Path item = directoryPath.resolve("item");
        if (!Files.exists(item)) {
            Files.createDirectories(item);
        }
        Path xhtml = item.resolve("navigation-documents.xhtml");
        String content = createNavigationDocumentsXhtmlContent();
        Files.write(xhtml, content.getBytes(StandardCharsets.UTF_8));
    }

    private String createNavigationDocumentsXhtmlContent() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE html>\n" +
                "<html\n" +
                " xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                " xmlns:epub=\"http://www.idpf.org/2007/ops\"\n" +
                " xml:lang=\"zh-TW\" lang=\"zh-TW\"\n" +
                ">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\"/>\n" +
                "<title>目錄</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<nav epub:type=\"toc\" id=\"toc\">\n" +
                "<h1>目錄</h1>\n" +
                "<ol>\n" +
                "<li><a href=\"xhtml/p-cover.xhtml\">封面</a></li>\n" +
                // "<li><a href=\"xhtml/p-fmatter-001.xhtml\">製作緣起</a></li>\n" +
                // "<li><a href=\"xhtml/p-titlepage.xhtml\">書名頁</a></li>\n" +
                // "<li><a href=\"xhtml/p-toc.xhtml\">目錄</a></li>\n" +
                // "<li><a href=\"xhtml/p-001.xhtml\">內文</a></li>\n" +
                // "<li><a href=\"xhtml/p-002.xhtml\">製作解說</a></li> \n" +
                // "<li><a href=\"xhtml/p-colophon.xhtml\">版權頁</a></li>\n" +
                "</ol>\n" +
                "</nav>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

    /**
     * 建立目錄頁
     */
    private void createToc() {

    }

    @SneakyThrows
    private void copyCss() {
        Path style = directoryPath.resolve("item").resolve("style");
        if (!Files.exists(style)) {
            Files.createDirectories(style);
        }
        try (Stream<Path> walk = Files.walk(Paths.get("src/main/resources"))) {
            walk.filter(Files::isRegularFile)
                .forEach(p -> {
                    Path targetPath = style.resolve(p.getFileName());
                    try {
                        Files.copy(p, targetPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }

    @SneakyThrows
    private void createCover() {
        copyCover();
        Path xhtml = directoryPath.resolve("item").resolve("xhtml");
        if (!Files.exists(xhtml)) {
            Files.createDirectories(xhtml);
        }
        Path coverXhtml = xhtml.resolve("p-cover.xhtml");
        String coverXhtmlContent = createCoverXhtmlContent();
        Files.write(coverXhtml, coverXhtmlContent.getBytes(StandardCharsets.UTF_8));
    }

    private String createCoverXhtmlContent() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE html>\n" +
                "<html\n" +
                " xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                " xmlns:epub=\"http://www.idpf.org/2007/ops\"\n" +
                " xml:lang=\"zh-TW\" lang=\"zh-TW\"\n" +
                " class=\"hltr\"\n" +
                ">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\"/>\n" +
                "<meta name=\"viewport\" content=\"width=1444,height=2048\" />\n" +
                "<style type=\"text/css\">\n" +
                "  html, body { margin: 0; padding: 0; width: 100%; height: 100%;}\n" +
                "</style>\n" +
                "<title>曹賊</title>\n" +
                "</head>\n" +
                "<body epub:type=\"cover\" class=\"p-cover\">\n" +
                "        <svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"\n" +
                "            xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
                "            width=\"100%\" height=\"100%\" viewBox=\"0 0 1444 2048\">\n" +
                "            <image width=\"1444\" height=\"2048\" xlink:href=\"../image/cover.jpeg\"/>\n" +
                "        </svg>\n" +
                "</body>\n" +
                "</html>";
    }

    @SneakyThrows
    private void copyCover() {
        if (StringUtils.isNotBlank(selectedCover)) {
            Path imageDirectoryPath = directoryPath.resolve("item").resolve("image");
            if (!Files.exists(imageDirectoryPath)) {
                Files.createDirectories(imageDirectoryPath);
            }
            Path path = Paths.get(selectedCover);
            Files.copy(path, imageDirectoryPath.resolve(path.getFileName()));
        }
    }

    private void createBasicFiles() {
        createContainerXml();
        createMineType();
    }

    private void createContainerXml() {
        Path containerXmlPath = directoryPath.resolve("META-INF").resolve("container.xml");
        if (!Files.exists(containerXmlPath.getParent())) {
            try {
                Files.createDirectories(containerXmlPath.getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Files.exists(containerXmlPath)) {
            try {
                Files.createFile(containerXmlPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        XmlUtil.convertToXmlFile(new Container(), containerXmlPath);
    }

    private void createMineType() {
        Path path = directoryPath.resolve("mimetype");
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                Files.write(path, "application/epub+zip".getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
