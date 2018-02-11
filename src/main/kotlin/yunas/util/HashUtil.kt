package yunas.util

import org.slf4j.LoggerFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

/**
 * Create Hash String.
 *
 */
object HashUtil {

    private val LOG = LoggerFactory.getLogger(HashUtil::class.java)

    /**
     * Create SHA256.
     *
     * @param key Secret
     */
    fun sha256(key: String): String {

        return hash("SHA-256", key)

    }

    /**
     * Create SHA256.
     *
     * @param key Secret
     */
    fun sha1(key: String): String {

        return hash("SHA-1", key)

    }


    /**
     * Create Hash with secret key.
     *
     * @param algorithm    "MD5","sha256"
     * @param key        String
     */
    private fun hash(algorithm: String, key: String): String {

        try {

            val digest = MessageDigest.getInstance(algorithm)
            val hash = digest.digest(key.toByteArray())

            val res = StringBuilder()

            for (h in hash) {
                val b = h.toInt() and 0xFF
                if (b <= 0xF) {
                    res.append("0")
                }
                res.append(Integer.toHexString(b))
            }

            return res.toString()

        } catch (e: NoSuchAlgorithmException) {

            LOG.warn(e.message)
            return ""

        }

    }

}