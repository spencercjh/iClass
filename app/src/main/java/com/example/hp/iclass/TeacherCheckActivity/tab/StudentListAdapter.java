package com.example.hp.iclass.TeacherCheckActivity.tab;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StudentListAdapter extends FragmentPagerAdapter {
	private ArrayList<String> mTitleArray;

	public StudentListAdapter(FragmentManager fm, ArrayList<String> titleArray) {
		super(fm);
		mTitleArray = titleArray;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return new CheckedStudentListFragment();
		} else if (position == 1) {
			return new UnCheckedStudentListFragment();
		}else if(position== 2){
            return new AllStudentListFragment();
        }
		return new UnCheckedStudentListFragment();
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
