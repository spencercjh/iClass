package com.example.hp.iclass.PersonCenter.PersonInfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_UpdateStudentCollege;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_UpdateTeacherCollege;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

public class ResetCollegeActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tl_head;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private String user = "";
    private int choice_user;
    private String select_college;
    private Button button;
    private Handler handler = new Handler() {   //修改成功后延迟1秒返回前一活动
        @Override
        public void handleMessage(Message msg) {
            gotolast();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_college);
        Intent intent = getIntent();
        user = (String) intent.getSerializableExtra("user");
        if (user.equals("teacher")) {
            choice_user = 1;
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (user.equals("student")) {
            choice_user = 0;
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
        button=(Button)findViewById(R.id.button2);
        button.setOnClickListener(this);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("             更改学院");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotolast();
            }
        });
        String[] mItems = getResources().getStringArray(R.array.college);
//      建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//      绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] college = getResources().getStringArray(R.array.college);
//                Toast.makeText(ResetCollegeActivity.this, "你点击的是:" + college[pos], Toast.LENGTH_SHORT).show();
                select_college = college[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void gotolast() {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button2) {
            if (choice_user == 1) {
                try {
                    if (Fun_UpdateTeacherCollege.http_UpdateTeacherCollege(teacherOBJ.getTeacher_id(), select_college)) {
                        Toast.makeText(ResetCollegeActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessageDelayed(0, 500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (choice_user == 0) {
                try {
                    if (Fun_UpdateStudentCollege.http_UpdateStudentCollege(studentOBJ.getStudent_id(), select_college)) {
                        Toast.makeText(ResetCollegeActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessageDelayed(0, 500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onBackPressed() {
        gotolast();
    }
}
