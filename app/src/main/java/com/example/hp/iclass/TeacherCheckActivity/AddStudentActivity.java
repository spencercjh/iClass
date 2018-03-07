package com.example.hp.iclass.TeacherCheckActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetStudentName;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_AddStudentToSubject;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetTeacherSubject;
import com.example.hp.iclass.HttpFunction.Json.Json_TeacherSubjectList;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp_subject;
    private Button bt_con;
    private EditText studentid;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private ArrayList<String> SubjectList = new ArrayList<>();
    private int subject_choose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);
        Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("请确保您的学生也使用这款App并成功登陆过！");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();
            }
        });
        sp_subject = (Spinner) findViewById(R.id.sp_subject);
        bt_con = (Button) findViewById(R.id.bt_con);
        bt_con.setOnClickListener(this);
        studentid = (EditText) findViewById(R.id.edit_id);
        Intent intent = getIntent();
        teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        try {
            SubjectList= Json_TeacherSubjectList.parserJson2(Fun_GetTeacherSubject.http_GetTeacherSubject(teacherOBJ));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SubjectList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_subject.setAdapter(adapter);
        sp_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                subject_choose=pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void gotomain() {
        Intent it = new Intent(AddStudentActivity.this, MainActivity.class);
        it.putExtra("teacherOBJ", teacherOBJ);
        it.putExtra("user", "teacher");
        it.putExtra("to_check", "true");
        startActivity(it);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_con) {
            try {
                addstudenttosubject();
            } catch (InterruptedException e) {
                e.printStackTrace();
                dialog_failed();
            }
        }
    }

    void addstudenttosubject() throws InterruptedException {
        String student_id=studentid.getText().toString().trim();
        String choose_subject_id=SubjectList.get(subject_choose);
        int index_sign=choose_subject_id.indexOf("-");
        choose_subject_id=choose_subject_id.substring(0,index_sign);
        String test_exist= Fun_GetStudentName.http_GetStudentName(student_id);
        if(test_exist.equals("failed")){
            dialog_student_not_exist();
        }else{
            if(Fun_AddStudentToSubject.http_AddStudentToSubject(choose_subject_id,student_id)){
                dialog_success();
            }else{
                dialog_failed();
            }
        }
    }

    public void onBackPressed() {
        gotomain();
    }

    void dialog_success(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddStudentActivity.this);
        builder.setMessage("添加成功！");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                }
        );
        builder.create().show();////显示对话框
    }

    void dialog_failed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddStudentActivity.this);
        builder.setMessage("添加失败！");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                }
        );
        builder.create().show();////显示对话框
    }

    void dialog_student_not_exist(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddStudentActivity.this);
        builder.setMessage("您输入的学生id并不存在！请确认他/她能正常使用本App");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                }
        );
        builder.create().show();////显示对话框
    }
}
