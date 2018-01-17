package com.example.hp.iclass.HistoryActivity.Teacher.OutputExcel;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.iclass.HistoryActivity.Common.ChooseTimeActivity;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

public class OutputInfo extends AppCompatActivity {
    EditText score;
    EditText eachlatescore;
    EditText eachabsentscore;
    EditText eachgoodscore;
    EditText eachbadscore;
    Button bt_confirm;
    Button bt_recovery;
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = "";
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private String user = "";
    private int choice_user;
    private Toolbar tl_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_info);
        Intent intent = getIntent();
        user = (String) intent.getSerializableExtra("user");
        if (user.equals("teacher")) {
            choice_user = 1;
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (user.equals("student")) {
            choice_user = 0;
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setTitle("             找回密码");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotolast();
            }
        });
        score = (EditText) findViewById(R.id.total_points);
        eachabsentscore = (EditText) findViewById(R.id.each_uncheckin_score);
        eachbadscore = (EditText) findViewById(R.id.each_bad_score);
        eachgoodscore = (EditText) findViewById(R.id.each_good_score);
        eachlatescore = (EditText) findViewById(R.id.each_late_score);
        bt_confirm = (Button) findViewById(R.id.bt_confirm);
        bt_recovery = (Button) findViewById(R.id.bt_recovery);
    }

    public void confirm(View view) {
        str1 = score.getText().toString();
        str2 = eachlatescore.getText().toString();
        str3 = eachabsentscore.getText().toString();
        str4 = eachgoodscore.getText().toString();
        str5 = eachbadscore.getText().toString();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("正在导出……");
        dialog.setMessage("请稍后");
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setMax(100);
        dialog.setButton("取消", new ProgressDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
//设置ProgressDialog 是否可以按退回按键取消
        dialog.setCancelable(true);
//显示
        dialog.show();
//设置ProgressDialog的当前进度
        dialog.setProgress(0);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int i = 0;
                while (i < 100) {
                    try {
                        Thread.sleep(80);
                        // 更新进度条的进度,可以在子线程中更新进度条进度
                        dialog.incrementProgressBy(1);
                        // dialog.incrementSecondaryProgressBy(10)//二级进度条更新方式
                        i++;

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                // 在进度条走完时删除Dialog
                dialog.dismiss();
                Intent intent = new Intent(OutputInfo.this, RecentOutput.class);
                startActivity(intent);
                finish();

            }
        }).start();

    }

    public void recovery(View view) {
        score.setText("30");
        eachabsentscore.setText("2");
        eachlatescore.setText("2");
        eachgoodscore.setText("2");
        eachbadscore.setText("2");

    }

    private void gotolast() {
        Intent intent = new Intent(this, ChooseTimeActivity.class);
        if (choice_user == 1) {
            intent.putExtra("teacherOBJ", teacherOBJ);
            intent.putExtra("user", "teacher");
        } else if (choice_user == 0) {
            intent.putExtra("studentOBJ", studentOBJ);
            intent.putExtra("user", "student");
        }
        startActivity(intent);
        finish();
    }
}
