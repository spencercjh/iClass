package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.QuaryTeacherID;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_QuaryTeacherID {

    public static String http_QuaryTeacherID(String subject_id) throws InterruptedException {
        String ip= IPCondition.server_ip;
        String url = ip+"iClass_Sever/QuaryTeacherID";
        QuaryTeacherID thread=new QuaryTeacherID(url,subject_id);
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            if (thread.getTeacher_id() == null) {
                return "failed";
            } else {
                return thread.getTeacher_id();
            }
        } else {
            return "failed";
        }
    }
}
