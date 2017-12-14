package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.UpdateStudentClass;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_UpdateStudentClass {

    public static boolean http_UpdateStudentClass(String student_id, String student_class) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateStudentClass";
        UpdateStudentClass thread = new UpdateStudentClass(url, student_id, student_class);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
