package com.example.hp.iclass.HistoryActivity.Teacher.OutputExcel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hp.iclass.HistoryActivity.Common.ChooseTimeActivity;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

public class RecentOutput extends AppCompatActivity {
    Button bt_open;
    Button bt_open1;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private String user = "";
    private int choice_user;
    private Toolbar tl_head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_output);
        bt_open=(Button)findViewById(R.id.bt_open);
        bt_open1=(Button)findViewById(R.id.bt_open1);
        Intent intent = getIntent();
        if (user.equals("teacher")) {
            choice_user = 1;
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (user.equals("student")) {
            choice_user = 0;
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
        user = (String) intent.getSerializableExtra("user");
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setTitle("           ");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotolast();
            }
        });
    }
    public void open(View view) {
        Toast.makeText(getApplicationContext(),"直接打开",Toast.LENGTH_SHORT).show();
    }
    public void open1(View view) {
        Toast.makeText(getApplicationContext(),"资源管理器打开",Toast.LENGTH_SHORT).show();
    }
    private void gotolast() {
        Intent intent = new Intent(this, OutputInfo.class);
        if (choice_user == 1) {
            intent.putExtra("teacherOBJ", teacherOBJ);
            intent.putExtra("user", "teacher");
        } else if (choice_user == 0) {
            intent.putExtra("studentOBJ", studentOBJ);
            intent.putExtra("user", "student");
        }
        startActivity(intent);
        finish();
    }
}
