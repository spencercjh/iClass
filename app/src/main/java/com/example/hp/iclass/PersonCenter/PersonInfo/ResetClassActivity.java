package com.example.hp.iclass.PersonCenter.PersonInfo;

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

import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_UpdateStudentClass;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.R;

public class ResetClassActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar tl_head;
    private StudentOBJ studentOBJ = new StudentOBJ();
    private Button button;
    private EditText editText;
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
        setContentView(R.layout.activity_reset_class);
        Intent intent = getIntent();
        studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        editText = (EditText) findViewById(R.id.edit_id);
        button = (Button) findViewById(R.id.button_search);
        button.setOnClickListener(this);
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("             更改班级");
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

    private void gotolast() {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_search) {
            String student_class = editText.getText().toString().trim();
            if (student_class.length() > 0) {
                try {
                    if (Fun_UpdateStudentClass.http_UpdateStudentClass(studentOBJ.getStudent_id(), student_class)) {
                        Toast.makeText(ResetClassActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ResetClassActivity.this, "请输入你的班级！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBackPressed() {
        gotolast();
    }
}
