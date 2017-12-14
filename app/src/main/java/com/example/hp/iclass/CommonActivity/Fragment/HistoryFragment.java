package com.example.hp.iclass.CommonActivity.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.iclass.HistoryActivity.Common.ChooseTimeActivity;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_GetStudentSubject;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetTeacherSubject;
import com.example.hp.iclass.HttpFunction.Json.Json_StudentSubjectList;
import com.example.hp.iclass.HttpFunction.Json.Json_TeacherSubjectList;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.util.ArrayList;

/**
 * Created by HP on 2017/11/21.
 * iClass
 */

public class HistoryFragment extends Fragment {
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private int choice_user;
    private Toolbar tl_head;
    private ListView lv;
    private View myview;

    public HistoryFragment() {
    }

    public HistoryFragment(TeacherOBJ OBJ, int user) {
        teacherOBJ = OBJ;
        choice_user = user;
    }

    public HistoryFragment(StudentOBJ OBJ, int user) {
        studentOBJ = OBJ;
        choice_user = user;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        tl_head = view.findViewById(R.id.tl_head);
        tl_head.setTitle("  请选择一门课查看历史信息");
        tl_head.setTitleTextColor(Color.WHITE);
        myview = view;
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tl_head = view.findViewById(R.id.tl_head);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {    //activity活动
        super.onActivityCreated(savedInstanceState);
        if (choice_user == 1) {
            try {
                Teacher_FillSubjectList();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (choice_user == 0) {
            try {
                Student_FillSubject();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_only_fresh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //toobar的点击操作
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {  //toobar里的fresh刷新
            if (choice_user == 1) {
                try {
                    Teacher_FillSubjectList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (choice_user == 0) {
                try {
                    Student_FillSubject();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Teacher_FillSubjectList() throws InterruptedException {
        lv = myview.findViewById(R.id.lv2);
        //http://www.cnblogs.com/soada/p/5705867.html
        final ArrayList<SubjectOBJ> SubjectList = Json_TeacherSubjectList.parserJson
                (Fun_GetTeacherSubject.http_GetTeacherSubject(teacherOBJ));

        //获取ListView,并通过Adapter把studentlist的信息显示到ListView
        //为ListView设置一个适配器,getCount()返回数据个数;getView()为每一行设置一个条目
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return SubjectList.size();
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
                    view = View.inflate(getActivity(), R.layout.item_teacher_history_subject_list, null);
                } else {
                    view = convertView;
                }
                //从subjectlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                SubjectOBJ subjectOBJ = SubjectList.get(position);
                TextView Tsubject_name = view.findViewById(R.id.tv_subjectname);
                TextView Tsubject_id = view.findViewById(R.id.tv_subjectID);
                TextView Tclassroom = view.findViewById(R.id.tv_classroom);
                TextView Tstudent_num = view.findViewById(R.id.tv_mustattend);
                Tsubject_name.setText(subjectOBJ.getSubject_name().trim());
                Tsubject_id.setText(subjectOBJ.getSubject_id().trim());
                Tclassroom.setText(subjectOBJ.getClassroom().trim());
                Tstudent_num.setText(String.valueOf(subjectOBJ.getStudent_num()));
                return view;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView Tsubject_name = view.findViewById(R.id.tv_subjectname);
                TextView Tsubject_id = view.findViewById(R.id.tv_subjectID);
                TextView Tclassroom = view.findViewById(R.id.tv_classroom);
                TextView Tstudent_num = view.findViewById(R.id.tv_mustattend);
                String subject_id = Tsubject_id.getText().toString().trim();
                String subject_name = Tsubject_name.getText().toString().trim();
                String classroom = Tclassroom.getText().toString().trim();
                String str_student_num = Tstudent_num.getText().toString().trim();
                int student_num = Integer.parseInt(str_student_num);
                SubjectOBJ subjectOBJ = new SubjectOBJ(subject_id, subject_name, classroom, student_num);
                Intent intent = new Intent(getActivity(), ChooseTimeActivity.class);
                if (choice_user == 1) {
                    intent.putExtra("teacherOBJ", teacherOBJ);
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    intent.putExtra("user", "teacher");
                } else if (choice_user == 0) {
                    intent.putExtra("studentOBJ", studentOBJ);
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    intent.putExtra("user", "student");
                }
                startActivity(intent);
            }
        });
    }

    private void Student_FillSubject() throws InterruptedException {
        lv = myview.findViewById(R.id.lv2);
        final ArrayList<SubjectOBJ> SubjectList = Json_StudentSubjectList.parserJson
                (Fun_GetStudentSubject.http_GetStudentSubject(studentOBJ));

        //获取ListView,并通过Adapter把studentlist的信息显示到ListView
        //为ListView设置一个适配器,getCount()返回数据个数;getView()为每一行设置一个条目
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return SubjectList.size();
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
                    view = View.inflate(getActivity(), R.layout.item_student_history_subject_list, null);
                } else {
                    view = convertView;
                }
                //从subjectlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                SubjectOBJ subjectOBJ = SubjectList.get(position);
                TextView Tsubject_name = view.findViewById(R.id.tv_subjectname);
                TextView Tsubject_id = view.findViewById(R.id.tv_subjectID);
                TextView Tclassroom = view.findViewById(R.id.tv_classroom);
                TextView Tteacher_name = view.findViewById(R.id.tv_teacher);
                Tsubject_name.setText(subjectOBJ.getSubject_name().trim());
                Tsubject_id.setText(subjectOBJ.getSubject_id().trim());
                Tclassroom.setText(subjectOBJ.getClassroom().trim());
                Tteacher_name.setText(subjectOBJ.getTeacher_name());
                return view;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView Tsubject_name = view.findViewById(R.id.tv_subjectname);
                TextView Tsubject_id = view.findViewById(R.id.tv_subjectID);
                TextView Tclassroom = view.findViewById(R.id.tv_classroom);
                TextView Tteacher_name = view.findViewById(R.id.tv_teacher);
                String subject_name = Tsubject_name.getText().toString().trim();
                String subject_id = Tsubject_id.getText().toString().trim();
                String classroom = Tclassroom.getText().toString().trim();
                String teacher_name = Tteacher_name.getText().toString().trim();
                SubjectOBJ subjectOBJ = new SubjectOBJ(subject_id, subject_name, teacher_name, classroom);
                Intent intent = new Intent(getActivity(), ChooseTimeActivity.class);
                if (choice_user == 1) {
                    intent.putExtra("teacherOBJ", teacherOBJ);
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    intent.putExtra("user", "teacher");
                } else if (choice_user == 0) {
                    intent.putExtra("studentOBJ", studentOBJ);
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    intent.putExtra("user", "student");
                }
                startActivity(intent);
            }
        });
    }
}
