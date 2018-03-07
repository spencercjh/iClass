package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.AddStudentToSubject;

/**
 * Created by spencercjh on 2018/3/7.
 * iClass
 */

public class Fun_AddStudentToSubject {
    public static boolean http_AddStudentToSubject(String subject_id, String student_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/AddStudentToSubject";
        AddStudentToSubject thread = new AddStudentToSubject(url, student_id, subject_id);
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
