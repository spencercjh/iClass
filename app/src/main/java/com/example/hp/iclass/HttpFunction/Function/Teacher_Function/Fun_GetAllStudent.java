package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.GetAllStudent;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/12/5.
 * iClass
 */

public class Fun_GetAllStudent {

    public static String http_GetAllStudent(SubjectOBJ subjectOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/GetAllStudent";
        GetAllStudent thread=new GetAllStudent(url,subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
        if(thread.getFlag()){
            return thread.getJsonstr();
        }else{
            return "failed";
        }
    }
}
