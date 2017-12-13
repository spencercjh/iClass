package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.QuaryStudentCollege;

/**
 * Created by spencercjh on 2017/12/13.
 * iClass
 */

public class Fun_QuaryStudentCollege {

    public static String http_QuaryStudentCollege(String student_id) throws InterruptedException {
        String ip= IPCondition.server_ip;
        String url=ip+"iClass_Sever/QuaryStudentCollege";
        QuaryStudentCollege thread=new QuaryStudentCollege(url,student_id);
        thread.start();
        thread.join();
        if(thread.getFlag()){
            return thread.getCollege();
        }else{
            return "failed";
        }
    }
}
