package com.example.hp.iclass.CommonActivity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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

    private int select_tab = 0;
    private String checked = "";
    private String choice_user = "";
    private PhotoText CheckTab;
    private PhotoText PersonCenterTab;
    private PhotoText HistoryTab;
    private FragmentManager fm;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentsList;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private long lastPressTime = 0;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        fragmentsList = new ArrayList<>();
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (sp.getBoolean("first_open", true)) {
            sp.edit().putBoolean("first_open", false).apply();
            dialog();
        }
    }

    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("请前往个人中心修改自己的密码和个人信息");
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

    @Override
    protected void onStart() {
        super.onStart();    //用户组 教师为1 学生为0
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
        if (choice_user.equals("teacher")) {
            fragmentsList.add(new CheckFragment(teacherOBJ, 1));
            fragmentsList.add(new HistoryFragment(teacherOBJ, 1));
            fragmentsList.add(new PersonFragment(teacherOBJ, 1));
        } else if (choice_user.equals("student")) {
            fragmentsList.add(new CheckFragment(studentOBJ, 0));
            fragmentsList.add(new HistoryFragment(studentOBJ, 0));
            fragmentsList.add(new PersonFragment(studentOBJ, 0));
        }
        CheckTab = (PhotoText) findViewById(R.id.check_tab);
        PersonCenterTab = (PhotoText) findViewById(R.id.person_center_tab);
        HistoryTab = (PhotoText) findViewById(R.id.history_tab);
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
        CheckTab.setOnClickListener(this);
        PersonCenterTab.setOnClickListener(this);
        HistoryTab.setOnClickListener(this);
        try {
            String back_to_check = (String) intent.getSerializableExtra("to_check");
            if (back_to_check.equals("true")) {
                select_tab = 0;
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String back_to_history = (String) intent.getSerializableExtra("to_history");
            if (back_to_history.equals("true")) {
                select_tab = 1;
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String back_to_person_center = (String) intent.getSerializableExtra("to_person_center");
            if (back_to_person_center.equals("true")) {
                select_tab = 2;
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectTab(select_tab);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            dialog2();
        }
    }

    private void dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("请打开GPS定位服务！");
        builder.setTitle("警告");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//关闭对话框
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        // 设置完成后返回到原来的界面
                        startActivityForResult(intent, 0);
                    }
                }
        );
        builder.create().show();////显示对话框
    }

    /*
    传入0表示第一个tab被选择
    1表示第二个变选中,设置selected
     */
    private void selectTab(int tab) {
        switch (tab) {
            case 0:
                CheckTab.setSelected(true);
                PersonCenterTab.setSelected(false);
                HistoryTab.setSelected(false);
                break;
            case 1:
                CheckTab.setSelected(false);
                HistoryTab.setSelected(true);
                PersonCenterTab.setSelected(false);
                break;
            case 2:
                CheckTab.setSelected(false);
                HistoryTab.setSelected(false);
                PersonCenterTab.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_tab:
                viewPager.setCurrentItem(0);
                break;
            case R.id.history_tab:
                viewPager.setCurrentItem(1);
                break;
            case R.id.person_center_tab:
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