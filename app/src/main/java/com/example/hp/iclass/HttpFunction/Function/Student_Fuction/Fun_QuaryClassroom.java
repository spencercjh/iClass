package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.QuaryClassroom;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class Fun_QuaryClassroom {

    public static String http_QuaryClassroom(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryCheckSituation";
        QuaryClassroom thread = new QuaryClassroom(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getClassroom();
        } else {
            return "failed";
        }
    }
}

