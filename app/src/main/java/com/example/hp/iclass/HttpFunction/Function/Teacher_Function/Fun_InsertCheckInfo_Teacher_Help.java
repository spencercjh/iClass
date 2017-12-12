package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.InsertCheckInfo_Teacher_Help;
import com.example.hp.iclass.OBJ.CheckOBJ;

/**
 * Created by spencercjh on 2017/12/12.
 * iClass
 */

public class Fun_InsertCheckInfo_Teacher_Help {

    public static boolean http_InsertCheckInfo_Teacher_Help(CheckOBJ checkOBJ) throws InterruptedException {
        String ip= IPCondition.server_ip;
        String url = ip + "iClass_Sever/InsertCheckInfo_Teacher_Help";
        InsertCheckInfo_Teacher_Help thread=new InsertCheckInfo_Teacher_Help(url,checkOBJ);
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