package com.example.hp.iclass;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;

public class ResetPasswordActivity extends AppCompatActivity {
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private int choice_user = 0;
    private EditText edit_oldpassword;
    private EditText edit_newpassword;
    private EditText edit_newpassword2;
    private Button btn_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        edit_oldpassword=(EditText)findViewById(R.id.edit_oldpassword);
        edit_newpassword=(EditText)findViewById(R.id.edit_newpassword);
        edit_newpassword2=(EditText)findViewById(R.id.edit_newpassword2);
        btn_confirm=(Button) findViewById(R.id.button_confirm);
        Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("            信息安全");
        tl_head.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();

            }
        });
    }
    private void gotomain() {
        Intent it = new Intent(this, MainActivity.class);
        if (choice_user == 1) {
            it.putExtra("user", "teacher");
            it.putExtra("teacherOBJ", teacherOBJ);
        } else if (choice_user == 0) {
            it.putExtra("user", "student");
            it.putExtra("studentOBJ", studentOBJ);
        }
        startActivity(it);
        finish();
    }
}
