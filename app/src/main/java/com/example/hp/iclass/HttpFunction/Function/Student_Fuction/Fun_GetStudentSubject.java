package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.GetStudentSubject;
import com.example.hp.iclass.OBJ.StudentOBJ;

/**
 * Created by spencercjh on 2017/11/30.
 * iClass
 */

public class Fun_GetStudentSubject {
    public static String http_GetStudentSubject(StudentOBJ studentOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetStudentSubject";
        GetStudentSubject thread = new GetStudentSubject(url, studentOBJ.getStudent_id());
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getJsonstr();
        }
    }
}
