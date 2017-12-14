package com.example.hp.iclass.HttpFunction.Json;

import com.example.hp.iclass.OBJ.TeacherOBJ;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Json_TeacherProperty {
    public static TeacherOBJ pareJson(String jsonStr) throws JSONException {
        TeacherOBJ teacherOBJ = new TeacherOBJ();
        JSONObject jsonObject = new JSONObject(jsonStr);
        teacherOBJ.setTeacher_id(jsonObject.getString("teacher_id"));
        teacherOBJ.setTeacher_name(jsonObject.getString("teacher_name"));
        teacherOBJ.setTeacher_sex(jsonObject.getInt("teacher_sex"));
        teacherOBJ.setTeacher_college(jsonObject.getString("teacher_college"));
        return teacherOBJ;
    }
}
