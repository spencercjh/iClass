package com.example.hp.iclass.HttpFunction.Thread.Student_Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class UpdateStudentDeviceCode extends Thread {
    private boolean flag;
    private String url;
    private String student_id;
    private String device_code;

    public UpdateStudentDeviceCode(String url, String teacher_id, String device_code) {
        // TODO Auto-generated constructor stub
        this.url = url;
        this.student_id = teacher_id;
        this.device_code = device_code;
    }

    private void doGet() throws IOException {
        /*将username和password传给Tomcat服务器*/
        url = url + "?student_id=" + student_id
                + "&device_code=" + device_code;
        try {
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
                device_code = buffer.toString();
                device_code = URLDecoder.decode(device_code, "UTF-8");
            }
            //把服务端返回的数据打印出来
            System.out.println("result:" + device_code);
            if (getDevice_code().equals("update device_code failed")) {
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

    private String getDevice_code() {
        return device_code;
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