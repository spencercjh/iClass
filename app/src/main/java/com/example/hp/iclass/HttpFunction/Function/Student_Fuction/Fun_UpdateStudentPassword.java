package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.UpdateStudentPassword;

/**
 * Created by spencercjh on 2017/12/13.
 * iClass
 */

public class Fun_UpdateStudentPassword {

    public static boolean http_UpdateStudentPassword(String student_id,String student_password) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateStudentPassword";
        UpdateStudentPassword thread=new UpdateStudentPassword(url,student_id,student_password);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
