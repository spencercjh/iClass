package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.GetStudentName;
import com.example.hp.iclass.OBJ.StudentOBJ;

/**
 * Created by spencercjh on 2017/11/29.
 * iClass
 */

public class Fun_GetStudentName {

    public static String http_GetStudentName(StudentOBJ studentOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/GetStudentName";
        GetStudentName thread = new GetStudentName(url, studentOBJ.getStudent_id());
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getStudent_name().trim();
        } else {
            return "failed";
        }
    }

    public static String http_GetStudentName(String student_id) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/GetStudentName";
        GetStudentName thread = new GetStudentName(url, student_id);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getStudent_name().trim();
        }
    }
}
