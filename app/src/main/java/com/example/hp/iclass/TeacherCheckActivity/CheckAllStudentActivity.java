package com.example.hp.iclass.TeacherCheckActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

public class CheckAllStudentActivity extends AppCompatActivity {
    final private String subject_th_tips = "这门课已经上到第 ";
    private TextView should;
    private TextView present;
    private Button seatbtn;
    private Button infobtn;

    private Toolbar tl_head;
    private TextView Tsubject_th;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_all_student_list);
        should = (TextView) findViewById(R.id.text_should);
        present = (TextView) findViewById(R.id.text_present);
        seatbtn = (Button) findViewById(R.id.button_seat);
        tl_head = (Toolbar) findViewById(R.id.toolbar);
        infobtn = (Button) findViewById(R.id.button_student_info);

        Tsubject_th = (TextView) findViewById(R.id.text_subject_th);


    }
}
