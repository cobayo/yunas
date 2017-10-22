package yunas.db;

/**
 * DBName in application.properties
 *
 */
public enum DBName  {

    /**
     * Use yunas.db..... in application.properties
     */
    MASTER("master"),
    /**
     * Use yunas.db.secondary..... in application.properties
     */
    SECONDARY("secondary");

    private String value;


    DBName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
