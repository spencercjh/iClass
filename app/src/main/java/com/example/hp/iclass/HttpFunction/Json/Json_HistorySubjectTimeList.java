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

public class Json_HistorySubjectTimeList {
    public static ArrayList<CheckOBJ> parserJson(String jsonStr) {
        ArrayList<CheckOBJ> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject list_item = jsonArray.getJSONObject(i);
                String check_time = list_item.getString("check_time");
                int subject_th = list_item.getInt("subject_th");
                CheckOBJ checkOBJ = new CheckOBJ(check_time, subject_th);
                list.add(checkOBJ);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
