package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.UpdateTeacherPassword;

/**
 * Created by spencercjh on 2017/12/13.
 * iClass
 */

public class Fun_UpdateTeacherPassword {

    public static boolean http_UpdateTeacherPassword(String teacher_id,String teacher_password) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateTeacherPassword";
        UpdateTeacherPassword thread=new UpdateTeacherPassword(url,teacher_id,teacher_password);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
