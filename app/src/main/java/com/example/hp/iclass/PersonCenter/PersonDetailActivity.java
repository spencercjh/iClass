package com.example.hp.iclass.PersonCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;
import com.example.hp.iclass.ResetClassActivity;
import com.example.hp.iclass.ResetCollegeActivity;
import com.example.hp.iclass.ResetSexActivity;

public class PersonDetailActivity extends AppCompatActivity {
    private StudentOBJ studentOBJ = new StudentOBJ();
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private String user = "";
    private int choice_user;
    private TextView tv_name;
    private TextView tv_id;
    private  TextView tv_sex;
    private  TextView tv_college;
    private  TextView tv_class;
    private RelativeLayout re_college;
    private RelativeLayout re_class;
    private RelativeLayout re_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_id=(TextView)findViewById(R.id.tv_id);
        tv_sex=(TextView)findViewById(R.id.tv_sex);
        tv_college=(TextView)findViewById(R.id.tv_college);
        tv_class=(TextView)findViewById(R.id.tv_class);
        re_class=(RelativeLayout)findViewById(R.id.re_class);
        re_college=(RelativeLayout)findViewById(R.id.re_college);
        re_sex=(RelativeLayout)findViewById(R.id.re_sex);
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
}
