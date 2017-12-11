package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.QuaryTeacherPassword;

/**
 * Created by spencercjh on 2017/12/1.
 * iClass
 */

public class Fun_QuaryTeacherPassword {

    public static String http_QuaryTeacherPassword(String teacher_id, String teacher_name) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryTeacherPassword";
        QuaryTeacherPassword thread = new QuaryTeacherPassword(url, teacher_id, teacher_name);
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            if (thread.getTeacher_password() == null) {
                return "failed";
            } else {
                return thread.getTeacher_password();
            }
        } else {
            return "failed";
        }
    }
}
