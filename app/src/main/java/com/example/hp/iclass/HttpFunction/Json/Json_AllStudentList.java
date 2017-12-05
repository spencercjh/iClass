package com.example.hp.iclass.HttpFunction.Json;

import com.example.hp.iclass.OBJ.StudentOBJ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by spencercjh on 2017/12/5.
 * iClass
 */

public class Json_AllStudentList {

    public static ArrayList<StudentOBJ> parserJson(String jsonStr) {
        ArrayList<StudentOBJ> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject list_item = jsonArray.getJSONObject(i);
                String student_id = list_item.getString("student_id");
                String student_name = list_item.getString("student_name");
                int student_sex =list_item.getInt("student_sex");
                String student_class = list_item.getString("student_class");
                String student_college=list_item.getString("student_college");
                StudentOBJ StudentOBJ = new StudentOBJ(student_id, student_name,student_sex,student_class,student_college);
                list.add(StudentOBJ);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}