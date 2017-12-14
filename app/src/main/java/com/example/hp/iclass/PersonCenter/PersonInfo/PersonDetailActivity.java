package com.example.hp.iclass.PersonCenter.PersonInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        user = (String) intent.getSerializableExtra("user");
        if (user.equals("teacher")) {
            choice_user = 1;
            setContentView(R.layout.activity_person_detail_teacher);
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
            tv_name = (TextView) findViewById(R.id.tv_name);
            tv_id = (TextView) findViewById(R.id.tv_id);
            tv_sex = (TextView) findViewById(R.id.tv_sex);
            tv_college = (TextView) findViewById(R.id.tv_college);
            try {
                teacherOBJ = Json_TeacherProperty.pareJson(Fun_GetTeacherProperty.http_GetTeacherProperty(teacherOBJ.getTeacher_id()));
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }
            if (teacherOBJ.getTeacher_name().length() < 1 || teacherOBJ.getTeacher_name().equals("null")) {
                tv_name.setText("未填写");
            } else {
                tv_name.setText(teacherOBJ.getTeacher_name());
            }
            if (teacherOBJ.getTeacher_id().length() < 1 || teacherOBJ.getTeacher_id().equals("null")) {
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
            if (teacherOBJ.getTeacher_college().length() < 1 || teacherOBJ.getTeacher_college().equals("null")) {
                tv_college.setText("未填写");
            } else {
                tv_college.setText(teacherOBJ.getTeacher_college());
            }
        } else if (user.equals("student")) {
            choice_user = 0;
            setContentView(R.layout.activity_person_detail_student);
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
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
            if (studentOBJ.getStudent_name().length() < 1 || studentOBJ.getStudent_name().equals("null")) {
                tv_name.setText("未填写");
            } else {
                tv_name.setText(studentOBJ.getStudent_name());
            }
            if (studentOBJ.getStudent_id().length() < 1 || studentOBJ.getStudent_id().equals("null")) {
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
            if (studentOBJ.getStudent_college().length() < 1 || studentOBJ.getStudent_college().equals("null")) {
                tv_college.setText("未填写");
            } else {
                tv_college.setText(studentOBJ.getStudent_college());
            }
            if (studentOBJ.getStudent_class().length() < 1 || studentOBJ.getStudent_class().equals("null")) {
                tv_class.setText("未填写");
            } else {
                tv_class.setText(studentOBJ.getStudent_class());
            }
        }

    }

    public void onBackPressed() {
        gotomain();
    }

    private void gotomain() {
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