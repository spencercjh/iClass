package com.example.hp.iclass.PersonCenter.PersonSecurity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_StudentLogin;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_UpdateStudentPassword;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_TeacherLogin;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_UpdateTeacherPassword;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private int choice_user = 0;
    private String user = "";
    private EditText edit_oldpassword;
    private EditText edit_newpassword;
    private EditText edit_newpassword2;
    private Button btn_confirm;

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
        setContentView(R.layout.activity_reset_password);
        Intent intent = getIntent();
        user = (String) intent.getSerializableExtra("user");
        if (user.equals("teacher")) {
            choice_user = 1;
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (user.equals("student")) {
            choice_user = 0;
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
        edit_oldpassword = (EditText) findViewById(R.id.edit_oldpassword);
        edit_newpassword = (EditText) findViewById(R.id.edit_newpassword);
        edit_newpassword2 = (EditText) findViewById(R.id.edit_newpassword2);
        btn_confirm = (Button) findViewById(R.id.button_confirm);
        btn_confirm.setOnClickListener(this);
        Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("            修改密码");
        tl_head.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotolast();
            }
        });
    }

    private void gotolast() {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_confirm) {
            try {
                ChangePassword();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ChangePassword() throws InterruptedException {
        if (choice_user == 1) {
            String old_password = edit_oldpassword.getText().toString().trim();
            int result = Fun_TeacherLogin.http_LoginTeacher(teacherOBJ.getTeacher_id(), old_password);
            if (result == 1) {
                String new_password = edit_newpassword.getText().toString().trim();
                String confirm = edit_newpassword2.getText().toString().trim();
                if (!new_password.equals(confirm)) {
                    Toast.makeText(ResetPasswordActivity.this, "您两次输入的密码不正确", Toast.LENGTH_SHORT).show();
                } else {
                    if (new_password.equals(old_password)) {
                        Toast.makeText(ResetPasswordActivity.this, "新密码不能和旧密码相同！", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Fun_UpdateTeacherPassword.http_UpdateTeacherPassword(teacherOBJ.getTeacher_id(), new_password)) {
                            Toast.makeText(ResetPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessageDelayed(0, 1000);
                        }
                    }
                }
            } else if (result == 0) {
                Toast.makeText(ResetPasswordActivity.this, "原密码不正确！", Toast.LENGTH_SHORT).show();
            } else if (result == -1) {
                Toast.makeText(ResetPasswordActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }
        } else if (choice_user == 0) {
            String old_password = edit_oldpassword.getText().toString().trim();
            int result = Fun_StudentLogin.http_LoginStudent(studentOBJ.getStudent_id(), old_password);
            if (result == 1) {
                String new_password = edit_newpassword.getText().toString().trim();
                String confirm = edit_newpassword2.getText().toString().trim();
                if (!new_password.equals(confirm)) {
                    Toast.makeText(ResetPasswordActivity.this, "您两次输入的密码不正确", Toast.LENGTH_SHORT).show();
                } else {
                    if (new_password.equals(old_password)) {
                        Toast.makeText(ResetPasswordActivity.this, "新密码不能和旧密码相同！", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Fun_UpdateStudentPassword.http_UpdateStudentPassword(studentOBJ.getStudent_id(), new_password)) {
                            Toast.makeText(ResetPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessageDelayed(0, 1000);
                        }
                    }
                }
            } else if (result == 0) {
                Toast.makeText(ResetPasswordActivity.this, "原密码不正确！", Toast.LENGTH_SHORT).show();
            } else if (result == -1) {
                Toast.makeText(ResetPasswordActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBackPressed() {
        gotolast();
    }
}
