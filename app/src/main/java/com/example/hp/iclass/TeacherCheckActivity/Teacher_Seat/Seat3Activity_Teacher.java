package com.example.hp.iclass.TeacherCheckActivity.Teacher_Seat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_GetStudentName;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuarySubjectTh;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_CountCheckStudent_AllTypes;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetCheckStudent;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_UpdateStudentScore;
import com.example.hp.iclass.HttpFunction.Json.Json_CheckInfoList;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.OBJ.TeacherOBJ;
import com.example.hp.iclass.R;
import com.example.hp.iclass.TeacherCheckActivity.CheckConditionActivity;
import com.example.hp.iclass.TeacherCheckActivity.CheckStudentDetailActivity;

import java.util.ArrayList;

public class Seat3Activity_Teacher extends AppCompatActivity {
    final private int seatnum = 117;
    private GridView gridView;
    private View view;
    private MyAdapter myAdapter;
    private TeacherOBJ teacherOBJ = new TeacherOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private Toolbar tl_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_seat1_teacher);
        gridView = (GridView) findViewById(R.id.seat_graphic);
        Intent intent = getIntent();
        teacherOBJ = (TeacherOBJ) intent.getSerializableExtra("teacherOBJ");
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
        try {
            subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        try {
            tl_head.setTitle(getString(R.string.已到) + Fun_CountCheckStudent_AllTypes.http_CountCheckStudent_AllTypes(subjectOBJ) + getString(R.string.人));
        } catch (InterruptedException e) {
            e.printStackTrace();
            tl_head.setTitle(R.string.系统错误);
        }
        tl_head.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback();
            }
        });
        Teacher_FillSeatView();
    }


    private void Teacher_FillSeatView() {
        final ArrayList<CheckOBJ> seat = new ArrayList<>();
        ArrayList<CheckOBJ> check_student = new ArrayList<>();
        try {
            subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
            check_student = Json_CheckInfoList.parserJson2(Fun_GetCheckStudent.http_GetCheckStudent(subjectOBJ));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CheckOBJ hash[] = new CheckOBJ[seatnum];
        boolean exist[] = new boolean[seatnum];
        for (CheckOBJ i : check_student) {
            hash[i.getSeat_index()] = i;
            exist[i.getSeat_index()] = true;
        }
        for (int i = 0; i < seatnum; i++) {
            if (exist[i]) {
                seat.add(hash[i]);
            } else {
                seat.add(new CheckOBJ());
            }
        }
        myAdapter = new MyAdapter(seat, this);
        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                myAdapter.setSelection(arg2);
                myAdapter.notifyDataSetChanged();
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                if (seat.get(position).getStudent_id() != null) {
                    //创建弹出式菜单对象（最低版本11）
                    PopupMenu popup = new PopupMenu(Seat3Activity_Teacher.this, view);//第二个参数是绑定的那个view
                    //获取菜单填充器
                    final MenuInflater inflater = popup.getMenuInflater();
                    //填充菜单
                    inflater.inflate(R.menu.popu, popup.getMenu());
                    //绑定菜单项的点击事件
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menu_quick_add:
                                    try {
                                        Fun_UpdateStudentScore.http_UpdateStudentScore(subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th(), seat.get(position).getStudent_id(), 5);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case R.id.menu_quick_minus:
                                    try {
                                        Fun_UpdateStudentScore.http_UpdateStudentScore(subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th(), seat.get(position).getStudent_id(), 1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case R.id.menu_info:
                                    Intent intent = new Intent(Seat3Activity_Teacher.this, CheckStudentDetailActivity.class);
                                    intent.putExtra("teacherOBJ", teacherOBJ);
                                    intent.putExtra("subjectOBJ", subjectOBJ);
                                    intent.putExtra("checkOBJ", seat.get(position));
                                    startActivity(intent);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show(); //显示(这一行代码不要忘记了)
                }
                return true;
            }
        });
    }

    private void goback() {
        view = null;
        Intent it = new Intent(this, CheckConditionActivity.class);
        it.putExtra("teacherOBJ", teacherOBJ);
        it.putExtra("subjectOBJ", subjectOBJ);
        startActivity(it);
//        clear_memory();
        finish();
        System.gc();
    }

    public void onBackPressed() {
        goback();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                // 进行资源释放操作
                break;
        }
    }

    private void clear_memory() {
        setContentView(null);
        BitmapDrawable bd = (BitmapDrawable) gridView.getBackground();
        gridView.setBackgroundResource(0);
        bd.setCallback(null);
        bd.getBitmap().recycle();
        gridView = null;
    }

    private void dialog1(final int seat_index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Seat3Activity_Teacher.this);
        builder.setMessage("你确认关闭这个座位吗？");
        builder.setTitle("注意");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                /*String start_time = "";
                try {   //获取签到开始时间
                    start_time = Fun_GetStartTime.http_GetStartTime(subjectOBJ);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {   //获取课程节数
                    subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CheckOBJ insert = new CheckOBJ(subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th(),teacherOBJ.getTeacher_id(), seat_index, start_time);
                try {   //尝试签到
                    if (Fun_InsertCheckInfo.http_InsertCheckInfo(insert)) {
                        Toast.makeText(Seat1Activity_Teacher.this, "关闭座位成功！", Toast.LENGTH_SHORT).show();
                        Teacher_FillSeatView();//刷新界面
                    } else {
                        Toast.makeText(Seat1Activity_Teacher.this, "关闭座位失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
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

    private void dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Seat3Activity_Teacher.this);
        builder.setMessage("这里已经有人了！");
        builder.setTitle("抱歉！");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
            }
        });
        builder.create().show();////显示对话框
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_only_fresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            try {
                tl_head.setTitle(getString(R.string.已到) + Fun_CountCheckStudent_AllTypes.http_CountCheckStudent_AllTypes(subjectOBJ) + getString(R.string.人));
            } catch (InterruptedException e) {
                e.printStackTrace();
                tl_head.setTitle(R.string.系统错误);
            }
            Teacher_FillSeatView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyAdapter extends BaseAdapter {
        ArrayList<CheckOBJ> arrayList;
        Context context;
        int selectItem = -1;
        private CheckOBJ checkOBJ;

        private MyAdapter(ArrayList<CheckOBJ> arrayList, Context context) {
            // TODO Auto-generated constructor stub
            this.arrayList = arrayList;
            this.context = context;
        }

        private void setSelection(int position) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_seat, arg2, false);
            checkOBJ = (CheckOBJ) getItem(arg0);
            if (checkOBJ.getStudent_id() == null) {
                FrameLayout frameLayout = view.findViewById(R.id.mylayout);
                frameLayout.setBackgroundColor(Color.parseColor("#56abe4"));
            } else {
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
                if (checkOBJ.getStudent_id() == null) { //座位为空，能坐下
                    dialog1(selectItem);
                } else {  //座位有人
                    Intent intent = new Intent(Seat3Activity_Teacher.this, CheckStudentDetailActivity.class);
                    intent.putExtra("teacherOBJ", teacherOBJ);
                    intent.putExtra("subjectOBJ", subjectOBJ);
                    intent.putExtra("checkOBJ", checkOBJ);
                    startActivity(intent);
                }
            }
            return view;
        }//设置适配器或更新适配器调用
    }
}
