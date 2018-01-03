package com.example.hp.iclass.HistoryActivity.Teacher.Teacher_History_StudentList_Tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.iclass.HistoryActivity.Teacher.CheckedStudentHistoryDetailActivity;
import com.example.hp.iclass.HistoryActivity.Teacher.OutputExcel.ExcelUtil;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetStudentName;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetCheckStudent;
import com.example.hp.iclass.HttpFunction.Json.Json_CheckedStudentList;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import java.util.ArrayList;
@SuppressLint("ValidFragment")
public class CheckedStudentListFragment extends Fragment {
    private static final String TAG = "CheckedStudentListFragment";
    protected View mView;
    protected Context mContext;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private CheckOBJ checkOBJ = new CheckOBJ();
    private ListView lv;
    private SwipeRefreshLayout srl_simple;

    CheckedStudentListFragment() {
    }


    CheckedStudentListFragment(SubjectOBJ subjectOBJ, TeacherOBJ teacherOBJ) {
        this.subjectOBJ = subjectOBJ;
        this.teacherOBJ = teacherOBJ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_checked_students, container, false);
        srl_simple = mView.findViewById(R.id.srl_simple);
        srl_simple.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Teacher_FillCheckInfoList();
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
            Teacher_FillCheckInfoList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void Teacher_FillCheckInfoList() throws InterruptedException {
        lv = mView.findViewById(R.id.checked_students_list);
        final ArrayList<CheckOBJ> CheckInfoList = Json_CheckedStudentList.parserJson(Fun_GetCheckStudent.http_GetCheckStudent(subjectOBJ));
        try {
            ExcelUtil.writeExcel(getContext(),CheckInfoList,"test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取ListView,并通过Adapter把studentlist的信息显示到ListView
        //为ListView设置一个适配器,getCount()返回数据个数;getView()为每一行设置一个条目
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return CheckInfoList.size();
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
                    view = View.inflate(getActivity(), R.layout.item_checked_student, null);
                } else {
                    view = convertView;
                }
                //从subjectlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                CheckOBJ checkOBJ = CheckInfoList.get(position);
                TextView Tstudent_name = view.findViewById(R.id.tv_name);
                TextView Tstudent_id = view.findViewById(R.id.tv_studentID);
                TextView Tcheck_time = view.findViewById(R.id.tv_checktime);
                TextView Tischeck = view.findViewById(R.id.tv_situation);
                Tstudent_id.setText(checkOBJ.getStudent_id());
                try {
                    Tstudent_name.setText(Fun_GetStudentName.http_GetStudentName(checkOBJ.getStudent_id()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Tcheck_time.setText(checkOBJ.getCheck_time());
                if (checkOBJ.getIscheck() == 1) {
                    Tischeck.setText(R.string.Ten分钟内签到);
                } else if (checkOBJ.getIscheck() == 2) {
                    Tischeck.setText(R.string.Forty分钟内签到);
                } else if (checkOBJ.getIscheck() == 3) {
                    Tischeck.setText(R.string.Ninety分钟内签到);
                } else if (checkOBJ.getIscheck() == 4) {
                    Tischeck.setText(R.string.Ninety分钟后签到);
                }else if (checkOBJ.getIscheck()==5){
                    Tischeck.setText(R.string.教师代签);
                }else{
                    Tischeck.setText("系统错误");
                }
                return view;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                checkOBJ = new CheckOBJ(student_id, student_name, check_time, ischeck_num);
                Intent intent = new Intent(getActivity(), CheckedStudentHistoryDetailActivity.class);
                intent.putExtra("subjectOBJ", subjectOBJ);
                intent.putExtra("teacherOBJ", teacherOBJ);
                intent.putExtra("checkOBJ", checkOBJ);
                intent.putExtra("user", "teacher");
                startActivity(intent);
            }
        });
    }
}
