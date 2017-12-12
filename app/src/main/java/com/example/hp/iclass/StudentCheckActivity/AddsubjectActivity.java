package com.example.hp.iclass.StudentCheckActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetSubjectProperty;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetTeacherName;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_InsertSubject;
import com.example.hp.iclass.HttpFunction.Json.Json_SubjectProperty;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import org.json.JSONException;

public class AddsubjectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button confirm;
    private EditText search;
    private int choice_user = 0;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_subject);

        Intent intent = getIntent();
        choice_user = (int) intent.getSerializableExtra("user");
        if (choice_user == 1) {
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (choice_user == 0) {
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
        confirm = (Button) findViewById(R.id.button_search);
        search = (EditText) findViewById(R.id.edit_subjectid);
        Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("            添加课程");
        tl_head.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();
                finish();
            }
        });
        if (choice_user == 1) {
            //教师添加课程 服务器操作 取消这个需求！2017年11月28日21:00:07，老师不能添加课程
          /*  try {
                if (Fun_InsertSubject.http_InsertSubject(subjectOBJ)) {
                    Toast.makeText(this, "成功添加课程", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "添加课程失败", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            dialog();
        }
        confirm.setOnClickListener(this);
    }

    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddsubjectActivity.this);
        builder.setMessage("只有学生能添加课程，请联系管理员");
        builder.setTitle("抱歉！");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                gotomain();
            }
        });

       /* builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/
        builder.create().show();////显示对话框
    }

    //在onKeyDown(int keyCode, KeyEvent event)方法中调用此方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (choice_user == 1) {
                dialog();
            } else if (choice_user == 0) {
                gotomain();
            }
        }
        return false;
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

    public void onBackPressed() {
        gotomain();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_search) {
            insert_subject();
        }
    }

    private void insert_subject() {
        try {
            subjectOBJ = Json_SubjectProperty.pareJson(Fun_GetSubjectProperty.http_GetSubjectProperty(search.getText().toString().trim()));
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        try {
            subjectOBJ.setTeacher_name(Fun_GetTeacherName.http_GetTeacherName(subjectOBJ.getTeacher_id()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if (Fun_InsertSubject.http_InsertSubject(subjectOBJ, studentOBJ)) {
                Toast.makeText(AddsubjectActivity.this, "成功添加课程", Toast.LENGTH_SHORT).show();
                gotomain();
            } else {
                Toast.makeText(AddsubjectActivity.this, "添加课程失败", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
