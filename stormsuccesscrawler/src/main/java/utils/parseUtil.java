package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by david on 17-7-12.
 */
public class parseUtil {
    public static JsonObject parse(String s){
        JsonParser parser =new JsonParser();
        JsonObject object = (JsonObject) parser.parse(s);
        return object;
    }
}
