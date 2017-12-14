package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.UpdateStudentSex;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_UpdateStudentSex {

    public static boolean http_UpdateStudentSex(String student_id,int student_sex) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateStudentSex";
        UpdateStudentSex thread=new UpdateStudentSex(url,student_id,student_sex);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
