package com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by spencercjh on 2017/12/14.
 * iClass
 */

public class CountCheckStudent_Late extends Thread {
    private boolean flag;
    private String url;
    private String subject_id;
    private int subject_th;
    private String late_check_student_num;

    public CountCheckStudent_Late(String url, String subject_id, int subject_th) {
        // TODO Auto-generated constructor stub
        this.url = url;
        this.subject_id = subject_id;
        this.subject_th = subject_th;
    }

    private void doGet() throws IOException {
        /*将username和password传给Tomcat服务器*/
        url = url + "?subject_id=" + subject_id + "&subject_th=" + subject_th;
        try {
            URL httpUrl = new URL(url);
//            URLEncoder.encode(url);
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
                late_check_student_num = buffer.toString();
                late_check_student_num = URLDecoder.decode(late_check_student_num, "UTF-8");
            }
            //把服务端返回的数据打印出来
            System.out.println("result:" + late_check_student_num);
            if (late_check_student_num.equals("count failed")) {
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

    public String getLate_check_student_num() {
        return late_check_student_num;
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

