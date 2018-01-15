package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import android.support.annotation.NonNull;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.CountOneStudentAllCheck_Checkin;

/**
 * Created by spencercjh on 2018/1/15.
 * iClass
 */

public class Fun_CountOneStudentAllCheck_Checkin {
    @NonNull
    public static String http_CountOneStudentAllCheck_Checkin(String subject_id, String student_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/CountOneStudentAllCheck_Checkin";
        CountOneStudentAllCheck_Checkin thread = new CountOneStudentAllCheck_Checkin(url, subject_id, student_id);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getNum_checkin().trim();
        }
    }
}
