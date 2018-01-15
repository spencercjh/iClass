package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import android.support.annotation.NonNull;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.CountOneStudentAllCheck_AllTypes;

/**
 * Created by spencercjh on 2018/1/15.
 * iClass
 */

public class Fun_CountOneStudentAllCheck_AllTypes {
    @NonNull
    public static String http_CountOneStudentAllCheck_AllTypes(String subject_id, String student_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/CountOneStudentAllCheck_AllTypes";
        CountOneStudentAllCheck_AllTypes thread = new CountOneStudentAllCheck_AllTypes(url, subject_id, student_id);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getNum_all().trim();
        }
    }
}
