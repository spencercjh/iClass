package com.example.hp.iclass.TeacherCheckActivity.Teacher_Tab;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;

public class StudentListAdapter extends FragmentPagerAdapter {
	private ArrayList<String> mTitleArray;
	private TeacherOBJ teacherOBJ = new TeacherOBJ();
	private SubjectOBJ subjectOBJ = new SubjectOBJ();
	public StudentListAdapter(FragmentManager fm, ArrayList<String> titleArray,SubjectOBJ subjectOBJ,TeacherOBJ teacherOBJ) {
		super(fm);
		mTitleArray = titleArray;
		this.teacherOBJ=teacherOBJ;
		this.subjectOBJ=subjectOBJ;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return new CheckedStudentListFragment(subjectOBJ,teacherOBJ);
		} else if (position == 1) {
			return new UnCheckedStudentListFragment(subjectOBJ,teacherOBJ);
		}else if(position== 2){
            return new AllStudentListFragment(subjectOBJ,teacherOBJ);
        }
		return new CheckedStudentListFragment(subjectOBJ,teacherOBJ);
	}

	@Override
	public int getCount() {
		return mTitleArray.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitleArray.get(position);
	}
}
