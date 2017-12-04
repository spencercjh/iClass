package com.example.hp.iclass.HttpFunction.Json;

import android.annotation.SuppressLint;

import com.example.hp.iclass.OBJ.SubjectOBJ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Json_TeacherSubjectList {
    @SuppressLint("DefaultLocale")
    public static ArrayList<SubjectOBJ> parserJson(String jsonStr) {
        ArrayList<SubjectOBJ> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject list_item = jsonArray.getJSONObject(i);
                String subject_id = list_item.getString("subject_id");
                String subject_name = list_item.getString("subject_name");
                int student_num = list_item.getInt("student_num");
                String classroom = list_item.getString("classroom");
                int check_situation = list_item.getInt("check_situation");
                SubjectOBJ subjectOBJ = new SubjectOBJ(subject_id, subject_name, student_num, classroom, check_situation);
                list.add(subjectOBJ);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
