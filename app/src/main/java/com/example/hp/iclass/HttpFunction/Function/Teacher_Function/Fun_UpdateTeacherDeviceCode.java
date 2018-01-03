package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.UpdateTeacherDeviceCode;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class Fun_UpdateTeacherDeviceCode {
    public static boolean http_UpdateTeacherDeviceCode(String teacher_id, String device_code) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/ UpdateTeacherDeviceCode";
        UpdateTeacherDeviceCode thread = new UpdateTeacherDeviceCode(url, teacher_id, device_code);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
