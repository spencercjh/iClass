package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.DeleteCheckInfo_Teacher;

/**
 * Created by spencercjh on 2017/12/12.
 * iClass
 */

public class Fun_DeleteCheckInfo_Teacher {

    public static boolean http_DeleteCheckInfo_Teacher(String subject_id,int subject_th,String student_id) throws InterruptedException {
        String ip= IPCondition.server_ip;
        String url = ip + "iClass_Sever/DeleteCheckInfo_Teacher";
        DeleteCheckInfo_Teacher thread=new DeleteCheckInfo_Teacher(url,subject_id,subject_th,student_id);
        thread.start();
        thread.join();
        if (!thread.getFlag()) {
            return false;
        } else {
            if (thread.getstate().contains("success")) {
                return true;
            } else if (thread.getstate().contains("failed")) {
                return false;
            }
            return false;
        }
    }
}