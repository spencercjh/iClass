package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.UpdateStudentScore;
import com.example.hp.iclass.OBJ.CheckOBJ;

/**
 * Created by spencercjh on 2017/12/3.
 * iClass
 */

public class Fun_UpdateStudentScore {
    public static void http_UpdateStudentScore(CheckOBJ checkOBJ, int score) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/UpdateStudentScore";
        UpdateStudentScore thread = new UpdateStudentScore(url, checkOBJ.getSubject_id(), checkOBJ.getSubject_th(), checkOBJ.getStudent_id(), score);
        thread.start();
        thread.join();
    }

    public static void http_UpdateStudentScore(String subject_id, int subject_th, String student_id, int score) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/UpdateStudentScore";
        UpdateStudentScore thread = new UpdateStudentScore(url, subject_id, subject_th, student_id, score);
        thread.start();
        thread.join();
    }
}
