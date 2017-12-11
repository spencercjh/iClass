package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.InsertCheckInfo_Teacher;
import com.example.hp.iclass.OBJ.CheckOBJ;

/**
 * Created by spencercjh on 2017/12/9.
 * iClass
 */

public class Fun_InsertCheckInfo_Teacher {
    public static boolean http_InsertCheckInfo_Teacher(CheckOBJ checkOBJ) throws InterruptedException {
        String ip= IPCondition.server_ip;
        String url = ip + "iClass_Sever/InsertCheckInfo_Teacher";
        InsertCheckInfo_Teacher thread=new InsertCheckInfo_Teacher(url,checkOBJ);
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