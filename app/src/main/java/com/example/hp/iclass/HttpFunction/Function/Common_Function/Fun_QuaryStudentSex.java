package com.example.hp.iclass.HttpFunction.Function.Common_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Common_Thread.QuaryStudentSex;

/**
 * Created by spencercjh on 2017/12/13.
 * iClass
 */

public class Fun_QuaryStudentSex {

    public static int http_QuaryStudentSex(String student_id) throws InterruptedException {
        String ip= IPCondition.server_ip;
        String url = ip + "iClass_Sever/QuaryStudentSex";
        QuaryStudentSex thread=new QuaryStudentSex(url,student_id);
        thread.start();
        thread.join();
        if(thread.getFlag()){
            return thread.getSex();
        }else{
            return -1;
        }
    }
}
