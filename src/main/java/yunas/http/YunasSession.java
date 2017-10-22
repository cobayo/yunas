package yunas.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Signed Cookie Session in Yunas.
 *
 * Session Key is yunas.session.key in application.properties.
 * Session Expire is yunas.session.expire in application.properties.(Default is 1 day)
 * Signer's secret key is yunas.secret in application.properties.
 */
public class YunasSession {


    private Map<String,String> data = new HashMap<>();

    public YunasSession(Map<String, String> data) {
        this.data = data;
    }

    public String get(String key){
        return data.getOrDefault(key, "");

    }

    public void add(String key, String value) {
        data.put(key, value);
    }

    public void del(String key) {
        if (data.containsKey(key)) {
            data.remove(key);
        }
    }

    public Map<String, String> getData() {
        return data;
    }
}
