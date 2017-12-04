package com.example.hp.iclass.HttpFunction.Function.Student_Fuction;

import com.example.hp.iclass.HttpFunction.Thread.Student_Thread.CountStudentCheckInfoTimes;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/12/1.
 * iClass
 */

public class Fun_CountStudentCheckInfoTimes {

    public static boolean http_CountStudentCheckInfoTimes(StudentOBJ studentOBJ, SubjectOBJ subjectOBJ) throws InterruptedException {
        String url = "http://192.168.3.17:8080/iClass_Sever/CountStudentCheckInfoTimes";
        CountStudentCheckInfoTimes thread = new CountStudentCheckInfoTimes(url, studentOBJ.getStudent_id(), subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th());
        thread.start();
        thread.join();
        if (thread.getFlag()) {
            int ans;
            try {
                ans = Integer.parseInt(thread.getPresent_check_info_num());
            } catch (NumberFormatException e) {
                ans = -1;
            }
            return ans == 1;
        } else {
            return false;
        }
    }
}
