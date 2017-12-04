package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.SetCheckSituationFalse;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/28.
 * iClass
 */

public class Fun_SetCheckSituationFalse {

    public static void http_SetCheckSituationFalse(SubjectOBJ subjectOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/SetCheckSituationFalse";
        SetCheckSituationFalse thread = new SetCheckSituationFalse(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
    }
}