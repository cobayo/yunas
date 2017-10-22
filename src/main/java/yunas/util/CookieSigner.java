package yunas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.Yunas;
import yunas.exception.YunasException;
import yunas.exception.YunasExceptionProvider;

/**
 * Signed messages for baking signed cookies.
 *
 */
public final class CookieSigner {

    private static final Logger LOG = LoggerFactory.getLogger(CookieSigner.class);

    public static String sign(String message) {

        try {

            String secret = Yunas.getConfiguration().getSecret();
            if (BaseUtil.blank(secret)) {
                throw new YunasExceptionProvider().unSetSecret();
            }

            return HashUtil.sha256(message + secret).substring(0,42);

        } catch (YunasException e) {
            LOG.warn(e.getMessage());
            throw e;
        }
    }

}