package com.example.hp.iclass.TeacherCheckActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetSubjectClassType;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuarySubjectTh;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_GetStartTime;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_QuaryCheckSituation;
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
import com.example.hp.iclass.TeacherCheckActivity.Teacher_StudentList_Tab.StudentListActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

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
    private SharedPreferences sp;

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
            tl_head.setTitle("签到未开启");
            tl_head.setTitleTextColor(Color.WHITE);
            tl_head.setNavigationIcon(R.drawable.ic_back);
            setSupportActionBar(tl_head);
            //      显示应到人数
            String Str_student_num = String.valueOf(subjectOBJ.getStudent_num());
            should.setText(Str_student_num.trim());
            //present.setText(R.string.右上角打call);
//            button_end_check.setEnabled(true);
            // button_end_check.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_div));
            button_end_check.setText("开始签到！");
        } else if (subjectOBJ.getCheck_situation() == 1) {
            tl_head.setTitle("正在签到中");
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
//            button_end_check.setEnabled(true);
        }
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();
            }
        });
        FreshPresentStudentNum();
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (sp.getBoolean("first_in_check", true)) {
            sp.edit().putBoolean("first_in_check", false).apply();
            dialog3();
        }
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
            FreshPresentStudentNum();
            return true;
        } else if (id == R.id.menu_check) {
            try {
                int check_open = Fun_QuaryCheckSituation.http_QuaryCheckSituation(subjectOBJ);
                if (check_open == 1) {
                    dialog4();
                } else if (check_open == 0) {
                    try {
                        UpdateSubject_th();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Tsubject_th.setText(subject_th_tips + String.valueOf(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ)) + getString(R.string.大节));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    FreshPresentStudentNum();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.menu_reupdate) {
            try {
                int check_open = Fun_QuaryCheckSituation.http_QuaryCheckSituation(subjectOBJ);
                if (check_open == 1) {
                    dialog4();
                } else {
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
                    FreshPresentStudentNum();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckConditionActivity.this);
        builder.setMessage("确认结束本次签到吗？");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
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
                tl_head.setTitle("签到未开启");
//                button_end_check.setEnabled(false);
//                button_end_check.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_div));
                button_end_check.setText("开始签到！");
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

    @TargetApi(Build.VERSION_CODES.M)
    private void StartCheck() throws InterruptedException {
        //      更新开始签到时间
        Fun_UpdateStartTime.http_UpdateStartTime(subjectOBJ);
        //      更改签到状态，允许学生签到
        Fun_SetCheckSituationTrue.http_SetCheckSituationTrue(subjectOBJ);
        subjectOBJ.setCheck_situation(1);
        //      获取这节课的节数
        try {
            subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
        } catch (InterruptedException e) {
//          签到失败,把签到状态改回去
            try {
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
        if (if_set_muted()) {
            getDoNotDisturb();
            int require = Build.VERSION_CODES.M;
            int present = Integer.parseInt(Build.VERSION.SDK);
            if (present >= require) {
                set_silent_higher();
            } else if (present < require) {
                set_silen_lower();
            }
        }
    }

    private boolean if_set_muted() {
        Properties prop = loadConfig(getApplicationContext(), "/mnt/sdcard/config.properties");
        if (prop == null) {
            // 配置文件不存在的时候创建配置文件 初始化配置信息
            prop = new Properties();
            prop.put("muted", "true");
            saveConfig(getApplicationContext(), "/mnt/sdcard/config.properties", prop);
        }
        if (prop.get("muted") == null) {
            prop.put("muted", "true");
        }
        String muted = (String) prop.get("muted");
        if (muted.equals("true")) {
            return true;
        } else if (muted.equals("false")) {
            return false;
        }
        return true;
    }

    public boolean saveConfig(Context context, String file, Properties properties) {
        try {
            File fil = new File(file);
            if (!fil.exists())
                fil.createNewFile();
            FileOutputStream s = new FileOutputStream(fil);
            properties.store(s, "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Properties loadConfig(Context context, String file) {
        Properties properties = new Properties();
        try {
            FileInputStream s = new FileInputStream(file);
            properties.load(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void set_silent_higher() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int previous_notification_interrupt_setting = notificationManager.getCurrentInterruptionFilter();
        if (!notificationManager.isNotificationPolicyAccessGranted()){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            previous_notification_interrupt_setting = notificationManager.getCurrentInterruptionFilter();
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
        }
        AudioManager audio = (AudioManager) CheckConditionActivity.this.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Toast.makeText(CheckConditionActivity.this, "已为您将手机调整为静音！", Toast.LENGTH_SHORT).show();
    }

    private void set_silen_lower() {
        AudioManager audio = (AudioManager) CheckConditionActivity.this.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Toast.makeText(CheckConditionActivity.this, "已为您将手机调整为静音！", Toast.LENGTH_SHORT).show();
    }

    private void getDoNotDisturb() {
        NotificationManager notificationManager = (NotificationManager) CheckConditionActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted()) {
            dialog7();
        }
    }

    private void UpdateSubject_th() throws InterruptedException {
        //      更新开始签到时间
//        Fun_UpdateStartTime.http_UpdateStartTime(subjectOBJ);
        //      更新课程节数
        try {
            Fun_UpdateSubjectTh.http_UpdateSubjectTh(subjectOBJ);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void ReupdateCheck() throws InterruptedException {
        subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
        if (subjectOBJ.getSubject_th() > 1) {
            dialog6();
        } else if (subjectOBJ.getSubject_th() == 1) {
            dialog5();
        }
//        button_end_check.setEnabled(false);
//        button_end_check.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_div));
        button_end_check.setText("开始签到！");
    }

    private void StudentList() {
        Intent it = new Intent(this, StudentListActivity.class);
        it.putExtra("teacherOBJ", teacherOBJ);
        it.putExtra("subjectOBJ", subjectOBJ);
        startActivity(it);
        finish();
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
            finish();
        } else if (subjectOBJ.getClass_type() == 2) {
            Intent intent = new Intent(this, Seat2Activity_Teacher.class);
            intent.putExtra("subjectOBJ", subjectOBJ);
            intent.putExtra("teacherOBJ", teacherOBJ);
            startActivity(intent);
            finish();
        } else if (subjectOBJ.getClass_type() == 3) {
            Intent intent = new Intent(this, Seat3Activity_Teacher.class);
            intent.putExtra("subjectOBJ", subjectOBJ);
            intent.putExtra("teacherOBJ", teacherOBJ);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, SeatErrorActivity_Teacher.class);
            intent.putExtra("subjectOBJ", subjectOBJ);
            intent.putExtra("teacherOBJ", teacherOBJ);
            startActivity(intent);
            finish();
        }
    }

    private void gotomain() {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("teacherOBJ", teacherOBJ);
        it.putExtra("user", "teacher");
        it.putExtra("to_check", "true");
        startActivity(it);
        finish();
    }

    private void EndOrStartCheck() throws InterruptedException {
        if (Fun_QuaryCheckSituation.http_QuaryCheckSituation(subjectOBJ) == 1) {
            dialog();
        } else if (Fun_QuaryCheckSituation.http_QuaryCheckSituation(subjectOBJ) == 0) {
            dialog2();
        }
    }

    private void dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckConditionActivity.this);
        builder.setMessage("确认开始签到吗？");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                try {
                    StartCheck();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tl_head.setTitle("正在签到中");
//                button_end_check.setEnabled(false);
//                button_end_check.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_div));
                button_end_check.setText("结束本次签到");
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

    public void onBackPressed() {
        gotomain();
    }

    private void FreshPresentStudentNum() {
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
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_end_check) {
            try {
                EndOrStartCheck();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.button_student_list) {
            StudentList();
        } else if (view.getId() == R.id.button_seat) {
            CheckSeat();
        }
    }

    private void dialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckConditionActivity.this);
        builder.setMessage("点击工具栏中的上箭头和下箭头调整课程进行到的节数！");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });
        builder.create().show();//显示对话框
    }

    private void dialog4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckConditionActivity.this);
        builder.setMessage("当节课程正在签到中，无法更改课程节数！");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });
        builder.create().show();//显示对话框
    }

    private void dialog5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckConditionActivity.this);
        builder.setMessage("课程节数已经最小，您确定想把第一节课的学生签到信息都删除吗？");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                try {
                    subjectOBJ.setCheck_situation(0);
                    Fun_SetCheckSituationFalse.http_SetCheckSituationFalse(subjectOBJ);
                    Fun_DeleteCheckInfo_Teacher.http_DeleteCheckInfo_Teacher(subjectOBJ.getSubject_id(),
                            subjectOBJ.getSubject_th(), teacherOBJ.getTeacher_id());//把老师插入的签到信息删掉
                } catch (InterruptedException e) {
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

    private void dialog6() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckConditionActivity.this);
        builder.setMessage("您确定想把这节课的学生签到信息都删除吗？");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                try {
                    subjectOBJ.setCheck_situation(0);
                    Fun_ReUpdateSubjectTh.http_ReUpdateSubjectTh(subjectOBJ);
                    Fun_SetCheckSituationFalse.http_SetCheckSituationFalse(subjectOBJ);
                    Fun_DeleteCheckInfo_Teacher.http_DeleteCheckInfo_Teacher(subjectOBJ.getSubject_id(),
                            subjectOBJ.getSubject_th(), teacherOBJ.getTeacher_id());//把老师插入的签到信息删掉
                } catch (InterruptedException e) {
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

    private void dialog7() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckConditionActivity.this);
        builder.setMessage("保持课堂秩序，手机将静音，请在接下来的页面中给予权限！");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("注意");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }
        });
        builder.create().show();//显示对话框
    }
}