package com.example.hp.iclass.HistoryActivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetHistorySubjectTimeandTh;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_QuaryTeacherID;
import com.example.hp.iclass.HttpFunction.Json.Json_HistorySubjectTimeList;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.util.ArrayList;

public class ChooseTimeActivity extends AppCompatActivity {
    private StudentOBJ studentOBJ = new StudentOBJ();
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private ListView lv;
    private int choice_user;
    private String user = "";
    private Toolbar tl_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosetime);
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);
        tl_head.setTitle("  请选择具体时间");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();
            }
        });
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
        try {
            Fill_HistorySubject();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_only_fresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            try {
                Fill_HistorySubject();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void Fill_HistorySubject() throws InterruptedException {
        lv = (ListView) findViewById(R.id.lv3);
        final ArrayList<CheckOBJ> HistorySubjectTime = Json_HistorySubjectTimeList
                .parserJson(Fun_GetHistorySubjectTimeandTh
                        .http_GetHistorySubjectTimeandTh(subjectOBJ.getSubject_id(),
                                Fun_QuaryTeacherID.http_QuaryTeacherID(subjectOBJ.getSubject_id())));
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return HistorySubjectTime.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            //ListView的每一个条目都是一个view对象
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //对ListView的优化，convertView为空时，创建一个新视图；convertView不为空时，代表它是滚出
                //屏幕，放入Recycler中的视图,若需要用到其他layout，则用inflate(),同一视图，用fiindViewBy()
                if (convertView == null) {
                    view = View.inflate(ChooseTimeActivity.this, R.layout.item_history_subject_time, null);
                } else {
                    view = convertView;
                }
                //从subjectlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                CheckOBJ checkOBJ = HistorySubjectTime.get(position);
                TextView Tcheck_time = view.findViewById(R.id.TextView_SubjectDate);
                TextView Tsubject_th = view.findViewById(R.id.TextView_SubjectTh);
                Tcheck_time.setText(checkOBJ.getCheck_time());
                Tsubject_th.setText(String.valueOf(checkOBJ.getSubject_th()));
                return view;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView Tcheck_time = view.findViewById(R.id.TextView_SubjectDate);
                TextView Tsubject_th = view.findViewById(R.id.TextView_SubjectTh);
                String check_time = Tcheck_time.getText().toString().trim();
                String str_subject_th = Tsubject_th.getText().toString().trim();
                int subject_th = Integer.parseInt(str_subject_th);
                CheckOBJ checkOBJ = new CheckOBJ(check_time, subject_th);
                Intent intent = new Intent();
                if (choice_user == 1) {
                    intent = new Intent(ChooseTimeActivity.this, History_SubjectInfo_TeacherActivity.class);
                    intent.putExtra("teacherOBJ", teacherOBJ);
                } else if (choice_user == 0) {
                    intent = new Intent(ChooseTimeActivity.this, History_SubjectInfo_StudentActivity.class);
                    intent.putExtra("studentOBJ", studentOBJ);
                }
                intent.putExtra("checkOBJ", checkOBJ);
                intent.putExtra("subjectOBJ",subjectOBJ);
                startActivity(intent);
                finish();
            }
        });
    }

    private void gotomain() {
        finish();
    }

    public void onBackPressed() {
        gotomain();
    }
}
