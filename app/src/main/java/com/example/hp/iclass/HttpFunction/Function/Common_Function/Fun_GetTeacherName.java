package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.GetTeacherName;
import com.example.hp.iclass.OBJ.TeacherOBJ;

/**
 * Created by spencercjh on 2017/11/29.
 * iClass
 */

public class Fun_GetTeacherName {

    public static String http_GetTeacherName(TeacherOBJ teacherOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/GetTeacherName";
        GetTeacherName thread = new GetTeacherName(url, teacherOBJ.getTeacher_id());
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getTeacher_name().trim();
        } else {
            return "failed";
        }
    }

    public static String http_GetTeacherName(String teahcer_id) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/GetTeacherName";
        GetTeacherName thread = new GetTeacherName(url, teahcer_id);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getTeacher_name().trim();
        }
    }
}