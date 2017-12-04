package com.example.hp.iclass.OBJ;

import java.io.Serializable;

/**
 * Created by spencercjh on 2017/11/29.
 * iClass
 */

public class CheckOBJ implements Serializable {
    private String subject_id;
    private int subject_th;
    private String student_id;
    private int seat_index;
    private int ischeck;
    private String check_time;
    private String student_name;
    private String start_time;
    private int score;

    public CheckOBJ() {
    }

    public CheckOBJ(String subject_id, int subject_th, String student_id, int seat_index, String start_time) {
        this.subject_id = subject_id;
        this.subject_th = subject_th;
        this.student_id = student_id;
        this.seat_index = seat_index;
        this.start_time = start_time;
    }

    //从servlet中获取(seat activity)
    public CheckOBJ(String student_id, String check_time, int ischeck, int seat_index) {
        this.student_id = student_id;
        this.check_time = check_time;
        this.ischeck = ischeck;
        this.seat_index = seat_index;
    }

    //从servlet中获取(list activity)
    public CheckOBJ(String student_id, String check_time, int ischeck) {
        this.student_id = student_id;
        this.check_time = check_time;
        this.ischeck = ischeck;
    }

    //从list里恢复数据结构
    public CheckOBJ(String student_id, String student_name, String check_time, int ischeck) {
        this.student_id = student_id;
        this.check_time = check_time;
        this.ischeck = ischeck;
        this.student_name = student_name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getSeat_index() {
        return seat_index;
    }

    public void setSeat_index(int seat_index) {
        this.seat_index = seat_index;
    }

    public int getIscheck() {
        return ischeck;
    }

    public void setIscheck(int ischeck) {
        this.ischeck = ischeck;
    }

    public int getSubject_th() {
        return subject_th;
    }

    public void setSubject_th(int subject_th) {
        this.subject_th = subject_th;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }
}