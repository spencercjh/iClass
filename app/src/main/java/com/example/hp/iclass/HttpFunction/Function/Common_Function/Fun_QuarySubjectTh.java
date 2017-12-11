package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.QuarySubjectTh;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Fun_QuarySubjectTh {

    public static int http_QuarySubjectTh(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuarySubjectTh";
        QuarySubjectTh thread = new QuarySubjectTh(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return 0;
        } else {
            return Integer.parseInt(thread.getPresent_subject_th().trim());
        }
    }
}
