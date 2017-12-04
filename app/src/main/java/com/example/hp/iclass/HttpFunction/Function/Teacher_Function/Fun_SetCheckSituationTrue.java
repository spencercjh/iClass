package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.SetCheckSituationTrue;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/28.
 * iClass
 */

public class Fun_SetCheckSituationTrue {

    public static void http_SetCheckSituationTrue(SubjectOBJ subjectOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/SetCheckSituationTrue";
        SetCheckSituationTrue thread = new SetCheckSituationTrue(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
    }
}