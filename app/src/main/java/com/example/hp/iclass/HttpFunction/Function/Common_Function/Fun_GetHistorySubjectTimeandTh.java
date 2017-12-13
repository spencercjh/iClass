package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.GetHistorySubjectTimeandTh;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class Fun_GetHistorySubjectTimeandTh {

    public static String http_GetHistorySubjectTimeandTh(String subject_id, String teacher_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetHistorySubjectTimeandTh";
        GetHistorySubjectTimeandTh thread = new GetHistorySubjectTimeandTh(url, subject_id, teacher_id);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getJsonstr();
        }
    }
}