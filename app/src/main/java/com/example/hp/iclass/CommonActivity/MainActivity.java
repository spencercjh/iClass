package com.example.hp.iclass.CommonActivity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.Fragment.CheckFragment;
import com.example.hp.iclass.CommonActivity.Fragment.HistoryFragment;
import com.example.hp.iclass.CommonActivity.Fragment.PersonFragment;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.util.ArrayList;
import java.util.Date;

import view.PhotoText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String checked = "";
    private String choice_user = "";
    private PhotoText dataTab;
    private PhotoText settingTab;
    private PhotoText userTab;
    private FragmentManager fm;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentsList;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private long lastPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        fragmentsList = new ArrayList<>();
        Intent intent = getIntent();
        choice_user = (String) intent.getSerializableExtra("user");
        if (choice_user.equals("teacher")) {
            teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        } else if (choice_user.equals("student")) {
            studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        }
        try {
            checked = (String) intent.getSerializableExtra("checked");
            if (checked != null) {
                if (checked.equals("checked")) {
                    Toast.makeText(MainActivity.this, "这节课你已经签过了", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();    //用户组 教师为1 学生为0
        if (choice_user.equals("teacher")) {
            fragmentsList.add(new CheckFragment(teacherOBJ, 1));
            fragmentsList.add(new HistoryFragment(teacherOBJ, 1));
            fragmentsList.add(new PersonFragment(teacherOBJ, 1));
        } else if (choice_user.equals("student")) {
            fragmentsList.add(new CheckFragment(studentOBJ, 0));
            fragmentsList.add(new HistoryFragment(studentOBJ, 0));
            fragmentsList.add(new PersonFragment(studentOBJ, 0));
        }

        dataTab = (PhotoText) findViewById(R.id.data_tab);
        settingTab = (PhotoText) findViewById(R.id.setting_tab);
        userTab = (PhotoText) findViewById(R.id.setting_tab1);
        viewPager = (ViewPager) findViewById(R.id.content);
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return fragmentsList.get(position);

            }

            @Override
            public int getCount() {
                return fragmentsList.size();
            }

        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                selectTab(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        dataTab.setOnClickListener(this);
        settingTab.setOnClickListener(this);
        userTab.setOnClickListener(this);
        selectTab(0);
    }

    /*
    传入0表示第一个tab被选择
    1表示第二个变选中,设置selected
     */
    private void selectTab(int tab) {
        switch (tab) {
            case 0:
                dataTab.setSelected(true);
                settingTab.setSelected(false);
                userTab.setSelected(false);
                break;
            case 1:
                dataTab.setSelected(false);
                userTab.setSelected(true);
                settingTab.setSelected(false);
                break;
            case 2:
                dataTab.setSelected(false);
                userTab.setSelected(false);
                settingTab.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.data_tab:
                viewPager.setCurrentItem(0);
                break;
            case R.id.setting_tab1:
                viewPager.setCurrentItem(1);
                break;
            case R.id.setting_tab:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    public void onBackPressed() {
        if (new Date().getTime() - lastPressTime < 1000) {
            finish();
            Runtime.getRuntime().exit(0);//结束程序
            finish();
        } else {
            lastPressTime = new Date().getTime();//重置lastPressTime
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        }
    }
}