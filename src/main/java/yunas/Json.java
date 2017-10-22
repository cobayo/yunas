package yunas;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Wrap Third Json Lib. (Gson).
 *
 * @author yosuke kobayashi
 */
public class Json<T> {

    public String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public T fromJson(String jsonString , Class<T> clazz) {
        return new Gson().fromJson(jsonString, clazz);
    }

    public JsonObject fromJson(String jsonString){
        return new Gson().fromJson(jsonString, JsonObject.class);
    }
}
