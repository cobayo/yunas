package yunas.filter;

/**
 * StaticExtension.
 *
 * @author yosuke kobayashi
 */
public enum StaticExtension {

    CSS(".css"),
    HTML(".html"),
    HTM(".htm"),
    JSON(".json"),
    PNG(".png"),
    ICO(".ico"),
    JPG(".jpg"),
    JPEG(".jpeg"),
    GIF(".gif"),
    XML(".xml"),
    YML(".yml"),
    SCSS(".scss"),
    AVI(".avi"),
    SHTML(".shtml"),
    XHTML(".xhtml"),
    TXT(".txt"),
    MD(".md"),
    XSL(".xsl"),
    PDF(".pdf"),
    JS(".js"),
    JSX(".jsx"),
    TS(".ts"),
    COFFEE(".coffee"),
    BMP(".bmp"),
    MOV(".mov"),
    WMV(".wmv"),
    ZIP(".zip"),
    DOC(".doc"),
    CSV(".csv"),
    MP3(".mp3"),
    MP4(".mp4"),
    TSV(".tsv");

    private String value;

    StaticExtension(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
