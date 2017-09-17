package org.yunas.util

import org.junit.Test
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertTrue

/**
 * ResultSetRowTest.
 *
 */
class ResultSetRowTest {


    @Test fun getSomethingTest() {

        val cal = Calendar.getInstance()
        cal.set(1979,3,21,0,0,0)

        val map = mapOf<String,Any>("fileName" to "foo","count" to 123232323,"day" to cal.time,"double" to 1.1)

        val resultSetRow = ResultSetRow(map)

        assertTrue {  -> "" == resultSetRow.getString("none") }

        assertTrue {  -> "foo" == resultSetRow.getString("fileName") }

        assertTrue {  -> "1.1" == resultSetRow.getString("double") }

        print(resultSetRow.getString("day"))

        assertTrue {  -> "1979/04/21 00:00:00" == resultSetRow.getString("day") }

        assertTrue {  -> 0 == resultSetRow.getInt("none") }

        assertTrue {  -> 0 == resultSetRow.getInt("fileName") }

        assertTrue {  -> 123232323 == resultSetRow.getInt("count") }

        assertTrue {  -> 0L == resultSetRow.getLong("fileName") }

        assertTrue {  ->  1.1 == resultSetRow.getDouble("double") }

        assertTrue {  -> 123232323L == resultSetRow.getLong("count") }

        assertTrue {  -> "foo" == resultSetRow.get("fileName") }

        assertTrue {  -> BigDecimal(123232323) == resultSetRow.getBigDecimal("count") }

        assertTrue {  -> BigDecimal(1.1) == resultSetRow.getBigDecimal("double") }

    }
}