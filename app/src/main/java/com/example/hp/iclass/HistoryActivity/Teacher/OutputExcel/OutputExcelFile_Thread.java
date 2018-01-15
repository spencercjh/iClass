package com.example.hp.iclass.HistoryActivity.Teacher.OutputExcel;

import android.content.Context;

import com.example.hp.iclass.OBJ.SubjectSumUpOBJ;

/**
 * Created by spencercjh on 2018/1/15.
 * iClass
 */

class OutputExcelFile_Thread extends Thread {
    private Context context;
    private SubjectSumUpOBJ Subject_Check_Info;
    private boolean flag;

    OutputExcelFile_Thread() {

    }

    OutputExcelFile_Thread(Context context, SubjectSumUpOBJ subject_Check_Info) {
        this.context = context;
        this.Subject_Check_Info = subject_Check_Info;
    }

    /*在run中调用doGet*/
    @Override
    public void run() {
        try {
            doOutputExcelFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doOutputExcelFile() throws Exception {
        flag = ExcelUtil.writeExcel(context, Subject_Check_Info);
    }

    public boolean isFlag() {
        return flag;
    }

}

