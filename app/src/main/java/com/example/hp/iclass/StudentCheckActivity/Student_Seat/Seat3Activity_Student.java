package com.example.hp.iclass.StudentCheckActivity.Student_Seat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.iclass.CommonActivity.MainActivity;
import com.example.hp.iclass.HttpFunction.Function.Common_Function.Fun_QuarySubjectTh;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_CountStudentCheckInfoTimes;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_GetStartTime;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_InsertCheckInfo;
import com.example.hp.iclass.HttpFunction.Function.Student_Fuction.Fun_QuaryCheckSituation;
import com.example.hp.iclass.HttpFunction.Function.Teacher_Function.Fun_GetCheckStudent;
import com.example.hp.iclass.HttpFunction.Json.Json_CheckedStudentList;
import com.example.hp.iclass.OBJ.CheckOBJ;
import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;
import com.example.hp.iclass.R;

import java.util.ArrayList;

public class Seat3Activity_Student extends AppCompatActivity {

    final private int seatnum = 117;
    final private int enable_seat = (int) (seatnum * 0.85);
    private GridView gridView;
    private View view;
    private MyAdapter myAdapter;

    private StudentOBJ studentOBJ = new StudentOBJ();
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private Toolbar tl_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_seat3_student);
        Intent intent = getIntent();
        studentOBJ = (StudentOBJ) intent.getSerializableExtra("studentOBJ");
        subjectOBJ = (SubjectOBJ) intent.getSerializableExtra("subjectOBJ");
        tl_head = (Toolbar) findViewById(R.id.tl_head);
        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("  请选择座位入座");
        tl_head.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotomain();
            }
        });
        if (ischecked()) {
//            Toast.makeText(Seat3Activity_Student.this, "这节课你已经签过了", Toast.LENGTH_SHORT).show();
            Checked_Back();
        } else {
            Student_FillSeatView();
        }
    }

    private boolean isopencheck() throws InterruptedException {
        return Fun_QuaryCheckSituation.http_QuaryCheckSituation(subjectOBJ) == 1;
    }

    private boolean ischecked() {
        try {
            subjectOBJ.setSubject_th(Fun_QuarySubjectTh.http_QuarySubjectTh(subjectOBJ));
            return Fun_CountStudentCheckInfoTimes.http_CountStudentCheckInfoTimes(studentOBJ, subjectOBJ);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void Student_FillSeatView() {
        ArrayList<CheckOBJ> seat = new ArrayList<>();
        ArrayList<CheckOBJ> check_student = new ArrayList<>();
        try {
            check_student = Json_CheckedStudentList.parserJson2(Fun_GetCheckStudent.http_GetCheckStudent(subjectOBJ));
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
        gridView = (GridView) this.findViewById(R.id.seat_graphic);
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
/*        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //创建弹出式菜单对象（最低版本11）
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.popu, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.good:
                                Toast.makeText(getApplicationContext(), "好", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.bad:
                                Toast.makeText(getApplicationContext(), "差", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.info:
                                Toast.makeText(getApplicationContext(), "详细信息", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show(); //显示(这一行代码不要忘记了)
                return true;
            }
        });*/
    }

    public StudentOBJ getStudentOBJ() {
        return studentOBJ;
    }

    public SubjectOBJ getSubjectOBJ() {
        return subjectOBJ;
    }

    private void Checked_Back() {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("studentOBJ", studentOBJ);
        it.putExtra("user", "student");
        it.putExtra("checked", "checked");
        startActivity(it);
//        clear_memory();
        finish();
        System.gc();
    }

    private void gotomain() {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("studentOBJ", studentOBJ);
        it.putExtra("user", "student");
        startActivity(it);
//        clear_memory();
        finish();
        System.gc();
    }

    private void clear_memory() {
        BitmapDrawable bd = (BitmapDrawable) gridView.getBackground();
        gridView.setBackgroundResource(0);
        bd.setCallback(null);
        bd.getBitmap().recycle();
        gridView = null;
    }

    public void onBackPressed() {
        gotomain();
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

    private void dialog1(final int seat_index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Seat3Activity_Student.this);
        builder.setMessage("你要在此入座吗？");
        builder.setTitle("注意");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();//关闭对话框
                try {
                    if (isopencheck()) {
                        String start_time = "";
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
                        CheckOBJ insert = new CheckOBJ(subjectOBJ.getSubject_id(), subjectOBJ.getSubject_th(), studentOBJ.getStudent_id(), seat_index, start_time);
                        try {   //尝试签到
                            if (Fun_InsertCheckInfo.http_InsertCheckInfo(insert)) {
                                Toast.makeText(Seat3Activity_Student.this, "签到成功！", Toast.LENGTH_SHORT).show();
                                Student_FillSeatView();//刷新界面
                            } else {
                                Toast.makeText(Seat3Activity_Student.this, "签到失败！", Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(Seat3Activity_Student.this, "教师还未开始签到！", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

    private void dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Seat3Activity_Student.this);
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
            Student_FillSeatView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Seat3Activity_Student.this);
        builder.setMessage("教室的最后15%的座位不能坐！");
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
                if (checkOBJ.getStudent_id().equals(studentOBJ.getStudent_id())) {
                    TextView textView = view.findViewById(R.id.seat_name);
                    textView.setText("你");
                }
            }
            if (selectItem == arg0) {
                if (checkOBJ.getStudent_id() == null) { //座位为空，能坐下
                    if (selectItem <= enable_seat) {
                        dialog1(selectItem);
                    } else {
                        dialog3();
                    }
                } else {  //座位被占
                    dialog2();
                }
            }
            return view;
        }//设置适配器或更新适配器调用
    }
}