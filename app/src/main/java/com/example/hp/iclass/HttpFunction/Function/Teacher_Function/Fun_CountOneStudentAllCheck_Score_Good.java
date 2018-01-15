package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.CountOneStudentAllCheck_Score_Good;

/**
 * Created by spencercjh on 2018/1/15.
 * iClass
 */

public class Fun_CountOneStudentAllCheck_Score_Good {
    public static String http_CountOneStudentAllCheck_Score_Good(String subject_id, String student_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/CountOneStudentAllCheck_Score_Good";
        CountOneStudentAllCheck_Score_Good thread = new CountOneStudentAllCheck_Score_Good(url, subject_id, student_id);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getNum_score_good().trim();
        }
    }
}
