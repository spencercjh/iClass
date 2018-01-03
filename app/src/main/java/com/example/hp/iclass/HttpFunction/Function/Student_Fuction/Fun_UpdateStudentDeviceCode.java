package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.UpdateStudentDeviceCode;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class Fun_UpdateStudentDeviceCode {
    public static boolean http_UpdateStudentDeviceCode(String student_id, String device_code) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateStudentDeviceCode";
        UpdateStudentDeviceCode thread = new UpdateStudentDeviceCode(url, student_id, device_code);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
