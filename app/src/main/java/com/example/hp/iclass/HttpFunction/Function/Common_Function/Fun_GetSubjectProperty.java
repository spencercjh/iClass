package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.GetSubjectProperty;

/**
 * Created by spencercjh on 2017/11/30.
 * iClass
 */

public class Fun_GetSubjectProperty {

    public static String http_GetSubjectProperty(String subject_id) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetSubjectProperty";
        GetSubjectProperty thread = new GetSubjectProperty(url, subject_id);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return "failed";
        } else {
            return thread.getJsonstr();
        }
    }
}

