package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.QuaryOneSubjectTable;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/12/5.
 * iClass
 */

public class Fun_QuaryOneSubjectTable {

    public static boolean http_QuaryOneSubjectTable(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryOneSubjectTable";
        QuaryOneSubjectTable thread = new QuaryOneSubjectTable(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
        return thread.getFlag();
    }
}
