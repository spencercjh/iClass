package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.QuaryStudentDeviceCode;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class Fun_QuaryStudentDeviceCode {
    public static String http_QuaryStudentDeviceCode(String student_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryStudentDeviceCode";
        QuaryStudentDeviceCode thread=new QuaryStudentDeviceCode(url,student_id);
        thread.start();
        thread.join();
        if(thread.getFlag()){
            return thread.getDevice_code();
        }else{
            return "failed";
        }
    }
}
