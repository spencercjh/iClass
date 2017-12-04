package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.CountCheckStudent_AllTypes;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class Fun_CountCheckStudent_AllTypes {

    public static String http_CountCheckStudent_AllTypes(SubjectOBJ subjectOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/CountCheckStudent_AllTypes";
        CountCheckStudent_AllTypes thread = new CountCheckStudent_AllTypes(url, subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th());
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getPresent_check_student_num().trim();
        }
    }
}
