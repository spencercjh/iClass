package com.example.hp.iclass.PersonCenter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hp.iclass.PersonCenter.PersonDetailActivity;
import com.example.hp.iclass.R;

public class ResetSexActivity extends AppCompatActivity {
    private Toolbar tl_head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_sex);
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("             更改性别");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotolast();
            }
        });
    }
    private void gotolast() {
        Intent it = new Intent(this, PersonDetailActivity.class);
        startActivity(it);
        finish();

    }
}
