package com.example.hp.iclass.StudentCheckActivity.Student_Seat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.R;

public class SeatErrorActivity_Student extends AppCompatActivity {
    private StudentOBJ studentOBJ = new StudentOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private int class_type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seaterror);
        Intent intent = getIntent();
        studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
    }

    private void gotomain() {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("studentOBJ", studentOBJ);
        it.putExtra("user", "student");
        startActivity(it);
        finish();
    }

    public void onBackPressed() {
        gotomain();
    }
}
