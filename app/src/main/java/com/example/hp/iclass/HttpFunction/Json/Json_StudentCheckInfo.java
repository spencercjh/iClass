package com.example.hp.iclass.HttpFunction.Json;

import com.example.hp.iclass.OBJ.CheckOBJ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Json_StudentCheckInfo {
    public static CheckOBJ parserJson(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String check_time = jsonObject.getString("check_time");
        int ischeck = jsonObject.getInt("ischeck");
        int seat_index = jsonObject.getInt("seat_index");
        return new CheckOBJ(check_time, ischeck, seat_index);
    }
}
