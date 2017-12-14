package com.example.hp.iclass.PersonCenter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

public class SettingActivity extends AppCompatActivity {
    private StudentOBJ studentOBJ = new StudentOBJ();
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private String user = "";
    private int choice_user;
    private Toolbar tl_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setTitle("");
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();
            }
        });
        Intent intent = getIntent();
        user = (String) intent.getSerializableExtra("user");
        if (user.equals("teacher")) {
            choice_user = 1;
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (user.equals("student")) {
            choice_user = 0;
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
    }

    public void onBackPressed() {
        gotomain();
    }

    private void gotomain() {
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        if (choice_user == 1) {
            intent.putExtra("teacherOBJ", teacherOBJ);
            intent.putExtra("user", "teacher");
        } else if (choice_user == 0) {
            intent.putExtra("studentOBJ", studentOBJ);
            intent.putExtra("user", "student");
        }
        intent.putExtra("to_person_center", "true");
        startActivity(intent);
        finish();
    }
}
