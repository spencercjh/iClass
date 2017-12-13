package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.QuaryStudentClass;

/**
 * Created by spencercjh on 2017/12/13.
 * iClass
 */

public class Fun_QuaryStudentClass {

    public static String http_QuaryStudentClass(String student_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryStudentClass";
        QuaryStudentClass thread = new QuaryStudentClass(url, student_id);
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getclass();
        } else {
            return "failed";
        }
    }
}
