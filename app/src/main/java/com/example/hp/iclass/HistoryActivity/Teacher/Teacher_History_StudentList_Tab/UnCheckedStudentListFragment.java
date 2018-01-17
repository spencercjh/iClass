package com.example.hp.iclass.HistoryActivity.Teacher.Teacher_History_StudentList_Tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuaryStudentScore;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuarySubjectTh;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_GetStudentProperty;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountOneStudentCheckNum;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetAllStudent;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetCheckStudent;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_InsertCheckInfo_Teacher_Help;
import com.example.hp.iclass.HttpFunction.Json.Json_AllStudentList;
import com.example.hp.iclass.HttpFunction.Json.Json_CheckedStudentList;
import com.example.hp.iclass.HttpFunction.Json.Json_StudentProperty;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

import org.json.JSONException;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class UnCheckedStudentListFragment extends Fragment {
    private static final String TAG = "UnCheckedStudentListFragment";
    protected View mView;
    protected Context mContext;
    private ListView lv;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private SwipeRefreshLayout srl_simple;


    UnCheckedStudentListFragment() {
    }

    UnCheckedStudentListFragment(SubjectOBJ subjectOBJ, TeacherOBJ teacherOBJ) {
        this.subjectOBJ = subjectOBJ;
        this.teacherOBJ = teacherOBJ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_unchecked_students, container, false);
        lv = mView.findViewById(R.id.unchecked_student_list);
        srl_simple = mView.findViewById(R.id.srl_simple);
        srl_simple.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Teacher_FillUncheckedStudentList();
                } catch (InterruptedException | JSONException e) {
                    e.printStackTrace();
                    srl_simple.setRefreshing(false);
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
            Teacher_FillUncheckedStudentList();
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }


    private void Teacher_FillUncheckedStudentList() throws InterruptedException, JSONException {
        final ArrayList<StudentOBJ> CheckInfoList = Json_CheckedStudentList.parserJson3(Fun_GetCheckStudent.http_GetCheckStudent(subjectOBJ));
        final ArrayList<StudentOBJ> AllStudentList = Json_AllStudentList.parserJson(Fun_GetAllStudent.http_GetAllStudent(subjectOBJ));
        init_student_property(AllStudentList);
        final ArrayList<StudentOBJ> UnCheckedStudentList = GetUnCheckedStudnetList(CheckInfoList, AllStudentList);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return UnCheckedStudentList.size();
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
                    view = View.inflate(getActivity(), R.layout.item_unchecked_student, null);
                } else {
                    view = convertView;
                }
                //从subjectlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                StudentOBJ studentOBJ = UnCheckedStudentList.get(position);
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
                return view;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView Tstudent_name = view.findViewById(R.id.tv_name);
                TextView Tstudent_id = view.findViewById(R.id.tv_studentID);
                String student_name = Tstudent_name.getText().toString().trim();
                String student_id = Tstudent_id.getText().toString().trim();
                dialog1(student_name, student_id);
            }
        });
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
                                            Teacher_FillAllStudentList();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).show();
                return true;
            }
        });*/
    }

    private void dialog1(String student_name, final String student_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("你要为吗" + student_name + "签到吗？");
        builder.setTitle("注意");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                CheckOBJ checkOBJ = new CheckOBJ(subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th(), student_id, 5, 999);
                try {
                    if (Fun_InsertCheckInfo_Teacher_Help.http_InsertCheckInfo_Teacher_Help(checkOBJ)) {
                        Toast.makeText(getContext(), "代签成功！", Toast.LENGTH_SHORT).show();
                        Teacher_FillUncheckedStudentList();
                    } else {
                        Toast.makeText(getContext(), "代签失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException | JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "代签失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();////显示对话框
    }

    private ArrayList<StudentOBJ> GetUnCheckedStudnetList(ArrayList<StudentOBJ> CheckInfoList, ArrayList<StudentOBJ> AllStudentList) {
        ArrayList<StudentOBJ> UnCheckedStudentList = new ArrayList<>();
        for (StudentOBJ i : AllStudentList) {
            boolean checked = false;
            for (StudentOBJ j : CheckInfoList) {
                if (j.getStudent_id().equals(i.getStudent_id())) {
                    checked = true;
                    break;
                }
            }
            if (!checked) {
                UnCheckedStudentList.add(i);
            }
        }
        return UnCheckedStudentList;
    }

    void init_student_property(ArrayList<StudentOBJ> AllStudentList) throws InterruptedException, JSONException {
        for (int i = 0; i < AllStudentList.size(); i++) {
            AllStudentList.set(i, Json_StudentProperty.pareJson(
                    Fun_GetStudentProperty.http_GetStudentProperty(AllStudentList.get(i).getStudent_id())));
        }
    }
}

