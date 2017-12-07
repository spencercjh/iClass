package com.example.hp.iclass.TeacherCheckActivity.tab;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.example.hp.iclass.R;


public class StudentListActivity extends AppCompatActivity implements OnTabSelectedListener {
	private final static String TAG = "StudentListActivity";
	private Toolbar tl_head;
	private ViewPager vp_content;
	private TabLayout tab_title;
	private ArrayList<String> mTitleArray = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_layout);
		tl_head = (Toolbar) findViewById(R.id.tl_head);
		tab_title = (TabLayout) findViewById(R.id.tab_title);
		vp_content = (ViewPager) findViewById(R.id.vp_content);
		tl_head.setEnabled(false);
		tl_head.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_div));
		setSupportActionBar(tl_head);
		tl_head.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		mTitleArray.add("已签");
		mTitleArray.add("未签");
		mTitleArray.add("全体");
		initTabLayout();
		initTabViewPager();
	}
	
	private void initTabLayout() {
		tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
		tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
		tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(2)));
		tab_title.setOnTabSelectedListener(this);
	}

	private void initTabViewPager() {
		StudentListAdapter adapter = new StudentListAdapter(
				getSupportFragmentManager(), mTitleArray);
		vp_content.setAdapter(adapter);
		vp_content.addOnPageChangeListener(new SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				tab_title.getTabAt(position).select();
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab) {
	}

	@Override
	public void onTabSelected(Tab tab) {
		vp_content.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab) {
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// 显示菜单项左侧的图标
		// ActionBar的featureId是8，Toolbar的featureId是108
		if (featureId % 100 == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_overflow, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
		} else if (id == R.id.menu_refresh) {

			String format="yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Toast.makeText(this, "当前刷新时间: "+sdf.format(new Date())
					+"当前页面"+tab_title.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
			int page=tab_title.getSelectedTabPosition();
			if(page==0){
//				FRESH checked student list
			}else if(page==1){
//				Fresh Unchecked student list
			}else if(page==2){
//				Fresh All student list
			}
			return true;
		} else if (id == R.id.menu_about) {
			Toast.makeText(this, "这个是标签布局的演示demo", Toast.LENGTH_LONG).show();
//			显示这门课的一些信息
			return true;
		} else if (id == R.id.menu_quit) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
