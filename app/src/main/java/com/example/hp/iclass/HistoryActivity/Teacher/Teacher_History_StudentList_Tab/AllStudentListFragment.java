package com.example.hp.iclass.HistoryActivity.Teacher.Teacher_History_StudentList_Tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuaryStudentScore;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuarySubjectTh;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountOneStudentCheckNum;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetAllStudent;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetCheckStudent;
import com.example.hp.iclass.HttpFunction.Json.Json_AllStudentList;
import com.example.hp.iclass.HttpFunction.Json.Json_CheckedStudentList;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.util.ArrayList;
@SuppressLint("ValidFragment")
public class AllStudentListFragment extends Fragment {
    private static final String TAG = "UnCheckedStudentListFragment";
    protected View mView;
    protected Context mContext;
    private ListView lv;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private SwipeRefreshLayout srl_simple;

    AllStudentListFragment() {
    }

    AllStudentListFragment(SubjectOBJ subjectOBJ, TeacherOBJ teacherOBJ) {
        this.subjectOBJ = subjectOBJ;
        this.teacherOBJ = teacherOBJ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_all_students, container, false);
        srl_simple = mView.findViewById(R.id.srl_simple);
        srl_simple.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Teacher_FillAllStudentList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                srl_simple.setRefreshing(false);
            }
        });
        //旧版用下面的setColorScheme设置进度条颜色
        //srl_simple.setColorScheme(R.color.red, R.color.orange, R.color.green, R.color.blue);
        //新版用下面的setColorSchemeResources设置进度圆圈颜色
        srl_simple.setColorSchemeResources(
                R.color.red, R.color.orange, R.color.green, R.color.blue);
        //旧版v4包中无下面三个方法
//		srl_simple.setProgressBackgroundColorSchemeResource(R.color.black);
//		srl_simple.setProgressViewOffset(true, 0, 50);
//		srl_simple.setDistanceToTriggerSync(100);
        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Teacher_FillAllStudentList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void Teacher_FillAllStudentList() throws InterruptedException {
        lv = mView.findViewById(R.id.all_studnet_list);
        final ArrayList<StudentOBJ> AllStudentList = Json_AllStudentList.parserJson(Fun_GetAllStudent.http_GetAllStudent(subjectOBJ));
        final ArrayList<String> CheckInfoList = Json_CheckedStudentList.parserJson4(Fun_GetCheckStudent.http_GetCheckStudent(subjectOBJ));
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
                    view = View.inflate(getActivity(), R.layout.item_all_student, null);
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
                int all_check_num;
                try {
                    all_check_num = Fun_CountOneStudentCheckNum.http_CountOneStudentCheckNum(subjectOBJ, studentOBJ);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    all_check_num = -1;
                }
                int subject_th_num;
                try {
                    subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
                    subject_th_num = subjectOBJ.getSubject_th();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    subject_th_num = -1;
                }
                Tischeck.setText(String.valueOf(all_check_num) + "/" + String.valueOf(subject_th_num));
                TextView Tscore = view.findViewById(R.id.tv_stuscore);
                int score;
                try {
                    score = Fun_QuaryStudentScore.http_QuaryStudentScore(subjectOBJ, studentOBJ);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    score = -1;
                }
                Tscore.setText(String.valueOf(score));
                /*if (!CheckInfoList.contains(studentOBJ.getStudent_id())) { bug
                    Tstudent_name.setTextColor(Color.parseColor("#ffffff"));
                    Tstudent_id.setTextColor(Color.parseColor("#ffffff"));
                    Tstudent_college.setTextColor(Color.parseColor("#ffffff"));
                    Tstudent_class.setTextColor(Color.parseColor("#ffffff"));
                    Tischeck.setTextColor(Color.parseColor("#ffffff"));
                    Tscore.setTextColor(Color.parseColor("#ffffff"));
                    Tstudent_name.setTextColor(Color.parseColor("#ff0000"));
                    Tstudent_id.setTextColor(Color.parseColor("#ff0000"));
                    Tstudent_college.setTextColor(Color.parseColor("#ff0000"));
                    Tstudent_class.setTextColor(Color.parseColor("#ff0000"));
                    Tischeck.setTextColor(Color.parseColor("#ff0000"));
                    Tscore.setTextColor(Color.parseColor("#ff0000"));
                    RelativeLayout relativeLayout=view.findViewById(R.id.back);
                    relativeLayout.setBackgroundColor(Color.parseColor("#ff0000"));
                }*/
                return view;
            }
        });
    }
}
