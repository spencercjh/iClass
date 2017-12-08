package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.LoginStudent;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Fun_StudentLogin {
    private final static String success = "100";
    private final static String failed = "200";

    public static int http_LoginStudent(String student_id, String student_password) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/LoginStudent";
        LoginStudent thread = new LoginStudent(url, student_id, student_password);
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
