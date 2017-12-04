package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.InsertSubject;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/28.
 * iClass
 */

public class Fun_InsertSubject {

    public static boolean http_InsertSubject(SubjectOBJ subjectOBJ, StudentOBJ studentOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/InsertSubject";
        InsertSubject thread = new InsertSubject(url, subjectOBJ, studentOBJ);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return false;  //servlet error
        } else {
            if (thread.getstate().contains("success")) {
                return true;
            } else if (thread.getstate().contains("failed")) {
                return false;
            }
            return false;
        }
    }
}