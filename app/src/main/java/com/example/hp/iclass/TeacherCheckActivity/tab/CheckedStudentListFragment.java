package com.example.hp.iclass.TeacherCheckActivity.tab;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.iclass.R;

public class CheckedStudentListFragment extends Fragment {
    private static final String TAG = "CheckedStudentListFragment";
    protected View mView;
    protected Context mContext;
    private TextView tv_simple;
    private SwipeRefreshLayout srl_simple;
    private Handler mHandler = new Handler();
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            tv_simple.setText("刷新完成");
            srl_simple.setRefreshing(false);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_checked_students, container, false);
        tv_simple = mView.findViewById(R.id.tv_goods_detail);
        srl_simple = mView.findViewById(R.id.srl_simple);
        srl_simple.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tv_simple.setText("正在刷新");
                mHandler.postDelayed(mRefresh, 2000);
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

}
