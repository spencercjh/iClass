package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.GetTeacherProperty;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_GetTeacherProperty {

    public static String http_GetTeacherProperty(String teacher_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetTeacherProperty";
        GetTeacherProperty thread = new GetTeacherProperty(url, teacher_id);
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getJsonstr();
        } else {
            return "failed";
        }
    }
}
