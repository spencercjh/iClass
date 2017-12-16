package com.example.hp.iclass.PersonCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.BeginningActivity.Login.LoginActivity;
import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class SettingActivity extends AppCompatActivity {
    private StudentOBJ studentOBJ = new StudentOBJ();
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private RelativeLayout re_endlogin;
    private Switch sw_message;
    private Switch sw_muted;
    private String user = "";
    private int choice_user;
    private Toolbar tl_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent();
        user = (String) intent.getSerializableExtra("user");
        if (user.equals("teacher")) {
            choice_user = 1;
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (user.equals("student")) {
            choice_user = 0;
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
        re_endlogin = (RelativeLayout) findViewById(R.id.rl_endlogin);
        sw_muted = (Switch) findViewById(R.id.sw_muted);
        sw_message = (Switch) findViewById(R.id.sw_message);
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("          设置");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();
            }
        });
        set_switch_bar();
        sw_muted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    Properties prop = loadConfig(getApplicationContext(), "/mnt/sdcard/config.properties");
                    if (prop == null) {
                        // 配置文件不存在的时候创建配置文件 初始化配置信息
                        prop = new Properties();
                        prop.put("muted", "true");
                        saveConfig(getApplicationContext(), "/mnt/sdcard/config.properties", prop);
                    } else {
                        prop.put("muted", "true");
                        saveConfig(getApplicationContext(), "/mnt/sdcard/config.properties", prop);
                    }
//                    Toast.makeText(getApplicationContext(), "设置成功 开启", Toast.LENGTH_SHORT).show();
                } else {
                    Properties prop = loadConfig(getApplicationContext(), "/mnt/sdcard/config.properties");
                    if (prop == null) {
                        // 配置文件不存在的时候创建配置文件 初始化配置信息
                        prop = new Properties();
                        prop.put("muted", "false");
                        saveConfig(getApplicationContext(), "/mnt/sdcard/config.properties", prop);
                    } else {
                        prop.put("muted", "false");
                        saveConfig(getApplicationContext(), "/mnt/sdcard/config.properties", prop);
                    }
//                    Toast.makeText(getApplicationContext(), "设置成功 关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sw_message.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    Toast.makeText(getApplicationContext(), "开",
                            Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "关",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private Properties loadConfig(Context context, String file) {
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

    private void set_switch_bar() {
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
            sw_muted.setChecked(true);
        } else if (muted.equals("false")) {
            sw_muted.setChecked(false);
        }
    }

    public void exit_login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("exit", "exit");
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        gotomain();
    }

    private void gotomain() {
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        if (choice_user == 1) {
            intent.putExtra("teacherOBJ", teacherOBJ);
            intent.putExtra("user", "teacher");
        } else if (choice_user == 0) {
            intent.putExtra("studentOBJ", studentOBJ);
            intent.putExtra("user", "student");
        }
        intent.putExtra("to_person_center", "true");
        startActivity(intent);
        finish();
    }
}
