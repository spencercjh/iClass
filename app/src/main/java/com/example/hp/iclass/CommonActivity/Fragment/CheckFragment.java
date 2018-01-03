package com.example.hp.iclass.CommonActivity.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import com.example.hp.iclass.CommonActivity.BeginningActivity.Login.LoginActivity;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetSubjectClassType;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetTeacherName;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_CreateStudentSubjectTable;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_GetStudentSubject;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetTeacherSubject;
import com.example.hp.iclass.HttpFunction.Json.Json_StudentSubjectList;
import com.example.hp.iclass.HttpFunction.Json.Json_TeacherSubjectList;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;
import com.example.hp.iclass.StudentCheckActivity.AddsubjectActivity;
import com.example.hp.iclass.StudentCheckActivity.Student_Seat.Seat1Activity_Student;
import com.example.hp.iclass.StudentCheckActivity.Student_Seat.Seat2Activity_Student;
import com.example.hp.iclass.StudentCheckActivity.Student_Seat.Seat3Activity_Student;
import com.example.hp.iclass.StudentCheckActivity.Student_Seat.SeatErrorActivity_Student;
import com.example.hp.iclass.TeacherCheckActivity.CheckConditionActivity;

import java.util.ArrayList;

/**
 * Created by HP on 2017/11/21.
 */
@SuppressLint("ValidFragment")
public class CheckFragment extends Fragment {
    private ListView lv;
    private View myview;
    private Toolbar tl_head;
    private TeacherOBJ teacherOBJ;
    private StudentOBJ studentOBJ;
    private int choice_user=-1;

    public CheckFragment(TeacherOBJ OBJ, int user) {
        teacherOBJ = OBJ;
        choice_user = user;
    }

    public CheckFragment(StudentOBJ OBJ, int user) {
        studentOBJ = OBJ;
        choice_user = user;
    }

    public CheckFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_check, container, false);
        if (choice_user == 1) {
            view = inflater.inflate(R.layout.fragment_teacher_check, container, false);
        } else if (choice_user == 0) {
            view = inflater.inflate(R.layout.fragment_student_check, container, false);
        }
        tl_head = view.findViewById(R.id.tl_head);
        ((AppCompatActivity) getActivity()).setSupportActionBar(tl_head);

        tl_head.setTitle("       请选择一门课开始签到");
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
                teacherOBJ.setTeacher_name(Fun_GetTeacherName.http_GetTeacherName(teacherOBJ));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Teacher_FillSubjectList();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (choice_user == 0) {
            try {
                Fun_CreateStudentSubjectTable.http_CreateStudentSubjectTable(studentOBJ);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Student_FillSubject();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_has_menu, menu);

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
        } else if (id == R.id.addsubject) {
            gotoaddcourse();
            return true;
        } else if (id == R.id.end) {
            exit_login();
        }
        return super.onOptionsItemSelected(item);
    }

    private void exit_login() {  //toobar menu里的退出登录
        Intent it = new Intent(getActivity(), LoginActivity.class);
        it.putExtra("exit", "exit");
        startActivity(it);
        getActivity().finish();
    }

    private void gotoaddcourse() {   //toobar menu里的添加课程
        Intent it = new Intent(getActivity(), AddsubjectActivity.class);
        it.putExtra("user", choice_user);
        if (choice_user == 1) {
            it.putExtra("teacherOBJ", teacherOBJ);
        } else if (choice_user == 0) {
            it.putExtra("studentOBJ", studentOBJ);
        }
        startActivity(it);
        getActivity().finish();
    }

    private void Teacher_FillSubjectList() throws InterruptedException {
        lv = myview.findViewById(R.id.lv);
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
                    view = View.inflate(getActivity(), R.layout.item_teacher_subject, null);
                } else {
                    view = convertView;
                }
                //从subjectlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                SubjectOBJ subjectOBJ = SubjectList.get(position);
                TextView Tsubject_name = view.findViewById(R.id.text_subject_name);
                TextView Tsubject_id = view.findViewById(R.id.text_subject_id);
                TextView Tclassroom = view.findViewById(R.id.text_classroom);
                TextView Tstudent_num = view.findViewById(R.id.text_student_num);
//                TextView Tteacher_name = view.findViewById(R.id.text_teacher_name);
                TextView Tcheck_situation = view.findViewById(R.id.text_check_situation);
                Tsubject_name.setText(subjectOBJ.getSubject_name().trim());
                Tsubject_id.setText(subjectOBJ.getSubject_id().trim());
                Tclassroom.setText(subjectOBJ.getClassroom().trim());
                Tstudent_num.setText(String.valueOf(subjectOBJ.getStudent_num()));
//                String teacher_name = teacherOBJ.getTeacher_name().trim();
//                Tteacher_name.setText(teacher_name);
                if (subjectOBJ.getCheck_situation() == 1) {
                    Tcheck_situation.setText("允许签到");
                } else if (subjectOBJ.getCheck_situation() == 0) {
                    Tcheck_situation.setText("关闭签到");
                }
                return view;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView Tsubject_name = view.findViewById(R.id.text_subject_name);
                TextView Tsubject_id = view.findViewById(R.id.text_subject_id);
                TextView Tclassroom = view.findViewById(R.id.text_classroom);
                TextView Tstudent_num = view.findViewById(R.id.text_student_num);
                TextView Tcheck_situation = view.findViewById(R.id.text_check_situation);
                String subject_name = Tsubject_name.getText().toString().trim();
                String subject_id = Tsubject_id.getText().toString().trim();
                String classroom = Tclassroom.getText().toString().trim();
                String Str_student_num = Tstudent_num.getText().toString().trim();
                String Str_check_situation = Tcheck_situation.getText().toString().trim();
                int student_num, check_situation = -1;
                try {
                    student_num = Integer.parseInt(Str_student_num);
                } catch (NumberFormatException e) {
                    student_num = -1;
                }
                if (Str_check_situation.contains("允许签到")) {
                    check_situation = 1;
                } else if (Str_check_situation.contains("关闭签到")) {
                    check_situation = 0;
                }
                SubjectOBJ subjectOBJ = new SubjectOBJ(subject_id, subject_name, teacherOBJ.getTeacher_id()
                        , student_num, classroom, check_situation);
                Intent intent = new Intent(getActivity(), CheckConditionActivity.class);
                intent.putExtra("subjectOBJ", subjectOBJ);
                intent.putExtra("teacherOBJ", teacherOBJ);
                intent.putExtra("user", "teacher");
                startActivity(intent);
                getActivity().finish();
            }
        });
      /*  lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                RelativeLayout item = (RelativeLayout) view;
                final TextView Tsubject_id = item.findViewById(R.id.text_subject_id);
                final TextView Tsubject_name = item.findViewById(R.id.text_subject_name);
                final String subject_id = Tsubject_id.getText().toString().trim();
                final String subject_name = Tsubject_name.getText().toString().trim();
                // Use an Alert dialog to confirm delete operation
                new AlertDialog.Builder(getActivity())
                        .setMessage("你确认要删除 " + subject_name + " 这门课程吗？")
                        .setPositiveButton("删除",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //根据subject_id从数据库中删除课程，记得补2017年11月27日23:13:52
                                        try {
                                            Teacher_FillSubjectList();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).show();
                return true;
            }
        });*/
    }

    private void Student_FillSubject() throws InterruptedException {
        lv = myview.findViewById(R.id.lv);
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
                    view = View.inflate(getActivity(), R.layout.item_student_subject, null);
                } else {
                    view = convertView;
                }
                //从subjectlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                SubjectOBJ subjectOBJ = SubjectList.get(position);
                TextView Tsubject_name = view.findViewById(R.id.text_subject_name);
                TextView Tsubject_id = view.findViewById(R.id.text_subject_id);
                TextView Tclassroom = view.findViewById(R.id.text_classroom);
                TextView Tteacher_name = view.findViewById(R.id.text_teacher_name);
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
                TextView Tsubject_name = view.findViewById(R.id.text_subject_name);
                TextView Tsubject_id = view.findViewById(R.id.text_subject_id);
                TextView Tclassroom = view.findViewById(R.id.text_classroom);
                TextView Tteacher_name = view.findViewById(R.id.text_teacher_name);
                String subject_name = Tsubject_name.getText().toString().trim();
                String subject_id = Tsubject_id.getText().toString().trim();
                String classroom = Tclassroom.getText().toString().trim();
                String teacher_name = Tteacher_name.getText().toString().trim();
                SubjectOBJ subjectOBJ = new SubjectOBJ(subject_id, subject_name, teacher_name, classroom);
                try {
                    subjectOBJ.setClass_type(Fun_GetSubjectClassType.http_GetSubjectClassType(subjectOBJ));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    subjectOBJ.setClass_type(-1);
                }
                if (subjectOBJ.getClass_type() == 1) {
                    Intent intent = new Intent(getActivity(), Seat1Activity_Student.class);
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    intent.putExtra("studentOBJ", studentOBJ);
                    startActivity(intent);
                    getActivity().finish();
                } else if (subjectOBJ.getClass_type() == 2) {
                    Intent intent = new Intent(getActivity(), Seat2Activity_Student.class);
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    intent.putExtra("studentOBJ", studentOBJ);
                    startActivity(intent);
                    getActivity().finish();
                } else if (subjectOBJ.getClass_type() == 3) {
                    Intent intent = new Intent(getActivity(), Seat3Activity_Student.class);
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    intent.putExtra("studentOBJ", studentOBJ);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Intent intent = new Intent(getActivity(), SeatErrorActivity_Student.class);
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    intent.putExtra("studentOBJ", studentOBJ);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
      /*  lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                RelativeLayout item = (RelativeLayout) view;
                final TextView Tsubject_id = item.findViewById(R.id.text_subject_id);
                final TextView Tsubject_name = item.findViewById(R.id.text_subject_name);
                final String subject_id = Tsubject_id.getText().toString().trim();
                final String subject_name = Tsubject_name.getText().toString().trim();
                // Use an Alert dialog to confirm delete operation
                new AlertDialog.Builder(getActivity())
                        .setMessage("你确认要删除 " + subject_name + " 这门课程吗？")
                        .setPositiveButton("删除",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //根据subject_id从数据库中删除课程，记得补2017年11月27日23:13:52
                                        try {
                                            Teacher_FillSubjectList();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).show();
                return true;
            }
        });*/
    }
}
