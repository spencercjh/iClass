package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.UpdateTeacherCollege;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_UpdateTeacherCollege {

    public static boolean http_UpdateTeacherCollege(String teacher_id,String teacher_college) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateTeacherCollege";
        UpdateTeacherCollege thread=new UpdateTeacherCollege(url,teacher_id,teacher_college);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
