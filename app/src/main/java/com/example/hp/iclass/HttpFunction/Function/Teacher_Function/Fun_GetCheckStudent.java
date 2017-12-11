package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.GetCheckStudent;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Fun_GetCheckStudent {

    public static String http_GetCheckStudent(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetCheckStudent";
        GetCheckStudent thread = new GetCheckStudent(url, subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th());
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getJsonstr();
        }
    }
}
