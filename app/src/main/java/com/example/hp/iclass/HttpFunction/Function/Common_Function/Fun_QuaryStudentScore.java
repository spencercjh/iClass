package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.QuaryStudentScore;
import com.example.hp.iclass.OBJ.CheckOBJ;

/**
 * Created by spencercjh on 2017/12/3.
 * iClass
 */

public class Fun_QuaryStudentScore {
    public static int http_QuaryStudentScore(CheckOBJ checkOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/QuaryStudentScore";
        QuaryStudentScore thread = new QuaryStudentScore(url, checkOBJ.getSubject_id(), checkOBJ.getSubject_th(), checkOBJ.getStudent_id());
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getScore();
        } else {
            return 0;
        }
    }
}
