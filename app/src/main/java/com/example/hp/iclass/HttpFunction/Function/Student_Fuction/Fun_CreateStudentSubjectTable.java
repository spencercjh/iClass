package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.CreateStudentSubjectTable;
import com.example.hp.iclass.OBJ.StudentOBJ;

/**
 * Created by spencercjh on 2017/11/30.
 * iClass
 */

public class Fun_CreateStudentSubjectTable {

    public static void http_CreateStudentSubjectTable(StudentOBJ studentOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/CreateStudentSubjectTable";
        CreateStudentSubjectTable thread = new CreateStudentSubjectTable(url, studentOBJ.getStudent_id());
        thread.start();
        thread.join();
    }
}
