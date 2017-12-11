package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.QuaryCheckSituation;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/12/3.
 * iClass
 */

public class Fun_QuaryCheckSituation {

    public static int http_QuaryCheckSituation(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryCheckSituation";
        QuaryCheckSituation thread = new QuaryCheckSituation(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getCheck_Situation();
        } else {
            return -1;
        }
    }
}
