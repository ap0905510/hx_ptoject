package com.yw.mvp.utils;

import org.json.JSONObject;

import java.util.Map;

/**
 * 作者：create by YW
 * 日期：2018.02.07 17:41
 * 描述：
 */

public class MapToJson {

    public static String toJson(Map<String, String> map) {
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

}
