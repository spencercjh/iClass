package com.example.hp.iclass.CommonActivity.Fragment;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.BeginningActivity.Login.LoginActivity;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.PersonSecurityActivity;
import com.example.hp.iclass.R;
import com.example.hp.iclass.SettingActivity;

/**
 * Created by HP on 2017/11/21.
 * iClass
 */

public class PersonFragment extends Fragment {
    private RelativeLayout re_setting;
    private RelativeLayout re_infosecurity;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private int choice_user;

    public PersonFragment() {
    }


    public PersonFragment(TeacherOBJ OBJ, int user) {
        teacherOBJ = OBJ;
        choice_user = user;
    }

    public PersonFragment(StudentOBJ OBJ, int user) {
        studentOBJ = OBJ;
        choice_user = user;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_center, container, false);
        Toolbar tl_head = view.findViewById(R.id.tl_head);
        tl_head.setTitle("                         个人中心");
        tl_head.setTitleTextColor(Color.WHITE);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        re_setting = getActivity().findViewById(R.id.re_setting);
        re_infosecurity=getActivity().findViewById(R.id.re_infosecurity);
        re_infosecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                        PersonSecurityActivity.class);
                //Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_LONG).show();
                //intent.putExtra("exit", "exit");
                startActivity(intent);
                getActivity().finish();
            }
        });
        re_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                        SettingActivity.class);
                //Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_LONG).show();
                //intent.putExtra("exit", "exit");
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
