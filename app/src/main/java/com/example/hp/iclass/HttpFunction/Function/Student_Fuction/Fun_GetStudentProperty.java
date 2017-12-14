package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.GetStudentProperty;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_GetStudentProperty {

    public static String http_GetStudentProperty(String student_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetStudentProperty";
        GetStudentProperty thread = new GetStudentProperty(url, student_id);
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getJsonstr();
        } else {
            return "failed";
        }
    }
}
