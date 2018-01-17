package com.example.hp.iclass.HistoryActivity.Teacher.OutputExcel;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.SubjectSumUpOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.io.File;

public class RecentOutput extends AppCompatActivity implements View.OnClickListener {
    Button bt_open;
    Button bt_open1;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private SubjectSumUpOBJ subjectSumUpOBJ = new SubjectSumUpOBJ();
    private String user = "";
    private int choice_user;
    private Toolbar tl_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_output);
        bt_open = (Button) findViewById(R.id.bt_open);
        bt_open1 = (Button) findViewById(R.id.bt_open1);
        bt_open.setOnClickListener(this);
        bt_open1.setOnClickListener(this);
        Intent intent = getIntent();
        if (user.equals("teacher")) {
            choice_user = 1;
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (user.equals("student")) {
            choice_user = 0;
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
        subjectSumUpOBJ = (SubjectSumUpOBJ) intent.getSerializableExtra("subjectsumupOBJ");
        user = (String) intent.getSerializableExtra("user");
        tl_head = (Toolbar) findViewById(R.id.toolbar);
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

    public void directly_open(View view) {
//        Toast.makeText(getApplicationContext(), "直接打开", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File dir = new File(this.getExternalFilesDir(null).getPath());
        File file = new File(dir, subjectSumUpOBJ.getFilename() + ".xls");
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        startActivity(intent);
    }

    public void indirectly_open(View view) {
//        Toast.makeText(getApplicationContext(), "资源管理器打开", Toast.LENGTH_SHORT).show();
        dialog();
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
        intent.putExtra("subjectOBJ", subjectOBJ);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_open) {
            directly_open(view);
        } else if (view.getId() == R.id.bt_open1) {
            indirectly_open(view);
        }
    }

    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RecentOutput.this);
        builder.setMessage("文件在.../Android/data/com.example.hp.iclass/files中");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                File dir = new File(RecentOutput.this.getExternalFilesDir(null).getPath());
                File file = new File(dir, subjectSumUpOBJ.getFilename() + ".xls");
                intent.setDataAndType(Uri.fromFile(file), "file/*");
                try {
                    startActivity(intent);
                    startActivity(Intent.createChooser(intent, "选择浏览工具"));
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();//显示对话框
    }
}
