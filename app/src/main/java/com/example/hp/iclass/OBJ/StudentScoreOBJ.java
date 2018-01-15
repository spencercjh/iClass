package com.example.hp.iclass.OBJ;

/**
 * Created by spencercjh on 2018/1/15.
 * iClass
 */

public class StudentScoreOBJ {
    private String student_id;  //学生学号
    private String student_name;    //学生姓名
    private int num_checkin;    //出勤次数
    private int num_uncheck_in; //缺勤次数
    private int num_late;   //迟到次数
    private int num_pp; //代签次数
    private int num_good;   //好评次数
    private int num_bad;    //差评次数
    private int class_score;    //平时课堂成绩

    StudentScoreOBJ() {

    }

    StudentScoreOBJ(String student_id, String student_name, int num_checkin, int num_uncheck_in, int num_late, int num_pp, int num_good, int num_bad, int class_score) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.num_checkin = num_checkin;
        this.num_uncheck_in = num_uncheck_in;
        this.num_late = num_late;
        this.num_pp = num_pp;
        this.num_good = num_good;
        this.num_bad = num_bad;
        this.class_score = class_score;
    }

    public int getClass_score() {
        return class_score;
    }

    public void setClass_score(int class_score) {
        this.class_score = class_score;
    }

    public int getNum_bad() {
        return num_bad;
    }

    public void setNum_bad(int num_bad) {
        this.num_bad = num_bad;
    }

    public int getNum_checkin() {
        return num_checkin;
    }

    public void setNum_checkin(int num_checkin) {
        this.num_checkin = num_checkin;
    }

    public int getNum_good() {
        return num_good;
    }

    public void setNum_good(int num_good) {
        this.num_good = num_good;
    }

    public int getNum_late() {
        return num_late;
    }

    public void setNum_late(int num_late) {
        this.num_late = num_late;
    }

    public int getNum_pp() {
        return num_pp;
    }

    public void setNum_pp(int num_pp) {
        this.num_pp = num_pp;
    }

    public int getNum_uncheck_in() {
        return num_uncheck_in;
    }

    public void setNum_uncheck_in(int num_uncheck_in) {
        this.num_uncheck_in = num_uncheck_in;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }
}