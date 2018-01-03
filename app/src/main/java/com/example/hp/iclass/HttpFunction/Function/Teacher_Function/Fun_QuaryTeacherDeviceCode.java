package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.QuaryTeacherDeviceCode;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class Fun_QuaryTeacherDeviceCode {
    public static String http_QuaryTeacherDeviceCode(String teacher_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryTeacherDeviceCode";
        QuaryTeacherDeviceCode thread = new QuaryTeacherDeviceCode(url, teacher_id);
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getDevice_code();
        } else {
            return "failed";
        }
    }
}
