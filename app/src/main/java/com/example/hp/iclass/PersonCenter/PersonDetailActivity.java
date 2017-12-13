package com.example.hp.iclass.PersonCenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

public class PersonDetailActivity extends AppCompatActivity {
    private StudentOBJ studentOBJ = new StudentOBJ();
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private String user = "";
    private int choice_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
    }

    public void onBackPressed() {
        gotomain();
    }

    private void gotomain() {
        finish();
    }
}
