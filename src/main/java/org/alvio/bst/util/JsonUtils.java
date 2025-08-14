package org.alvio.bst.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter prettyWriter = mapper.writerWithDefaultPrettyPrinter();

    public static String toPrettyJson(String flatJson) throws IllegalArgumentException {
        if (flatJson == null || flatJson.equals("null")) {
            return "null";
        }
        try {
            Object jsonObject = mapper.readValue(flatJson, Object.class);
            return prettyWriter.writeValueAsString(jsonObject);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON: " + flatJson, e);
        }
    }
}