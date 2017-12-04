package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.QuaryStudentPassword;

/**
 * Created by spencercjh on 2017/12/1.
 * iClass
 */

public class Fun_QuaryStudentPassword {

    public static String http_QuaryStudentPassword(String student_id, String student_name) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/QuaryStudentPassword";
        QuaryStudentPassword thread = new QuaryStudentPassword(url, student_id, student_name);
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            if (thread.getStudent_password() == null) {
                return "failed";
            } else {
                return thread.getStudent_password();
            }
        } else {
            return "failed";
        }
    }
}
