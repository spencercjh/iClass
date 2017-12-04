package com.example.hp.iclass.HttpFunction.Thread.Student_Thread;

import com.example.hp.iclass.OBJ.StudentOBJ;
import com.example.hp.iclass.OBJ.SubjectOBJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by spencercjh on 2017/11/28.
 * iClass
 */

public class InsertSubject extends Thread {
    private boolean flag;
    private String url;
    private SubjectOBJ subjectOBJ = new SubjectOBJ();
    private StudentOBJ studentOBJ = new StudentOBJ();
    private String state = "";

    public InsertSubject(String url, SubjectOBJ subjectOBJ, StudentOBJ studentOBJ) {
        // TODO Auto-generated constructor stub
        this.url = url;
        this.subjectOBJ = subjectOBJ;
        this.studentOBJ = studentOBJ;
    }

    private void doGet() throws IOException {
        String subject_name = URLEncoder.encode(subjectOBJ.getSubject_name(), "UTF-8");
        subject_name = URLEncoder.encode(subject_name, "UTF-8");
        String teacher_name = URLEncoder.encode(subjectOBJ.getTeacher_name(), "UTF-8");
        teacher_name = URLEncoder.encode(teacher_name, "UTF-8");
        /*将username和password传给Tomcat服务器*/
        url = url + "?student_id=" + studentOBJ.getStudent_id()
                + "&subject_id=" + subjectOBJ.getSubject_id()
                + "&subject_name=" + subject_name
                + "&teacher_name=" + teacher_name
                + "&classroom=" + subjectOBJ.getClassroom();
        try {
//            URLEncoder.encode(URLEncoder.encode(url));
            URL httpUrl = new URL(url);
            /*获取网络连接*/
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            /*设置请求方法为GET方法*/
            conn.setRequestMethod("GET");
            /*设置访问超时时间*/
            conn.setReadTimeout(2000);
            conn.setConnectTimeout(2000);
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream inStream = conn.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }
                state = buffer.toString();
                state = URLDecoder.decode(state, "UTF-8");
            }
            //把服务端返回的数据打印出来
            System.out.println("result:" + state);
            if (state.equals("insert subject failed")) {
                setFlag(false);
            } else {
                setFlag(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean getFlag() {
        return flag;
    }

    private void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getstate() {
        return state.trim();
    }

    public void setJsonstr(String state) {
        this.state = state;
    }

    /*在run中调用doGet*/
    @Override
    public void run() {
        try {
            doGet();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}