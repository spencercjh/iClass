package com.example.hp.iclass.TeacherCheckActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hp.iclass.R;

public class AddStudentActivity extends AppCompatActivity {
private Spinner sp_subject;
private Button bt_con;
private EditText studentid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);
        sp_subject=(Spinner) findViewById(R.id.sp_subject);
        bt_con=(Button)findViewById(R.id.bt_con);
        studentid=(EditText)findViewById(R.id.edit_id);

    }
}
