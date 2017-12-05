package com.example.hp.iclass.HttpFunction.Json;

import com.example.hp.iclass.OBJ.CheckOBJ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Json_CheckedStudentList {
    public static ArrayList<CheckOBJ> parserJson(String jsonStr) {
        ArrayList<CheckOBJ> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject list_item = jsonArray.getJSONObject(i);
                String student_id = list_item.getString("student_id");
                String check_time = list_item.getString("check_time");
                int ischeck = list_item.getInt("ischeck");
                CheckOBJ checkOBJ = new CheckOBJ(student_id, Adjust_Time(check_time), ischeck);
                list.add(checkOBJ);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<CheckOBJ> parserJson2(String jsonStr) {
        ArrayList<CheckOBJ> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject list_item = jsonArray.getJSONObject(i);
                String student_id = list_item.getString("student_id");
                String check_time = list_item.getString("check_time");
                int ischeck = list_item.getInt("ischeck");
                int seat_index = list_item.getInt("seat_index");
                CheckOBJ checkOBJ = new CheckOBJ(student_id, Adjust_Time(check_time), ischeck, seat_index);
                list.add(checkOBJ);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String Adjust_Time(String time) {
        return time.substring(11);
    }
}
