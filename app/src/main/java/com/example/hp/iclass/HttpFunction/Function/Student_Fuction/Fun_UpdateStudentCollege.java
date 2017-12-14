package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.UpdateStudentCollege;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_UpdateStudentCollege {

    public static boolean http_UpdateStudentCollege(String student_id,String student_college) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateStudentCollege";
        UpdateStudentCollege thread=new UpdateStudentCollege(url,student_id,student_college);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
