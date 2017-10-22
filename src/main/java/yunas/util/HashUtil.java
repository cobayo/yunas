package yunas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create Hash String.
 *
 */
public class HashUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HashUtil.class);

    /**
     * Create SHA256.
     *
     * @param key Secret
     */
    public static String sha256(String key) {

        return hash("SHA-256", key);

    }

    /**
     * Create SHA256.
     *
     * @param key Secret
     */
    public static String sha1(String key) {

        return hash("SHA-1", key);

    }


    /**
     * Create Hash with secret key.
     *
     * @param algorithm    "MD5","sha256"
     * @param key        String
     */
    private static String hash(String algorithm, String key) {

        try {

            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hash = digest.digest(key.getBytes());

            StringBuilder res = new StringBuilder();

            for (byte h : hash) {
                int b = h & 0xFF;
                if (b <= 0xF) {
                    res.append("0");
                }
                res.append(Integer.toHexString(b));
            }

            return res.toString();

        } catch (NoSuchAlgorithmException e) {

            LOG.warn(e.getMessage());
            return "";

        }

    }

}