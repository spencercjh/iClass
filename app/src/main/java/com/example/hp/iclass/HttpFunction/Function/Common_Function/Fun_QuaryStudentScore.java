package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.QuaryStudentScore;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/12/3.
 * iClass
 */

public class Fun_QuaryStudentScore {
    public static int http_QuaryStudentScore(CheckOBJ checkOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryStudentScore";
        QuaryStudentScore thread = new QuaryStudentScore(url, checkOBJ.getSubject_id(), checkOBJ.getSubject_th(), checkOBJ.getStudent_id());
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getScore();
        } else {
            return 0;
        }
    }

    public static int http_QuaryStudentScore(SubjectOBJ subjectOBJ, StudentOBJ studentOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryStudentScore";
        QuaryStudentScore thread = new QuaryStudentScore(url, subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th(), studentOBJ.getStudent_id());
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getScore();
        } else {
            return 0;
        }
    }
}
