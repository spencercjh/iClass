package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.UpdateSubjectTh;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Fun_UpdateSubjectTh {

    public static void http_UpdateSubjectTh(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateSubjectTh";
        UpdateSubjectTh thread = new UpdateSubjectTh(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
    }
}
