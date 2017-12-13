package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import android.support.annotation.NonNull;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.CountCheckStudent_Good;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_CountCheckStudent_Good {
    @NonNull
    public static String http_CountCheckStudent_Good(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/CountCheckStudent_Good";
        CountCheckStudent_Good thread = new CountCheckStudent_Good(url, subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th());
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getGood_check_student_num().trim();
        }
    }
}
