package com.example.hp.iclass.HistoryActivity.Teacher.Teacher_History_Seat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.R;

public class SeatErrorActivity_Teacher extends AppCompatActivity {
    private StudentOBJ studentOBJ = new StudentOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private int class_type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seaterror);
        Intent intent = getIntent();
        studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
    }

    private void goback() {
        finish();
    }

    public void onBackPressed() {
        goback();
    }
}
