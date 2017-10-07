package yunas.db

/**
 * DBName in application.properties
 *
 */
enum class DBName constructor(val value: String) {

    /**
     * Use yunas.db..... in application.properties
     */
    MASTER("master"),
    /**
     * Use yunas.db.secondary..... in application.properties
     */
    SECONDARY("secondary");

}
