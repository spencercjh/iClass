package com.example.hp.iclass.CommonActivity.BeginningActivity.Login;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_QuaryStudentPassword;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_QuaryTeacherPassword;
import com.example.hp.iclass.R;

public class ForgetkeyActivity extends AppCompatActivity {
    private EditText accountet;
    private EditText nameet;
    private Button conbtn;
    private RadioButton choice_student;
    private RadioButton choice_teacher;
    private TextView Tpassword;
    private Toolbar tl_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetkey);
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        nameet = (EditText) findViewById(R.id.edit_name);
        accountet = (EditText) findViewById(R.id.edit_id);
        conbtn = (Button) findViewById(R.id.confirmBtn);
        Tpassword = (TextView) findViewById(R.id.text_password);
        choice_student = (RadioButton) findViewById(R.id.user_student);
        choice_teacher = (RadioButton) findViewById(R.id.user_teacher);
        tl_head.setTitle("             找回密码");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotologin();
            }
        });
    }

    private void gotologin() {
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
        finish();
    }

    public void onBackPressed() {
        gotologin();
    }

    public void GetPassword(View view) {
        String str1 = accountet.getText().toString().trim();
        String str2 = nameet.getText().toString().trim();
        if (str1.length() == 0 || str2.length() == 0) {
            new AlertDialog.Builder(this).setMessage("帐号或姓名不可为空！").setCancelable(false).
                    setIcon(android.R.drawable.ic_dialog_alert).setTitle("注意").setPositiveButton("关闭", null).show();
        } else {
            if (!choice_student.isChecked() && !choice_teacher.isChecked()) {
                new android.support.v7.app.AlertDialog.Builder(this)
                        .setMessage("请选择你的用户组")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("注意")
                        .setPositiveButton("关闭", null)
                        .show();
            } else {
                if (choice_student.isChecked()) {
                    String student_password;
                    try {
                        student_password = Fun_QuaryStudentPassword.http_QuaryStudentPassword(str1.trim(), str2.trim());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        student_password = "没有找到您的用户信息！";
                    }
                    Tpassword.setText(student_password);
                } else if (choice_teacher.isChecked()) {
                    String teacher_password;
                    try {
                        teacher_password = Fun_QuaryTeacherPassword.http_QuaryTeacherPassword(str1.trim(), str2.trim());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        teacher_password = "没有找到您的用户信息！";
                    }
                    Tpassword.setText(teacher_password);
                }
            }
        }
    }
}
