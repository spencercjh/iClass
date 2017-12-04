package com.example.hp.iclass.CommonActivity.BeginningActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.BeginningActivity.Login.LoginActivity;
import com.example.hp.iclass.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class OpenappActivity extends AppCompatActivity implements OnClickListener {

    private long lastPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final LinearLayout tv_lin = (LinearLayout) findViewById(R.id.text_lin);//要显示的字体
        final LinearLayout tv_hide_lin = (LinearLayout) findViewById(R.id.text_hide_lin);//所谓的布
        ImageView logo = (ImageView) findViewById(R.id.image);//图片
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        logo.startAnimation(animation);//开始执行动画
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //第一个动画执行完后执行第二个动画就是那个字体显示那部分
                animation = AnimationUtils.loadAnimation(OpenappActivity.this, R.anim.text_splash_position);
                tv_lin.startAnimation(animation);
                animation = AnimationUtils.loadAnimation(OpenappActivity.this, R.anim.text_canvas);
                tv_hide_lin.startAnimation(animation);
                gotonextactivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.wholeview);
        relativeLayout.setOnClickListener(this);
    }

    private void gotonextactivity() {
        final Intent it = new Intent(this, LoginActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                OpenappActivity.this.startActivity(it);
                finish();
            }
        };
        timer.schedule(task, 900);
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.wholeview) {
            Intent intent = new Intent(OpenappActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
