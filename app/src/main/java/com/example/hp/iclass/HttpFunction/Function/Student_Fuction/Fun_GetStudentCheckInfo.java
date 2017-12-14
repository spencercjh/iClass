package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.GetStudentCheckInfo;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_GetStudentCheckInfo {

    public static String http_GetStudentCheckInfo(String student_id, String subject_id, int subject_th) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetStudentCheckInfo";
        GetStudentCheckInfo thread = new GetStudentCheckInfo(url, student_id, subject_id, subject_th);
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            return thread.getJsonstr();
        } else {
            return "failed";
        }
    }
}
