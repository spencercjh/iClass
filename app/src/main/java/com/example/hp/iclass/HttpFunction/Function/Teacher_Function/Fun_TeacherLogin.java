package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.LoginTeacher;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Fun_TeacherLogin {
    private final static String success = "100";
    private final static String failed = "200";

    public static int http_LoginTeacher(String teacher_id, String teacher_password) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/LoginTeacher";
        LoginTeacher thread = new LoginTeacher(url, teacher_id, teacher_password);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return -1;  //servlet error
        } else {
            if (thread.getLogin_state().contains(success)) {
                return 1;   //login success
            } else if (thread.getLogin_state().contains(failed)) {
                return 0;   //login failed
            }
        }
        return -1;
    }
}
