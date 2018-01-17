package com.example.hp.iclass.CommonActivity.BeginningActivity.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_QuaryStudentDeviceCode;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_StudentLogin;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_UpdateStudentDeviceCode;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_QuaryTeacherDeviceCode;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_TeacherLogin;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_UpdateTeacherDeviceCode;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.util.Date;

import static com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_isNetworkAvailable.isNetworkAvailable;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private long lastPressTime = 0;
    private TextView forgetkey;
    private EditText id;
    private EditText pwd;
    private CheckBox rem_pw, auto_login;
    private SharedPreferences sp;
    private RadioButton choice_student;
    private RadioButton choice_teacher;
    private Button bt_login;
    private int choice_user;
    private String exit = "";

    @SuppressLint("WorldReadableFiles")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgetkey = (TextView) findViewById(R.id.button_forget_password);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        id = (EditText) findViewById(R.id.edit_id);
        pwd = (EditText) findViewById(R.id.edit_password);
        rem_pw = (CheckBox) findViewById(R.id.remember_password);
        auto_login = (CheckBox) findViewById(R.id.auto_login);
        choice_student = (RadioButton) findViewById(R.id.user_student);
        choice_teacher = (RadioButton) findViewById(R.id.user_teacher);
        bt_login = (Button) findViewById(R.id.button_search);
        bt_login.setOnClickListener(this);
        //退出登录后回到login界面清空输入过的id和pwd
        try {
            Intent it_exit = getIntent();
            exit = (String) it_exit.getSerializableExtra("exit");
            if (exit != null) {
                id.setText("");
                pwd.setText("");
                auto_login.setChecked(false);
                sp.edit().putBoolean("AUTO_ISCHECK", false).apply();
                rem_pw.setChecked(false);
                sp.edit().putBoolean("ISCHECK", false).apply();
                choice_student.setChecked(false);
                sp.edit().putBoolean("student_user", false).apply();
                choice_teacher.setChecked(false);
                sp.edit().putBoolean("teacher_user", false).apply();
            }
        } catch (RuntimeException ignored) {
            ignored.printStackTrace();
        }
        forget_password();
        if (!isNetworkAvailable(LoginActivity.this)) {        //检测用户联网状态
            new AlertDialog.Builder(LoginActivity.this).setMessage("你是不是忘记打开数据连接了？").setCancelable(true).
                    setIcon(android.R.drawable.ic_dialog_alert).setTitle("警告").setPositiveButton("确认", null).show();
        }

        if (sp.getBoolean("teacher_user", false)) {     //设置默认是教师用户组状态
            choice_teacher.setChecked(true);
            choice_user = 1;
        } else if (sp.getBoolean("student_user", false)) {  //设置默认是学生用户组状态
            choice_student.setChecked(true);
            choice_user = 0;
        }

        if (sp.getBoolean("ISCHECK", false)) {  //设置默认是记录密码状态
            rem_pw.setChecked(true);
            id.setText(sp.getString("USER_ID", ""));
            pwd.setText(sp.getString("PASSWORD", ""));
            if (sp.getBoolean("AUTO_ISCHECK", false)) { //判断自动登陆多选框状态
                auto_login.setChecked(true);          //设置默认是自动登录状态
                if (choice_user == 1) { //教师自动登陆
                    TeacherOBJ teacherOBJ = new TeacherOBJ(id.getText().toString().trim(), pwd.getText().toString().trim());
                    int result = -1;
                    try {
                        result = Fun_TeacherLogin.http_LoginTeacher(teacherOBJ.getTeacher_id(), teacherOBJ.getTeacher_password());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (result == 1) {  //登陆成功
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);    //跳转界面
                        intent.putExtra("teacherOBJ", teacherOBJ);
                        intent.putExtra("user", "teacher");
                        startActivity(intent);
                        finish();
                    } else if (result == 0) {   //登陆失败
                        Toast.makeText(getApplicationContext(), "用户名不存在或密码错误", Toast.LENGTH_SHORT).show();
                    } else if (result == -1) {  //连接服务器失败
                        Toast.makeText(getApplicationContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                } else if (choice_user == 0) {  //学生自动登录
                    StudentOBJ studentOBJ = new StudentOBJ(id.getText().toString().trim(), pwd.getText().toString().trim());
                    int result = -1;
                    try {
                        result = Fun_StudentLogin.//网络登录请求
                                http_LoginStudent(studentOBJ.getStudent_id(), studentOBJ.getStudent_password());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (result == 1) {  //登陆成功
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);    //跳转界面
                        intent.putExtra("studentOBJ", studentOBJ);
                        intent.putExtra("user", "student");
                        startActivity(intent);
                        finish();
                    } else if (result == 0) {   //登录失败
                        Toast.makeText(getApplicationContext(), "用户名不存在或密码错误", Toast.LENGTH_SHORT).show();
                    } else if (result == -1) {  //连接服务器失败
                        Toast.makeText(getApplicationContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        //监听自动登录多选框按钮事件
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).apply();
                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).apply();
                }
            }
        });
        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).apply();
                } else {
                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).apply();
                }
            }
        });
        //监听学生用户组多选框按钮事件
        choice_student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (choice_student.isChecked()) {
                    System.out.println("学生用户组已选中");
                    sp.edit().putBoolean("student_user", true).apply();
                } else {
                    System.out.println("学生用户组没有选中");
                    sp.edit().putBoolean("student_user", false).apply();
                }
            }
        });
        //监听教师用户组多选框按钮事件
        choice_teacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (choice_teacher.isChecked()) {
                    System.out.println("教师用户组已选中");
                    sp.edit().putBoolean("teacher_user", true).apply();
                } else {
                    System.out.println("教师用户组没有选中");
                    sp.edit().putBoolean("teacher_user", false).apply();
                }
            }
        });
    }

    //忘记密码按钮
    private void forget_password() {
        String text2 = "忘记密码？";
        SpannableString spannableString2 = new SpannableString(text2);
        spannableString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetkeyActivity.class);
                startActivity(intent);
            }
        }, 0, text2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgetkey.setText(spannableString2);
        forgetkey.setMovementMethod(LinkMovementMethod.getInstance());
    }

    //登陆按钮
    public void Login(View view) throws InterruptedException {
        String str1 = id.getText().toString().trim();
        String str2 = pwd.getText().toString().trim();
        if (str1.length() == 0 || str2.length() == 0) {
            new AlertDialog.Builder(this).setMessage("帐号或密码不可为空！").setCancelable(false).
                    setIcon(android.R.drawable.ic_dialog_alert).setTitle("注意").setPositiveButton("关闭", null).show();
        } else {
            if (!choice_student.isChecked() && !choice_teacher.isChecked()) {
                new AlertDialog.Builder(this)
                        .setMessage("请选择你的用户组")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("注意")
                        .setPositiveButton("关闭", null)
                        .show();
            } else if (choice_student.isChecked()) {//学生登录
                StudentOBJ studentOBJ = new StudentOBJ(str1, str2);
                int check_device = Student_Judge_Only(str1);
                if (check_device == 1) {
                    int result = Fun_StudentLogin.//网络登录请求
                            http_LoginStudent(studentOBJ.getStudent_id(), studentOBJ.getStudent_password());
                    if (result == 1) {
                        if (rem_pw.isChecked()) {
                            //记住用户名、密码、
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("USER_ID", studentOBJ.getStudent_id());
                            editor.putString("PASSWORD", studentOBJ.getStudent_password());
                            editor.apply();
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("studentOBJ", studentOBJ);
                        intent.putExtra("user", "student");
                        LoginActivity.this.startActivity(intent);
                        finish();
                    } else if (result == 0) {
                        Toast.makeText(getApplicationContext(), "用户名不存在或密码错误", Toast.LENGTH_SHORT).show();
                    } else if (result == -1) {
                        Toast.makeText(getApplicationContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                } else if (check_device == 0) {
                    new AlertDialog.Builder(LoginActivity.this).setMessage("您的设备已登陆过别的账号或连接服务器失败，请联系你的任课老师").setCancelable(true).
                            setIcon(android.R.drawable.ic_dialog_alert).setTitle("警告").setPositiveButton("确认", null).show();
                } else if (check_device == -1) {
                    new AlertDialog.Builder(LoginActivity.this).setMessage("连接服务器失败").setCancelable(true).
                            setIcon(android.R.drawable.ic_dialog_alert).setTitle("警告").setPositiveButton("确认", null).show();
                } else if (check_device == -2) {
                    new AlertDialog.Builder(LoginActivity.this).setMessage("在这个用户组中并没有您的信息，请确认！").setCancelable(true).
                            setIcon(android.R.drawable.ic_dialog_alert).setTitle("警告").setPositiveButton("确认", null).show();
                }
            } else if (choice_teacher.isChecked()) {//教师登录
                TeacherOBJ teacherOBJ = new TeacherOBJ(str1, str2);
                int check_device = Teacher_Judge_Only(str1);
                if (check_device == 1) {
                    int result = Fun_TeacherLogin.//网络登录请求
                            http_LoginTeacher(teacherOBJ.getTeacher_id(), teacherOBJ.getTeacher_password());
                    if (result == 1) {
                        if (rem_pw.isChecked()) {
                            //记住用户名、密码、
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("USER_ID", teacherOBJ.getTeacher_id());
                            editor.putString("PASSWORD", teacherOBJ.getTeacher_password());
                            editor.apply();
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("teacherOBJ", teacherOBJ);
                        intent.putExtra("user", "teacher");
                        LoginActivity.this.startActivity(intent);
                        finish();
                    } else if (result == 0) {
                        Toast.makeText(getApplicationContext(), "用户名不存在或密码错误", Toast.LENGTH_SHORT).show();
                    } else if (result == -1) {
                        Toast.makeText(getApplicationContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                } else if (check_device == 0) {
                    new AlertDialog.Builder(LoginActivity.this).setMessage("您的设备已登陆过别的账号或连接服务器失败，请联系管理员").setCancelable(true).
                            setIcon(android.R.drawable.ic_dialog_alert).setTitle("警告").setPositiveButton("确认", null).show();
                } else if (check_device == -1) {
                    new AlertDialog.Builder(LoginActivity.this).setMessage("连接服务器失败").setCancelable(true).
                            setIcon(android.R.drawable.ic_dialog_alert).setTitle("警告").setPositiveButton("确认", null).show();
                } else if (check_device == -2) {
                    new AlertDialog.Builder(LoginActivity.this).setMessage("在这个用户组中并没有您的信息，请确认！").setCancelable(true).
                            setIcon(android.R.drawable.ic_dialog_alert).setTitle("警告").setPositiveButton("确认", null).show();
                }
            }
        }
    }

    private int Teacher_Judge_Only(String teacher_id) throws InterruptedException {
        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        String quary_device_id = Fun_QuaryTeacherDeviceCode.http_QuaryTeacherDeviceCode(teacher_id);
        if (quary_device_id.equals("failed")) {
            return -1;  //获取设备码失败，可能是连接服务器失败
        }
        if (quary_device_id.isEmpty()) {
            return -2;  //获取设备码失败，可能是在这个用户组中没有该用户
        }
        if (!ANDROID_ID.equals(quary_device_id)) {//本机设备码和服务器中存储的设备码不同
            if (quary_device_id.equals(teacher_id)) {//服务器中存储的设备码是用户id，则为第一次登陆的用户
                if (Fun_UpdateTeacherDeviceCode.http_UpdateTeacherDeviceCode(teacher_id, ANDROID_ID)) {//上传设备码
                    return 1;   //通过检测
                } else {
                    return -1;  //上传设备码失败，可能是连接服务器失败
                }
            } else {
                return 0;   //不通过检测
            }
        } else {
            return 1;   //通过检测
        }
    }

    private int Student_Judge_Only(String student_id) throws InterruptedException {
        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        String quary_device_id = Fun_QuaryStudentDeviceCode.http_QuaryStudentDeviceCode(student_id);
        if (quary_device_id.equals("failed")) {
            return -1;  //获取设备码失败，可能是连接服务器失败
        }
        if (quary_device_id.isEmpty()) {
            return -2;  //获取设备码失败，可能是在这个用户组中没有该用户
        }
        if (!ANDROID_ID.equals(quary_device_id)) {  //本机设备码和服务器中存储的设备码不同
            if (quary_device_id.equals(student_id)) {   //服务器中存储的设备码是用户id，则为第一次登陆的用户
                if (Fun_UpdateStudentDeviceCode.http_UpdateStudentDeviceCode(student_id, ANDROID_ID)) { //上传设备码
                    return 1;   //通过检测
                } else {
                    return -1;  //上传设备码失败，可能是连接服务器失败
                }
            } else {
                return 0;   //不通过检测
            }
        } else {
            return 1;   //通过检测
        }
    }

    //重写返回键
    public void onBackPressed() {
        if (new Date().getTime() - lastPressTime < 1000) {
            finish();
            Runtime.getRuntime().exit(0);//结束程序
        } else {
            lastPressTime = new Date().getTime();//重置lastPressTime
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_search) {
            try {
                Login(view);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}