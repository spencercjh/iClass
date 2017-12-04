package com.example.hp.iclass.HttpFunction.Json;

import com.example.hp.iclass.OBJ.SubjectOBJ;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by spencercjh on 2017/11/30.
 * iClass
 */

public class Json_SubjectProperty {
    public static SubjectOBJ pareJson(String jsonStr) throws JSONException {
        SubjectOBJ subjectOBJ = new SubjectOBJ();
        JSONObject jsonObject = new JSONObject(jsonStr);
        subjectOBJ.setSubject_id(jsonObject.getString("subject_id"));
        subjectOBJ.setSubject_name(jsonObject.getString("subject_name"));
        subjectOBJ.setTeacher_id(jsonObject.getString("teacher_id"));
        subjectOBJ.setClassroom(jsonObject.getString("classroom"));
        return subjectOBJ;
    }
}
