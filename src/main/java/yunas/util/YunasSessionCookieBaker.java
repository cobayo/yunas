package yunas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yunas.exception.YunasExceptionProvider;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create Signed Cookie.
 */
public class YunasSessionCookieBaker {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(YunasSessionCookieBaker.class);

    public static String  encode(Map<String,String> data) {

        if (data.isEmpty()) {
            return "";
        }

        try {

            String encode = data.entrySet().stream().map(it -> encodeUrl(it.getKey()) + "=" + encodeUrl(it.getValue())
                    ).collect(Collectors.joining("&"));

            return CookieSigner.sign(encode) + "-" + encode;


        } catch (Exception e) {
            LOG.info(e.getMessage());
            return "";
        }

    }

    private static String encodeUrl(String value) {

        try {

            return URLEncoder.encode(value,"UTF-8");

        } catch (Exception e) {
            LOG.error(e.getMessage());
            return "";
        }
    }

    public Map<String,String> decode(String value ) {

        if (value == null) {
            return Collections.emptyMap();
        }

        String[] split = value.split("-");

        if (split.length <= 1) {
            return Collections.emptyMap();
        }

        try {

            String message = split[0];
            String data = split[1];
            if (!BaseUtil.equals(message,CookieSigner.sign(data))) {
                throw new YunasExceptionProvider().notMatchSign(value);
            }

            Map<String,String> res = new HashMap<>();
            Arrays.stream(data.split("&")).forEach(

                it ->  {

                    String[] v =  it.split("=");
                    if (v.length >= 1) {
                        res.put(v[0],v[1]);
                    }
                }

            );

            return res;


        } catch (Exception e) {
            LOG.info(e.getMessage());
            return Collections.emptyMap();
        }
    }


}
