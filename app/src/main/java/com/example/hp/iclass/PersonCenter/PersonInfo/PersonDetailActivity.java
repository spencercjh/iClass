package com.example.hp.iclass.PersonCenter.PersonInfo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuarySubjectTh;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_GetStudentProperty;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetTeacherProperty;
import com.example.hp.iclass.HttpFunction.Json.Json_StudentProperty;
import com.example.hp.iclass.HttpFunction.Json.Json_TeacherProperty;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.PersonCenter.PersonInfo.ResetClassActivity;
import com.example.hp.iclass.PersonCenter.PersonInfo.ResetCollegeActivity;
import com.example.hp.iclass.PersonCenter.PersonInfo.ResetSexActivity;
import com.example.hp.iclass.R;

import org.json.JSONException;

public class PersonDetailActivity extends AppCompatActivity {
    private StudentOBJ studentOBJ = new StudentOBJ();
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private TextView tv_name;
    private TextView tv_id;
    private TextView tv_sex;
    private TextView tv_college;
    private TextView tv_class;
    private RelativeLayout re_college;
    private RelativeLayout re_class;
    private RelativeLayout re_sex;
    private String user = "";
    private int choice_user;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        user = (String) intent.getSerializableExtra("user");
        if (user.equals("teacher")) {
            choice_user = 1;
            setContentView(R.layout.activity_person_detail_teacher);
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (user.equals("student")) {
            choice_user = 0;
            setContentView(R.layout.activity_person_detail_student);
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
        fill_info();
        toolbar = (Toolbar) findViewById(R.id.tl_head);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle("       个人资料");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();
            }
        });
    }

    private void fill_info() {
        if (choice_user == 1) {
            tv_name = (TextView) findViewById(R.id.tv_name);
            tv_id = (TextView) findViewById(R.id.tv_id);
            tv_sex = (TextView) findViewById(R.id.tv_sex);
            tv_college = (TextView) findViewById(R.id.tv_college);
            try {
                teacherOBJ = Json_TeacherProperty.pareJson(Fun_GetTeacherProperty.http_GetTeacherProperty(teacherOBJ.getTeacher_id()));
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }
            if (teacherOBJ.getTeacher_name() == null || teacherOBJ.getTeacher_name().equals("null")) {
                tv_name.setText("未填写");
            } else {
                tv_name.setText(teacherOBJ.getTeacher_name());
            }
            if (teacherOBJ.getTeacher_id() == null || teacherOBJ.getTeacher_id().equals("null")) {
                tv_id.setText("未填写");
            } else {
                tv_id.setText(teacherOBJ.getTeacher_id());
            }
            if (teacherOBJ.getTeacher_sex() == -1) {
                tv_sex.setText("未填写");
            } else if (teacherOBJ.getTeacher_sex() == 1) {
                tv_sex.setText("女");
            } else if (teacherOBJ.getTeacher_sex() == 0) {
                tv_sex.setText("男");
            }
            if (teacherOBJ.getTeacher_college() == null || teacherOBJ.getTeacher_college().equals("null")) {
                tv_college.setText("未填写");
            } else {
                tv_college.setText(teacherOBJ.getTeacher_college());
            }
        } else if (choice_user == 0) {
            tv_name = (TextView) findViewById(R.id.tv_name);
            tv_id = (TextView) findViewById(R.id.tv_id);
            tv_sex = (TextView) findViewById(R.id.tv_sex);
            tv_college = (TextView) findViewById(R.id.tv_college);
            tv_class = (TextView) findViewById(R.id.tv_class);
            try {
                studentOBJ = Json_StudentProperty.pareJson(Fun_GetStudentProperty.http_GetStudentProperty(studentOBJ.getStudent_id()));
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }
            if (studentOBJ.getStudent_name() == null || studentOBJ.getStudent_name().equals("null")) {
                tv_name.setText("未填写");
            } else {
                tv_name.setText(studentOBJ.getStudent_name());
            }
            if (studentOBJ.getStudent_id() == null || studentOBJ.getStudent_id().equals("null")) {
                tv_id.setText("未填写");
            } else {
                tv_id.setText(studentOBJ.getStudent_id());
            }
            if (studentOBJ.getStudent_sex() == -1) {
                tv_sex.setText("未填写");
            } else if (studentOBJ.getStudent_sex() == 1) {
                tv_sex.setText("女");
            } else if (studentOBJ.getStudent_sex() == 0) {
                tv_sex.setText("男");
            }
            if (studentOBJ.getStudent_college() == null || studentOBJ.getStudent_college().equals("null")) {
                tv_college.setText("未填写");
            } else {
                tv_college.setText(studentOBJ.getStudent_college());
            }
            if (studentOBJ.getStudent_class() == null || studentOBJ.getStudent_class().equals("null")) {
                tv_class.setText("未填写");
            } else {
                tv_class.setText(studentOBJ.getStudent_class());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_only_fresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            fill_info();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        gotomain();
    }

    private void gotomain() {
        Intent intent = new Intent(PersonDetailActivity.this, MainActivity.class);
        if (choice_user == 1) {
            intent.putExtra("user", "teacher");
            intent.putExtra("teacherOBJ", teacherOBJ);
        } else if (choice_user == 0) {
            intent.putExtra("user", "student");
            intent.putExtra("studentOBJ", studentOBJ);
        }
        intent.putExtra("to_person_center", "true");
        startActivity(intent);
        finish();
    }

    public void resetsex(View view) {
        Intent intent = new Intent(this, ResetSexActivity.class);
        if (choice_user == 1) {
            intent.putExtra("teacherOBJ", teacherOBJ);
            intent.putExtra("user", "teacher");
        } else if (choice_user == 0) {
            intent.putExtra("studentOBJ", studentOBJ);
            intent.putExtra("user", "student");
        }
        startActivity(intent);
//        finish();
    }

    public void resetcollege(View view) {
        Intent intent = new Intent(this, ResetCollegeActivity.class);
        if (choice_user == 1) {
            intent.putExtra("teacherOBJ", teacherOBJ);
            intent.putExtra("user", "teacher");
        } else if (choice_user == 0) {
            intent.putExtra("studentOBJ", studentOBJ);
            intent.putExtra("user", "student");
        }
        startActivity(intent);
//        finish();
    }

    public void resetclass(View view) {
        Intent intent = new Intent(this, ResetClassActivity.class);
        intent.putExtra("studentOBJ", studentOBJ);
        intent.putExtra("user", "student");
        startActivity(intent);
//        finish();
    }
}