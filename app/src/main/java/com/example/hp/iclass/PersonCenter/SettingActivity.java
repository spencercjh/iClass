package com.example.hp.iclass.PersonCenter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.BeginningActivity.Login.LoginActivity;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.PersonCenter.PersonInfo.ResetSexActivity;
import com.example.hp.iclass.R;

public class SettingActivity extends AppCompatActivity {
    private StudentOBJ studentOBJ = new StudentOBJ();
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private RelativeLayout re_endlogin;
    private Switch sw_message;
    private Switch sw_auto;
    private String user = "";
    private int choice_user;
    private Toolbar tl_head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        re_endlogin=(RelativeLayout)findViewById(R.id.rl_endlogin);
        sw_auto=(Switch) findViewById(R.id.sw_auto);
        sw_message=(Switch) findViewById(R.id.sw_message);
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("          设置");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotolast();
            }
        });
        sw_auto.setOnCheckedChangeListener(new  CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    Toast.makeText(getApplicationContext(), "开",
                            Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getApplicationContext(), "关",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        sw_message.setOnCheckedChangeListener(new  CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    Toast.makeText(getApplicationContext(), "开",
                            Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(getApplicationContext(), "关",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void gotologin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
        finish();
    }
    public void onBackPressed() {
        gotomain();
    }

    private void gotomain() {
        finish();
    }
    private void gotolast() {
        finish();
    }
}
