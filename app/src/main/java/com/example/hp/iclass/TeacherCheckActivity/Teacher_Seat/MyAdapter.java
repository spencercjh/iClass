package com.example.hp.iclass.TeacherCheckActivity.Teacher_Seat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetStudentName;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;
import com.example.hp.iclass.TeacherCheckActivity.CheckedStudentDetailActivity;

import java.util.ArrayList;

/**
 * Created by spencercjh on 2018/1/18.
 * iClass
 */

public class MyAdapter extends BaseAdapter {
    ArrayList<CheckOBJ> arrayList;
    Context context;
    int selectItem = -1;
    private CheckOBJ checkOBJ;
    private TeacherOBJ teacherOBJ=new TeacherOBJ();
    private SubjectOBJ subjectOBJ=new SubjectOBJ();

    MyAdapter(ArrayList<CheckOBJ> arrayList, Context context, TeacherOBJ teacherOBJ, SubjectOBJ subjectOBJ) {
        // TODO Auto-generated constructor stub
        this.arrayList = arrayList;
        this.context = context;
        this.teacherOBJ=teacherOBJ;
        this.subjectOBJ=subjectOBJ;
    }

    void setSelection(int position) {
        selectItem = position;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (null == arrayList) {
            return 0;
        } else {
            return arrayList.size();
        }
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arrayList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @SuppressLint("ViewHolder")
    @SuppressWarnings("unchecked")
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View view = LayoutInflater.from(context).inflate(R.layout.item_seat, arg2, false);
        checkOBJ = (CheckOBJ) getItem(arg0);
        if (checkOBJ.getStudent_id() == null) {
            FrameLayout frameLayout = view.findViewById(R.id.mylayout);
            frameLayout.setBackground(context.getResources().getDrawable(R.drawable.whitechair));
        } else {
            FrameLayout frameLayout = view.findViewById(R.id.mylayout);
            frameLayout.setBackground(context.getResources().getDrawable(R.drawable.bluechair));
            TextView textView = view.findViewById(R.id.seat_name);
            try {
                checkOBJ.setStudent_name(Fun_GetStudentName.http_GetStudentName(checkOBJ.getStudent_id()));
                textView.setText(checkOBJ.getStudent_name());
            } catch (InterruptedException e) {
                e.printStackTrace();
                textView.setText(checkOBJ.getStudent_id());
            }
        }
        if (selectItem == arg0) {
            if (checkOBJ.getStudent_id() != null) {  //座位有人
                Intent intent = new Intent(context, CheckedStudentDetailActivity.class);
                intent.putExtra("teacherOBJ", teacherOBJ);
                intent.putExtra("subjectOBJ", subjectOBJ);
                intent.putExtra("checkOBJ", checkOBJ);
                context.startActivity(intent);
            }
        }
        return view;
    }//设置适配器或更新适配器调用
}
