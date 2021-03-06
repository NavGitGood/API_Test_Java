package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dataLoader.YAMLLoader;

import java.util.Map;

public class Helper {

    public static Map<String, Object> getMapFromInstance(Object toConvert) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(toConvert, Map.class);
    }

    public static Map<String, Object> removeKey(String key, Map<String, Object> toUpdate) {
        toUpdate.remove(key);
        return toUpdate;
    }

}
