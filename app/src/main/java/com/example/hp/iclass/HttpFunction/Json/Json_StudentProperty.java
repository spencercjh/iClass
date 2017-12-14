package com.example.hp.iclass.HttpFunction.Json;

import com.example.hp.iclass.OBJ.StudentOBJ;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Json_StudentProperty {
    public static StudentOBJ pareJson(String jsonStr) throws JSONException {
        StudentOBJ studentOBJ = new StudentOBJ();
        JSONObject jsonObject = new JSONObject(jsonStr);
        studentOBJ.setStudent_id(jsonObject.getString("student_id"));
        studentOBJ.setStudent_name(jsonObject.getString("student_name"));
        studentOBJ.setStudent_sex(jsonObject.getInt("student_sex"));
        studentOBJ.setStudent_college(jsonObject.getString("student_college"));
        studentOBJ.setStudent_class(jsonObject.getString("student_class"));
        return studentOBJ;
    }
}
