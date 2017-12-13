package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import android.support.annotation.NonNull;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.CountCheckStudent_Late;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_CountCheckStudent_Late {
    @NonNull
    public static String http_CountCheckStudent_Late(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/CountCheckStudent_Late";
        CountCheckStudent_Late thread=new CountCheckStudent_Late(url,subjectOBJ.getSubject_id(),subjectOBJ.getSubject_th());
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getLate_check_student_num().trim();
        }
    }
}

