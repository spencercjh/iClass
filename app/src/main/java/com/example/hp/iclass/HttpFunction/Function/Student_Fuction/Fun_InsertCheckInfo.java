package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.InsertCheckInfo;
import com.example.hp.iclass.OBJ.CheckOBJ;

/**
 * Created by spencercjh on 2017/11/30.
 * iClass
 */

public class Fun_InsertCheckInfo {

    public static boolean http_InsertCheckInfo(CheckOBJ checkOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/InsertCheckInfo";
        InsertCheckInfo thread = new InsertCheckInfo(url, checkOBJ);
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