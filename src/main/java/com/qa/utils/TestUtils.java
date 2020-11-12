package com.qa.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestUtils {

    public static String getValueByJPath(JSONObject responseJSON, String jpath) {

        Object obj = responseJSON;
        for (String s : jpath.split("/"))
            if (!s.isEmpty())
                if (!(s.contains("[") || s.contains("]")))
                    obj = ((JSONObject) obj).get(s);
                else if (s.contains("[") || s.contains("]")) {
                    obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0]))
                            .get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
                }
        return obj.toString();
    }
}
