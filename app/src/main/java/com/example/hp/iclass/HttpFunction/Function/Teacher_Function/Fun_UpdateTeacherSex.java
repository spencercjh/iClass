package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.UpdateTeacherSex;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_UpdateTeacherSex {

    public static boolean http_UpdateTeacherSex(String teacher_id,int teacher_sex) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateTeacherSex";
        UpdateTeacherSex thread=new UpdateTeacherSex(url,teacher_id,teacher_sex);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
