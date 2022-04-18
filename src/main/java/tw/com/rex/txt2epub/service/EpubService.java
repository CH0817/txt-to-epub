package tw.com.rex.txt2epub.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import tw.com.rex.txt2epub.model.Container;
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
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<package\n" +
                " xmlns=\"http://www.idpf.org/2007/opf\"\n" +
                " version=\"3.0\"\n" +
                " xml:lang=\"zh-TW\"\n" +
                " unique-identifier=\"unique-id\"\n" +
                " prefix=\"ibooks: http://vocabulary.itunes.apple.com/rdf/ibooks/vocabulary-extensions-1.0/ rendition: http://www.idpf.org/vocab/rendition/#\"\n" +
                ">\n" +
                "\n" +
                "<metadata xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n" +
                "\n" +
                "<!-- 作品名 -->\n" +
                "<dc:title id=\"title\">曹賊</dc:title>\n" +
                "\n" +
                "<!-- 作者名 -->\n" +
                "<dc:creator id=\"creator01\">庚新</dc:creator>\n" +
                "<meta refines=\"#creator01\" property=\"role\" scheme=\"marc:relators\">aut</meta>\n" +
                "<meta refines=\"#creator01\" property=\"display-seq\">1</meta>\n" +
                "\n" +
                "<!-- 出版社名 -->\n" +
                "<dc:publisher id=\"publisher\">台灣數位出版聯盟</dc:publisher>\n" +
                "\n" +
                "<!-- 語言 -->\n" +
                "<dc:language>zh-TW</dc:language>\n" +
                "\n" +
                "<!-- 檔案id -->\n" +
                "<dc:identifier id=\"unique-id\">urn:uuid:" + UUID.randomUUID() + "</dc:identifier>\n" +
                "\n" +
                "<!-- 更新時間 -->\n" +
                "<meta property=\"dcterms:modified\">2019-11-01T00:00:00Z</meta>\n" +
                "\n" +
                "<!-- iBook指定字體 -->\n" +
                "<meta property=\"ibooks:specified-fonts\">true</meta>\n" +
                "\n" +
                "</metadata>\n" +
                "\n" +
                "<manifest>\n" +
                "\n" +
                "<!-- navigation -->\n" +
                "<item media-type=\"application/xhtml+xml\" id=\"toc\" href=\"navigation-documents.xhtml\" properties=\"nav\"/>\n" +
                "\n" +
                "<!-- style -->\n" +
                "<item media-type=\"text/css\" id=\"book-style\"     href=\"style/book-style.css\"/>\n" +
                "<item media-type=\"text/css\" id=\"style-reset\"    href=\"style/style-reset.css\"/>\n" +
                "<item media-type=\"text/css\" id=\"style-standard\" href=\"style/style-standard.css\"/>\n" +
                "<item media-type=\"text/css\" id=\"style-advance\"  href=\"style/style-advance.css\"/>\n" +
                "<item media-type=\"text/css\" id=\"style-check\"    href=\"style/style-check.css\"/>\n" +
                "\n" +
                "<!-- image -->\n" +
                "<item media-type=\"image/jpeg\" id=\"cover\"      href=\"image/cover.jpg\" properties=\"cover-image\"/>\n" +
                // "<item media-type=\"image/png\" id=\"tdpf\"      href=\"image/tdpf.png\" />\n" +
                // "<item media-type=\"image/png\" id=\"moc\"      href=\"image/moc.png\" />\n" +
                "\n" +
                "\n" +
                "<!-- xhtml -->\n" +
                "<item media-type=\"application/xhtml+xml\" id=\"p-cover\"       href=\"xhtml/p-cover.xhtml\" properties=\"svg\"/>\n" +
                // "<item media-type=\"application/xhtml+xml\" id=\"p-fmatter-001\" href=\"xhtml/p-fmatter-001.xhtml\"/>\n" +
                // "<item media-type=\"application/xhtml+xml\" id=\"p-titlepage\"   href=\"xhtml/p-titlepage.xhtml\"/>\n" +
                // "<item media-type=\"application/xhtml+xml\" id=\"p-toc\"         href=\"xhtml/p-toc.xhtml\"/>\n" +
                // "<item media-type=\"application/xhtml+xml\" id=\"p-001\"         href=\"xhtml/p-001.xhtml\"/>\n" +
                // "<item media-type=\"application/xhtml+xml\" id=\"p-002\"         href=\"xhtml/p-002.xhtml\"/>\n" +
                // "<item media-type=\"application/xhtml+xml\" id=\"p-003\"         href=\"xhtml/p-003.xhtml\"/>\n" +
                "\n" +
                "<!-- font -->\n" +
                "\n" +
                "</manifest>\n" +
                "\n" +
                "<spine page-progression-direction=\"rtl\">\n" +
                "<itemref linear=\"yes\" idref=\"p-cover\"       properties=\"rendition:layout-pre-paginated \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t    rendition:spread-none \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t    rendition:page-spread-center\"/>\n" +
                "<itemref linear=\"yes\" idref=\"p-fmatter-001\" properties=\"page-spread-left\"/>\n" +
                "<itemref linear=\"yes\" idref=\"p-titlepage\"   properties=\"page-spread-left\"/>\n" +
                "<itemref linear=\"yes\" idref=\"p-toc\"         properties=\"page-spread-left\"/>\n" +
                "<itemref linear=\"yes\" idref=\"p-001\"         properties=\"page-spread-left\"/>\n" +
                "<itemref linear=\"yes\" idref=\"p-002\"         properties=\"page-spread-left\"/>\n" +
                "<itemref linear=\"yes\" idref=\"p-003\"         properties=\"page-spread-left\"/>\n" +
                "<itemref linear=\"yes\" idref=\"p-colophon\"    properties=\"page-spread-left\"/>\n" +
                "\n" +
                "</spine>\n" +
                "\n" +
                "</package>";
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
                "<title>羅生門</title>\n" +
                "</head>\n" +
                "<body epub:type=\"cover\" class=\"p-cover\">\n" +
                "        <svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"\n" +
                "            xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
                "            width=\"100%\" height=\"100%\" viewBox=\"0 0 1444 2048\">\n" +
                "            <image width=\"1444\" height=\"2048\" xlink:href=\"../image/cover.jpg\"/>\n" +
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
