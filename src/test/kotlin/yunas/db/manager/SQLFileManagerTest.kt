package yunas.db.manager

import org.junit.Assert
import org.junit.Test

/**
 * SQLFileManagerTest.
 *
 */
class SQLFileManagerTest {


    @Test fun readSqlTest() {

        val sqlFile =  "sql/select_test.sql";
        val sql = SQLFileManager.readSql(sqlFile)

        Assert.assertTrue(sql!!.contains("SELECT"))


        val sqlEmptyFile =  "sql/select_test2.sql";
        val sqlEmpty = SQLFileManager.readSql(sqlEmptyFile)

        Assert.assertTrue(sqlEmpty == null)

    }
}