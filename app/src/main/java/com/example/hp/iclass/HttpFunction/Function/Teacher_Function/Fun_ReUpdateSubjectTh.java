package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.ReUpdateSubjectTh;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Fun_ReUpdateSubjectTh {

    public static void http_ReUpdateSubjectTh(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/ReUpdateSubjectTh";
        ReUpdateSubjectTh thread = new ReUpdateSubjectTh(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
    }
}
