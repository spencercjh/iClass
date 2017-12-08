package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.GetTeacherSubject;
import com.example.hp.iclass.OBJ.TeacherOBJ;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Fun_GetTeacherSubject {

    public static String http_GetTeacherSubject(TeacherOBJ teacherOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetOneTeacherSubject";
        GetTeacherSubject thread = new GetTeacherSubject(url, teacherOBJ.getTeacher_id());
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getJsonstr();
        }
    }
}

