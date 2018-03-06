package com.example.hp.iclass.HistoryActivity.Student;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.hp.iclass.HistoryActivity.Common.ChooseTimeActivity;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_GetStudentCheckInfo;
import com.example.hp.iclass.HttpFunction.Json.Json_StudentCheckInfo;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.R;

import org.json.JSONException;

public class History_SubjectInfo_StudentActivity extends AppCompatActivity {
    private CheckOBJ checkOBJ = new CheckOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private Toolbar tl_head;
    private TextView ischeck;
    private TextView seat_index;
    private TextView check_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_subjectinfo_student);
        tl_head = (Toolbar) findViewById(R.id.toolbar);
        tl_head.setTitleTextColor(Color.WHITE);
        Intent intent = getIntent();
        studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
        checkOBJ = (CheckOBJ) intent.getSerializableExtra("checkOBJ");
        subjectOBJ.setSubject_th(checkOBJ.getSubject_th());
        tl_head.setTitle("第" + checkOBJ.getSubject_th() + "节" + subjectOBJ.getSubject_name());
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback();
            }
        });
        CheckOBJ student_check_info = new CheckOBJ();
        try {
            student_check_info = Json_StudentCheckInfo
                    .parserJson(Fun_GetStudentCheckInfo
                            .http_GetStudentCheckInfo(studentOBJ.getStudent_id(), subjectOBJ.getSubject_id(), checkOBJ.getSubject_th()));
        } catch (JSONException | InterruptedException e) {
            e.printStackTrace();
        }
        ischeck = (TextView) findViewById(R.id.text_ischeck);
        seat_index = (TextView) findViewById(R.id.text_seat_index);
        check_time = (TextView) findViewById(R.id.tv_check_time);
        if (student_check_info.getIscheck() == 1) {
            ischeck.setText("出勤");
        } else if (student_check_info.getIscheck() == 5) {
            ischeck.setText("教师代签");
        } else if (student_check_info.getIscheck() == -1) {
            ischeck.setText("系统错误");
        } else {
            ischeck.setText("迟到");
        }
        if (student_check_info.getSeat_index() == 999) {
            seat_index.setText("教师代签");
        } else {
            seat_index.setText(String.valueOf(student_check_info.getSeat_index()));
        }
        if (student_check_info.getCheck_time()==null) {
            check_time.setText("系统错误");
        } else {
            check_time.setText(student_check_info.getCheck_time());
        }
    }

    private void goback() {
        Intent intent = new Intent(History_SubjectInfo_StudentActivity.this, ChooseTimeActivity.class);
        intent.putExtra("user", "student");
        intent.putExtra("studentOBJ", studentOBJ);
        intent.putExtra("subjectOBJ", subjectOBJ);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        goback();
    }
}
