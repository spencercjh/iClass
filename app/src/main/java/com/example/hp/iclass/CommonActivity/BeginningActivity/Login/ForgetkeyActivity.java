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

import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetStudentName;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetTeacherName;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_UpdateStudentPassword;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_UpdateTeacherPassword;
import com.example.hp.iclass.R;

public class ForgetkeyActivity extends AppCompatActivity {
    private EditText accountet;
    private EditText nameet;
    private EditText passwordet;
    private Button conbtn;
    private RadioButton choice_student;
    private RadioButton choice_teacher;
    private Toolbar tl_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetkey);
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        nameet = (EditText) findViewById(R.id.edit_name);
        accountet = (EditText) findViewById(R.id.edit_id);
        passwordet = (EditText) findViewById(R.id.edit_password);
        conbtn = (Button) findViewById(R.id.confirmBtn);
        choice_student = (RadioButton) findViewById(R.id.user_student);
        choice_teacher = (RadioButton) findViewById(R.id.user_teacher);
        tl_head.setTitle("             修改密码");
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

    public void GetPassword(View view) throws InterruptedException {
        String id = accountet.getText().toString().trim();
        String name = nameet.getText().toString().trim();
        String password = passwordet.getText().toString().trim();
        if (id.length() == 0 || name.length() == 0 || password.length() == 0) {
            new AlertDialog.Builder(this).setMessage("请不要留有空项！").setCancelable(false).
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
                    String student_name = Fun_GetStudentName.http_GetStudentName(id);
                    if (student_name.equals(name)) {
                        if (Fun_UpdateStudentPassword.http_UpdateStudentPassword(id, MD5andKL.MD5(password))) {
                            new android.support.v7.app.AlertDialog.Builder(this)
                                    .setMessage("修改成功！您可以登陆了！")
                                    .setCancelable(false)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("注意")
                                    .setPositiveButton("关闭", null)
                                    .show();
                        } else {
                            new android.support.v7.app.AlertDialog.Builder(this)
                                    .setMessage("修改失败！")
                                    .setCancelable(false)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("注意")
                                    .setPositiveButton("关闭", null)
                                    .show();
                        }
                    } else {
                        new android.support.v7.app.AlertDialog.Builder(this)
                                .setMessage("您输入的姓名与数据库中存储的不一致！请确认！")
                                .setCancelable(false)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("注意")
                                .setPositiveButton("关闭", null)
                                .show();
                    }
                } else if (choice_teacher.isChecked()) {
                    String teacher_name = Fun_GetTeacherName.http_GetTeacherName(id);
                    if (teacher_name.equals(name)) {
                        if (Fun_UpdateTeacherPassword.http_UpdateTeacherPassword(id, MD5andKL.MD5(password))) {
                            new android.support.v7.app.AlertDialog.Builder(this)
                                    .setMessage("修改成功！您可以登陆了！")
                                    .setCancelable(false)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("注意")
                                    .setPositiveButton("关闭", null)
                                    .show();
                        } else {
                            new android.support.v7.app.AlertDialog.Builder(this)
                                    .setMessage("修改失败！")
                                    .setCancelable(false)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("注意")
                                    .setPositiveButton("关闭", null)
                                    .show();
                        }
                    } else {
                        new android.support.v7.app.AlertDialog.Builder(this)
                                .setMessage("您输入的姓名与数据库中存储的不一致！请确认！")
                                .setCancelable(false)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("注意")
                                .setPositiveButton("关闭", null)
                                .show();
                    }
                }
            }
        }
    }
}
