package com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by spencercjh on 2017/12/1.
 * iClass
 */

public class QuaryTeacherPassword extends Thread {
    private boolean flag;
    private String url;
    private String teacher_id;
    private String teacher_name;
    private String teacher_password = "";

    public QuaryTeacherPassword(String url, String teacher_id, String teacher_name) {
        // TODO Auto-generated constructor stub
        this.url = url;
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
    }

    private void doGet() throws IOException {
        String teacher_name_url = URLEncoder.encode(teacher_name, "UTF-8");
        teacher_name_url = URLEncoder.encode(teacher_name_url, "UTF-8");
        /*将username和password传给Tomcat服务器*/
        url = url + "?teacher_id=" + teacher_id
                + "&teacher_name=" + teacher_name_url;
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
                teacher_password = buffer.toString();
                teacher_password = URLDecoder.decode(teacher_password, "UTF-8");
            }
            //把服务端返回的数据打印出来
            System.out.println("result:" + teacher_password);
            if (teacher_password.equals("get teacher password failed")) {
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

    public String getTeacher_password() {
        return teacher_password;
    }

    /*在run中调用doGet*/
    @Override
    synchronized public void run() {
        try {
            doGet();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}