package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.GetStartTime;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/30.
 * iClass
 */

public class Fun_GetStartTime {

    public static String http_GetStartTime(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetStartTime";
        GetStartTime thread = new GetStartTime(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getStart_time();
        } else {
            return "failed";
        }
    }
}
