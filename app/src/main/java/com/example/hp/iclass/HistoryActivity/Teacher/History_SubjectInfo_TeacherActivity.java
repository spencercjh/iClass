package com.example.hp.iclass.HistoryActivity.Teacher;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.iclass.HistoryActivity.Common.ChooseTimeActivity;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetSubjectClassType;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountCheckStudent_Good;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountCheckStudent_Late;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;
import com.example.hp.iclass.HistoryActivity.Teacher.Teacher_History_Seat.Seat1Activity_Teacher;
import com.example.hp.iclass.HistoryActivity.Teacher.Teacher_History_Seat.Seat2Activity_Teacher;
import com.example.hp.iclass.HistoryActivity.Teacher.Teacher_History_Seat.Seat3Activity_Teacher;
import com.example.hp.iclass.HistoryActivity.Teacher.Teacher_History_Seat.SeatErrorActivity_Teacher;
import com.example.hp.iclass.HistoryActivity.Teacher.Teacher_History_StudentList_Tab.StudentListActivity;

public class History_SubjectInfo_TeacherActivity extends AppCompatActivity implements View.OnClickListener {
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private CheckOBJ checkOBJ = new CheckOBJ();
    private Toolbar tl_head;
    private TextView should;
    private TextView present;
    private TextView start_time;
    private Button check_list;
    private Button check_seat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_subjectinfo_teacher);
        tl_head = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tl_head);
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback();
            }
        });
        Intent intent = getIntent();
        teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
        checkOBJ = (CheckOBJ) intent.getSerializableExtra("checkOBJ");
        subjectOBJ.setSubject_th(checkOBJ.getSubject_th());
        tl_head.setTitle("第" + checkOBJ.getSubject_th() + "节" + subjectOBJ.getSubject_name());
        should = (TextView) findViewById(R.id.text_should);
        present = (TextView) findViewById(R.id.text_present);
        start_time = (TextView) findViewById(R.id.tv_starttime);
        should.setText(String.valueOf(subjectOBJ.getStudent_num()));
        try {
            present.setText(Fun_CountCheckStudent_Good.http_CountCheckStudent_Good(subjectOBJ)
                    + "/"
                    + Fun_CountCheckStudent_Late.http_CountCheckStudent_Late(subjectOBJ));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        start_time.setText(checkOBJ.getCheck_time());
        check_list = (Button) findViewById(R.id.button_student_info);
        check_seat = (Button) findViewById(R.id.button_seat);
        check_list.setOnClickListener(this);
        check_seat.setOnClickListener(this);
    }

    private void goback() {
        Intent intent = new Intent(History_SubjectInfo_TeacherActivity.this, ChooseTimeActivity.class);
        intent.putExtra("user", "teacher");
        intent.putExtra("teacherOBJ", teacherOBJ);
        intent.putExtra("subjectOBJ", subjectOBJ);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        goback();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_student_info) {
            Intent intent = new Intent(History_SubjectInfo_TeacherActivity.this, StudentListActivity.class);
            intent.putExtra("teacherOBJ", teacherOBJ);
            intent.putExtra("subjectOBJ", subjectOBJ);
            startActivity(intent);
        } else if (view.getId() == R.id.button_seat) {
            CheckSeat();
        }
    }

    private void CheckSeat() {
        try {
            subjectOBJ.setClass_type(Fun_GetSubjectClassType.http_GetSubjectClassType(subjectOBJ));
        } catch (InterruptedException e) {
            e.printStackTrace();
            subjectOBJ.setClass_type(-1);
        }
        if (subjectOBJ.getClass_type() == 1) {
            Intent intent = new Intent(this, Seat1Activity_Teacher.class);
            intent.putExtra("subjectOBJ", subjectOBJ);
            intent.putExtra("teacherOBJ", teacherOBJ);
            startActivity(intent);
        } else if (subjectOBJ.getClass_type() == 2) {
            Intent intent = new Intent(this, Seat2Activity_Teacher.class);
            intent.putExtra("subjectOBJ", subjectOBJ);
            intent.putExtra("teacherOBJ", teacherOBJ);
            startActivity(intent);
        } else if (subjectOBJ.getClass_type() == 3) {
            Intent intent = new Intent(this, Seat3Activity_Teacher.class);
            intent.putExtra("subjectOBJ", subjectOBJ);
            intent.putExtra("teacherOBJ", teacherOBJ);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SeatErrorActivity_Teacher.class);
            intent.putExtra("subjectOBJ", subjectOBJ);
            intent.putExtra("teacherOBJ", teacherOBJ);
            startActivity(intent);
        }
    }
}