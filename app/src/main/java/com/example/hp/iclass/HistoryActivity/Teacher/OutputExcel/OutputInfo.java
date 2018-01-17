package com.example.hp.iclass.HistoryActivity.Teacher.OutputExcel;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.iclass.HistoryActivity.Common.ChooseTimeActivity;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetAllStudent;
import com.example.hp.iclass.HttpFunction.Json.Json_AllStudentList;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.SubjectSumUpOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

@SuppressWarnings("deprecation")
public class OutputInfo extends AppCompatActivity implements View.OnClickListener {
    EditText Etotal_points;
    EditText Eeach_late_score;
    EditText Eeach_uncheckin_score;
    EditText Eeach_good_score;
    EditText Eeach_bad_score;
    Button bt_confirm;
    Button bt_recovery;
    String Input_total_points = "";
    String Input_late_score = "";
    String Input_uncheckin_score = "";
    String Input_good_score = "";
    String Input_bad_score = "";
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
        tl_head = (Toolbar) findViewById(R.id.toolbar);
        tl_head.setTitle(R.string.平时成绩导出);
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotolast();
            }
        });
        Etotal_points = (EditText) findViewById(R.id.total_points);
        Eeach_late_score = (EditText) findViewById(R.id.each_late_score);
        Eeach_uncheckin_score = (EditText) findViewById(R.id.each_uncheckin_score);
        Eeach_good_score = (EditText) findViewById(R.id.each_good_score);
        Eeach_bad_score = (EditText) findViewById(R.id.each_bad_score);
        bt_confirm = (Button) findViewById(R.id.bt_confirm);
        bt_recovery = (Button) findViewById(R.id.bt_recovery);
        bt_confirm.setOnClickListener(this);
        bt_recovery.setOnClickListener(this);
    }

    public void confirm(View view) throws InterruptedException {
        boolean flag = true;
        Input_total_points = Etotal_points.getText().toString();
        Input_late_score = Eeach_late_score.getText().toString();
        Input_uncheckin_score = Eeach_uncheckin_score.getText().toString();
        Input_good_score = Eeach_good_score.getText().toString();
        Input_bad_score = Eeach_bad_score.getText().toString();
        int total_points = 0, each_late_score = 0, each_uncheckin_score = 0, each_good_score = 0, each_bad_score = 0;
        try {
            total_points = Integer.parseInt(Input_total_points);
            each_late_score = Integer.parseInt(Input_late_score);
            each_uncheckin_score = Integer.parseInt(Input_uncheckin_score);
            each_good_score = Integer.parseInt(Input_good_score);
            each_bad_score = Integer.parseInt(Input_bad_score);
        } catch (NumberFormatException e) { //检查不合法输入
            e.printStackTrace();
            dialog1();
            flag = false;
        }
        if (total_points < 0 || each_late_score < 0 || each_uncheckin_score < 0 || each_good_score < 0 ||
                each_good_score < 0 || each_bad_score < 0) {    //检查负数
            dialog2();
            flag = false;
        }
        if (total_points == 0) {    //检查总分是否为0
            dialog3();
            flag = false;
        }
        /*if (flag) { //所有输入都正确后才能执行导出excel操作
            String filename = subjectOBJ.getSubject_name() + "_" + subjectOBJ.getSubject_id() + "_" +
                    subjectOBJ.getTeacher_name() + "平时成绩汇总表";
            final SubjectSumUpOBJ subjectSumUpOBJ = new SubjectSumUpOBJ(Json_AllStudentList.parserJson2(
                    Fun_GetAllStudent.http_GetAllStudent(subjectOBJ)), filename, subjectOBJ.getSubject_id(), 1,
                    each_uncheckin_score, each_late_score, each_good_score, each_bad_score, total_points);
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("正在导出……");
            dialog.setMessage("请稍后");
            dialog.setIcon(android.R.drawable.ic_dialog_alert);
            dialog.setButton("取消", new ProgressDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.setCancelable(true); //设置ProgressDialog 是否可以按退回按键取消
            dialog.show();  //显示
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean have_done = false;
                    final int time_outputexcel = 1000;
                    try {
                        have_done = OutputExcelFile_Fun.http_OutputExcelFile(OutputInfo.this, subjectSumUpOBJ);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // TODO Auto-generated method stub
                    int i = 0;
                    while (i < 100) {
                        try {
                            Thread.sleep(time_outputexcel);
                            dialog.incrementProgressBy(1);  // 更新进度条的进度,可以在子线程中更新进度条进度
//                         dialog1.incrementSecondaryProgressBy(10);    //二级进度条更新方式
                            i++;
                            if (have_done) {
                                dialog.setProgress(100);
                                break;
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                    dialog.dismiss();// 在进度条走完时删除Dialog
                    Intent intent = new Intent(OutputInfo.this, RecentOutput.class);
                    if (choice_user == 1) {
                        intent.putExtra("teacherOBJ", teacherOBJ);
                        intent.putExtra("user", "teacher");
                    } else if (choice_user == 0) {
                        intent.putExtra("studentOBJ", studentOBJ);
                        intent.putExtra("user", "student");
                    }
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    startActivity(intent);
                    finish();
                }
            });
            thread.start();
            thread.join();
        }*/
        if (flag) {
            String filename = "";
            final SubjectSumUpOBJ subjectSumUpOBJ = init_sum(filename, each_uncheckin_score, each_late_score, each_good_score, each_bad_score, total_points);
            final ProgressDialog dialog = new ProgressDialog(this);
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        boolean have_done = OutputExcelFile_Fun.http_OutputExcelFile(OutputInfo.this, subjectSumUpOBJ);
                        // cancel和dismiss方法本质都是一样的，都是从屏幕中删除Dialog,唯一的区别是
                        // 调用cancel方法会回调DialogInterface.OnCancelListener如果注册的话,dismiss方法不会回掉
                        if (have_done) {
                            dialog.dismiss();
                            Intent intent = new Intent(OutputInfo.this, RecentOutput.class);
                            if (choice_user == 1) {
                                intent.putExtra("teacherOBJ", teacherOBJ);
                                intent.putExtra("user", "teacher");
                            } else if (choice_user == 0) {
                                intent.putExtra("studentOBJ", studentOBJ);
                                intent.putExtra("user", "student");
                            }
                            intent.putExtra("subjectOBJ", subjectOBJ);
                            intent.putExtra("subjectsumupOBJ",subjectSumUpOBJ);
                            startActivity(intent);
                            finish();
                        }
                        // dialog.dismiss();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            dialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            dialog.setIcon(android.R.drawable.ic_dialog_alert);
            // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
            dialog.setTitle("警告");
            dialog.setMessage("导出中……");
            // dismiss监听
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub

                }
            });
            // 监听Key事件被传递给dialog
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    return false;
                }
            });
            // 监听cancel事件
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub
//                    thread.stop();
                }
            });
           /* //设置可点击的按钮，最多有三个(默认情况下)
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });*/
//            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
//                    new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // TODO Auto-generated method stub
//                            dialog.cancel();
//                        }
//                    });
/*        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });*/
            dialog.show();
            thread.start();
        }
    }

    private SubjectSumUpOBJ init_sum(String filename, int each_uncheckin_score, int each_late_score, int each_good_score, int each_bad_score, int total_points) throws InterruptedException {
        filename = subjectOBJ.getSubject_name() + "_" + subjectOBJ.getSubject_id() + "_" +
                subjectOBJ.getTeacher_name() + "平时成绩汇总表";
        return new SubjectSumUpOBJ(Json_AllStudentList.parserJson2(
                Fun_GetAllStudent.http_GetAllStudent(subjectOBJ)), filename, subjectOBJ.getSubject_id(), 1,
                each_uncheckin_score, each_late_score, each_good_score, each_bad_score, total_points);
    }

    public void recovery(View view) {
        Etotal_points.setText("30");
        Eeach_uncheckin_score.setText("2");
        Eeach_late_score.setText("2");
        Eeach_good_score.setText("2");
        Eeach_bad_score.setText("2");
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
        intent.putExtra("subjectOBJ", subjectOBJ);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_confirm) {
            try {
                confirm(view);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.bt_recovery) {
            recovery(view);
        }
    }

    public void onBackPressed() {
        gotolast();
    }

    private void dialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OutputInfo.this);
        builder.setMessage("您输入的参数不合法，请确认！");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setCancelable(true);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                }
        );
        builder.create().show();////显示对话框
    }

    private void dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OutputInfo.this);
        builder.setMessage("请不要输入负整数,请确认！");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setCancelable(true);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                    }
                }
        );
        builder.create().show();////显示对话框
    }

    private void dialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OutputInfo.this);
        builder.setMessage("总分不能为0，请确认！");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("警告");
        builder.setCancelable(true);
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
