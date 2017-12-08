package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.GetSubjectClassType;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/30.
 * iClass
 */

public class Fun_GetSubjectClassType {
    public static int http_GetSubjectClassType(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/GetSubjectClassType";
        GetSubjectClassType thread = new GetSubjectClassType(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return -1;
        } else {
            int class_type;
            try {
                class_type = Integer.parseInt(thread.getClass_type());
            } catch (NumberFormatException e) {
                class_type = -1;
            }
            return class_type;
        }
    }
}
