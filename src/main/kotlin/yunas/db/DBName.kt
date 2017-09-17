package yunas.db

/**
 * DBName.
 *
 * @author yosuke kobayashi
 */
enum class DBName constructor(val value: String) {

    MASTER("master"),
    SECONDARY("secondary");

}
