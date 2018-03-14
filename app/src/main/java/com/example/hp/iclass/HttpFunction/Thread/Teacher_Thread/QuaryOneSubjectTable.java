package com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by spencercjh on 2017/12/5.
 * iClass
 */

public class QuaryOneSubjectTable extends Thread{
    private boolean flag;
    private String url;
    private String subject_id;
    private String table_name = "";

    public QuaryOneSubjectTable(String url, String subject_id) {
        // TODO Auto-generated constructor stub
        this.url = url;
        this.subject_id = subject_id;
    }

    private void doGet() throws IOException {
        /*将username和password传给Tomcat服务器*/
        url = url + "?subject_id=" + subject_id;
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
                table_name = buffer.toString();
                table_name = URLDecoder.decode(table_name, "UTF-8");
            }
            //把服务端返回的数据打印出来
            System.out.println("result:" + table_name);
            if (table_name.equals("quary subject_id table failed")) {
                setFlag(false);
            } else {
                setFlag(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(table_name.equals("")){
            setFlag(false);
        }
    }

    public boolean getFlag() {
        return flag;
    }

    private void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getTable_name() {
        return table_name;
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
