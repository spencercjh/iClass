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
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.BeginningActivity.Login.LoginActivity;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;

/**
 * Created by HP on 2017/11/21.
 * iClass
 */

public class PersonFragment extends Fragment {
    private Button outlogin;
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
        View view = inflater.inflate(R.layout.personfragment, container, false);
        Toolbar tl_head = view.findViewById(R.id.tl_head);
        tl_head.setTitle("                         个人中心");
        tl_head.setTitleTextColor(Color.WHITE);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        outlogin = getActivity().findViewById(R.id.button_search);
        outlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                        LoginActivity.class);
                Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_LONG).show();
                intent.putExtra("exit", "exit");
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
