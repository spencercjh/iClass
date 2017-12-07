package com.example.hp.iclass.CommonActivity.Fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;
import com.example.hp.iclass.TeacherCheckActivity.tab.StudentListActivity;

/**
 * Created by HP on 2017/11/21.
 * iClass
 */

public class HistoryFragment extends Fragment implements View.OnClickListener{
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private int choice_user;
    private Button test;
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
        Toolbar tl_head = view.findViewById(R.id.tl_head);
        tl_head.setTitle("                         历史信息");
        tl_head.setTitleTextColor(Color.WHITE);
        test=view.findViewById(R.id.test);
        return view;
    }
    private void Test(View view){
        Intent intent=new Intent(getActivity(), StudentListActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.test) {
            Test(view);
        }
    }
}
