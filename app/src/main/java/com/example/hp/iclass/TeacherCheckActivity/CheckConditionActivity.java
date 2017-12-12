package com.example.hp.iclass.TeacherCheckActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetSubjectClassType;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuarySubjectTh;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_GetStartTime;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountCheckStudent_AllTypes;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_DeleteCheckInfo_Teacher;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_InsertCheckInfo_Teacher;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_ReUpdateSubjectTh;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_SetCheckSituationFalse;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_SetCheckSituationTrue;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_UpdateStartTime;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_UpdateSubjectTh;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;
import com.example.hp.iclass.TeacherCheckActivity.Teacher_Seat.Seat1Activity_Teacher;
import com.example.hp.iclass.TeacherCheckActivity.Teacher_Seat.Seat2Activity_Teacher;
import com.example.hp.iclass.TeacherCheckActivity.Teacher_Seat.Seat3Activity_Teacher;
import com.example.hp.iclass.TeacherCheckActivity.Teacher_Seat.SeatErrorActivity_Teacher;
import com.example.hp.iclass.TeacherCheckActivity.Teacher_Tab.StudentListActivity;

public class CheckConditionActivity extends AppCompatActivity implements View.OnClickListener {

    final private String subject_th_tips = "这门课已经上到第 ";
    private TextView should;
    private TextView present;
    private Button seatbtn;
    private Button infobtn;
    private Button button_end_check;
    private Toolbar tl_head;
    private TextView Tsubject_th;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_condition);
        should = (TextView) findViewById(R.id.text_should);
        present = (TextView) findViewById(R.id.text_present);
        seatbtn = (Button) findViewById(R.id.button_seat);
        tl_head = (Toolbar) findViewById(R.id.toolbar);
        infobtn = (Button) findViewById(R.id.button_student_list);
        button_end_check = (Button) findViewById(R.id.button_end_check);
        Tsubject_th = (TextView) findViewById(R.id.text_subject_th);
        seatbtn.setOnClickListener(this);
        infobtn.setOnClickListener(this);
        button_end_check.setOnClickListener(this);

        Intent intent = getIntent();
        teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
        try {
            subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
            Tsubject_th.setText(subject_th_tips + String.valueOf(subjectOBJ.getSubject_th()) + getString(R.string.大节));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (subjectOBJ.getCheck_situation() == 0) {
            tl_head.setTitle(" 签到未开启");
            tl_head.setTitleTextColor(Color.WHITE);
            tl_head.setNavigationIcon(R.drawable.ic_back);
            setSupportActionBar(tl_head);
            //      显示应到人数
            String Str_student_num = String.valueOf(subjectOBJ.getStudent_num());
            should.setText(Str_student_num.trim());
            present.setText(R.string.右上角打call);
            button_end_check.setEnabled(false);
            button_end_check.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_div));
            button_end_check.setText("本节课程签到已结束！");
        } else if (subjectOBJ.getCheck_situation() == 1) {
            tl_head.setTitle(" 正在签到中");
            tl_head.setTitleTextColor(Color.WHITE);
            tl_head.setNavigationIcon(R.drawable.ic_back);
            setSupportActionBar(tl_head);
            //      显示应到人数
            String Str_student_num = String.valueOf(subjectOBJ.getStudent_num());
            should.setText(Str_student_num.trim());
            //      获取当前签到人数并显示
            try {
                present.setText(Fun_CountCheckStudent_AllTypes.http_CountCheckStudent_AllTypes(subjectOBJ));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_teacher_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            try {
                Tsubject_th.setText(subject_th_tips + String.valueOf(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ)) + getString(R.string.大节));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
                present.setText(Fun_CountCheckStudent_AllTypes.http_CountCheckStudent_AllTypes(subjectOBJ));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } else if (id == R.id.menu_check) {
            try {
                StartCheck();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Tsubject_th.setText(subject_th_tips + String.valueOf(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ)) + getString(R.string.大节));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl_head.setTitle(" 正在签到中");
            button_end_check.setEnabled(true);
            button_end_check.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_div2));
            button_end_check.setText("结束本次签到");
        } else if (id == R.id.menu_reupdate) {
            tl_head.setTitle("  签到未开启");
            try {
                ReupdateCheck();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Tsubject_th.setText(subject_th_tips + String.valueOf(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ)) + getString(R.string.大节));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckConditionActivity.this);
        builder.setMessage("确认结束本次签到吗？");
        builder.setTitle("警告！");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                try {//关闭签到，刷新一下已签人数
                    Fun_SetCheckSituationFalse.http_SetCheckSituationFalse(subjectOBJ);
                    present.setText(Fun_CountCheckStudent_AllTypes.http_CountCheckStudent_AllTypes(subjectOBJ));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Tsubject_th.setText(subject_th_tips + String.valueOf(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ)) + getString(R.string.大节));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tl_head.setTitle("  签到未开启");
                button_end_check.setEnabled(false);
                button_end_check.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_div));
                button_end_check.setText("本节课程签到已结束！");
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

    private void StartCheck() throws InterruptedException {
        //      更新开始签到时间
        Fun_UpdateStartTime.http_UpdateStartTime(subjectOBJ);
        //      更新课程节数
        try {
            Fun_UpdateSubjectTh.http_UpdateSubjectTh(subjectOBJ);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //      更改签到状态，允许学生签到
        Fun_SetCheckSituationTrue.http_SetCheckSituationTrue(subjectOBJ);
        subjectOBJ.setCheck_situation(1);
        //      获取这节课的节数
        try {
            subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
        } catch (InterruptedException e) {
//          签到失败,把+1过的subject_th减回去，把签到状态改回去
            try {
                Fun_ReUpdateSubjectTh.http_ReUpdateSubjectTh(subjectOBJ);
                Fun_SetCheckSituationFalse.http_SetCheckSituationFalse(subjectOBJ);
                subjectOBJ.setCheck_situation(0);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
//      教师在all_check_info中插入一条签到信息
        CheckOBJ checkOBJ = new CheckOBJ();
        checkOBJ.setSubject_id(subjectOBJ.getSubject_id());
        checkOBJ.setSubject_th(subjectOBJ.getSubject_th());
        checkOBJ.setStudent_id(teacherOBJ.getTeacher_id());
        checkOBJ.setSeat_index(999);
        checkOBJ.setStart_time(Fun_GetStartTime.http_GetStartTime(subjectOBJ));

        Fun_InsertCheckInfo_Teacher.http_InsertCheckInfo_Teacher(checkOBJ);
//      显示应到人数
        String Str_student_num = String.valueOf(subjectOBJ.getStudent_num());
        should.setText(Str_student_num.trim());
//      获取当前签到人数并显示
        try {
            present.setText(Fun_CountCheckStudent_AllTypes.http_CountCheckStudent_AllTypes(subjectOBJ));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void ReupdateCheck() throws InterruptedException {
        subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
        if (subjectOBJ.getSubject_th() > 0) {
            Fun_ReUpdateSubjectTh.http_ReUpdateSubjectTh(subjectOBJ);
            Fun_SetCheckSituationFalse.http_SetCheckSituationFalse(subjectOBJ);
            subjectOBJ.setCheck_situation(0);
        } else if (subjectOBJ.getSubject_th() == 0) {
            Fun_SetCheckSituationFalse.http_SetCheckSituationFalse(subjectOBJ);
            subjectOBJ.setCheck_situation(0);
        }//把老师插入的签到信息删掉
        Fun_DeleteCheckInfo_Teacher.http_DeleteCheckInfo_Teacher(subjectOBJ.getSubject_id(),subjectOBJ.getSubject_th(),teacherOBJ.getTeacher_id());
        button_end_check.setEnabled(false);
        button_end_check.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_div));
        button_end_check.setText("本节课程签到已结束！");
    }

    private void StudentList() {
        Intent it = new Intent(this, StudentListActivity.class);
        it.putExtra("teacherOBJ", teacherOBJ);
        it.putExtra("subjectOBJ", subjectOBJ);
        startActivity(it);
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

    private void gotomain() {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("teacherOBJ", teacherOBJ);
        it.putExtra("user", "teacher");
        startActivity(it);
        finish();
    }

    private void endcheck() {
        dialog();
    }

    public void onBackPressed() {
        gotomain();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_end_check) {
            endcheck();
        } else if (view.getId() == R.id.button_student_list) {
            StudentList();
        } else if (view.getId() == R.id.button_seat) {
            CheckSeat();
        }
    }
}