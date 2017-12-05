package com.example.hp.iclass.TeacherCheckActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetStudentName;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetAllStudent;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_QuaryOneSubjectTable;
import com.example.hp.iclass.HttpFunction.Json.Json_AllStudentList;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.util.ArrayList;

public class AllStudentListActivity extends AppCompatActivity {
    private ListView lv;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private StudentOBJ StudentOBJ = new StudentOBJ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_all_student_list);
        Intent intent = getIntent();
        teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");

        Toolbar tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("  本教学班全体学生信息");
        tl_head.setTitleTextColor(Color.WHITE);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback();
            }
        });
        try {
            if (!Fun_QuaryOneSubjectTable.http_QuaryOneSubjectTable(subjectOBJ)) {
                dialog1();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Teacher_FillCheckInfoList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void dialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AllStudentListActivity.this);
        builder.setMessage("数据库中没有您这节课的全部学生名单，请联系管理员");
        builder.setTitle("警告");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                finish();
            }
        });
        builder.create().show();////显示对话框
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_only_fresh, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            try {
                Teacher_FillCheckInfoList();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void Teacher_FillCheckInfoList() throws InterruptedException {
        lv = (ListView) findViewById(R.id.allstudentlist);
        final ArrayList<StudentOBJ> AllStudentList = Json_AllStudentList.parserJson(Fun_GetAllStudent.http_GetAllStudent(subjectOBJ));
        //获取ListView,并通过Adapter把studentlist的信息显示到ListView
        //为ListView设置一个适配器,getCount()返回数据个数;getView()为每一行设置一个条目
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return AllStudentList.size();
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
                    view = View.inflate(getBaseContext(), R.layout.item_all_student, null);
                } else {
                    view = convertView;
                }
                //从subjectlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                StudentOBJ studentOBJ = AllStudentList.get(position);
                TextView Tstudent_name = view.findViewById(R.id.tv_name);
                Tstudent_name.setText(studentOBJ.getStudent_name());
                TextView Tstudent_id = view.findViewById(R.id.tv_studentID);
                Tstudent_id.setText(studentOBJ.getStudent_id());
                TextView Tstudent_college = view.findViewById(R.id.tv_college);
                Tstudent_college.setText(studentOBJ.getStudent_college());
                TextView Tstudent_class = view.findViewById(R.id.tv_class);
                Tstudent_class.setText(studentOBJ.getStudent_class());
                TextView Tischeck = view.findViewById(R.id.tv_ischeck);
//                Tischeck.setText();
                TextView Tscore = view.findViewById(R.id.tv_stuscore);

                return view;
            }
        });
      /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                RelativeLayout item = (RelativeLayout) view;
                TextView Tstudent_name = view.findViewById(R.id.tv_name);
                TextView Tstudent_id = view.findViewById(R.id.tv_studentID);
                TextView Tcheck_time = view.findViewById(R.id.tv_checktime);
                TextView Tischeck = view.findViewById(R.id.tv_situation);
                String student_name = Tstudent_name.getText().toString().trim();
                String student_id = Tstudent_id.getText().toString().trim();
                String check_time = Tcheck_time.getText().toString().trim();
                String ischeck = Tischeck.getText().toString().trim();
                int ischeck_num;
                if (ischeck.equals(R.string.Ten分钟内签到)) {
                    ischeck_num = 1;
                } else if (ischeck.equals(R.string.Forty分钟内签到)) {
                    ischeck_num = 2;
                } else if (ischeck.equals(R.string.Ninety分钟内签到)) {
                    ischeck_num = 3;
                } else if (ischeck.equals(R.string.Ninety分钟后签到)) {
                    ischeck_num = 4;
                } else {
                    ischeck_num = -1;
                }
                StudentOBJ = new StudentOBJ(student_id, student_name, check_time, ischeck_num);
                Intent intent = new Intent(CheckedStudentListActivity.this, CheckedStudentDetailActivity.class);
                intent.putExtra("subjectOBJ", subjectOBJ);
                intent.putExtra("teacherOBJ", teacherOBJ);
                intent.putExtra("StudentOBJ", StudentOBJ);
                intent.putExtra("user", "teacher");
                startActivity(intent);
            }
        });*/
/*        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                RelativeLayout item = (RelativeLayout) view;
                final TextView Tsubject_id = item.findViewById(R.id.text_subject_id);
                final TextView Tsubject_name = item.findViewById(R.id.text_subject_name);
                final String subject_id = Tsubject_id.getText().toString().trim();
                final String subject_name = Tsubject_name.getText().toString().trim();
                // Use an Alert dialog to confirm delete operation
                new AlertDialog.Builder(CheckedStudentListActivity.this)
                        .setMessage("你确认要删除 " + subject_name + " 这门课程吗？")
                        .setPositiveButton("删除",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //根据subject_id从数据库中删除课程，记得补2017年11月27日23:13:52
                                        try {
                                            Teacher_FillCheckInfoList();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).show();
                return true;
            }
        });*/
    }

    private void goback() {
        finish();
    }

    public void onBackPressed() {
        finish();
    }
}
