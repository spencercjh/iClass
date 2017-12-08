package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.CountOneStudentCheckNum;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/12/6.
 * iClass
 */

public class Fun_CountOneStudentCheckNum {

    public static int http_CountOneStudentCheckNum(SubjectOBJ subjectOBJ, StudentOBJ studentOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/CountOneStudentCheckNum";
        CountOneStudentCheckNum thread = new CountOneStudentCheckNum(url, subjectOBJ.getSubject_id(), studentOBJ.getStudent_id());
        thread.start();
        thread.join();
        int all_check_num;
        if (thread.getFlag()) {
            try {
                all_check_num = Integer.parseInt(thread.getAll_check_num());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                all_check_num = -1;
            }
        } else {
            all_check_num = -1;
        }
        return all_check_num;
    }
}
