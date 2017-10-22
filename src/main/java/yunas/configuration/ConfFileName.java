package yunas.configuration;

/**
 * ConfFileName.
 *
 */
public enum ConfFileName {

    DEFAULT("application.properties");

    private String fileName;

    ConfFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
