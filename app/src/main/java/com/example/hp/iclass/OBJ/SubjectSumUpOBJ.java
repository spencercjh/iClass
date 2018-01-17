package com.example.hp.iclass.OBJ;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by spencercjh on 2018/1/15.
 * iClass
 */

public class SubjectSumUpOBJ implements Serializable {
    private String subject_id;  //课程id
    private String filename;    //Excel文件名
    private int score_checkin;  //出勤权重
    private int score_uncheckin;    //未签权重
    private int score_late; //迟到权重
    private int score_good; //好评权重
    private int score_bad;  //差评权重
    private int total_points;   //平时课堂成绩满分
    private ArrayList<StudentScoreOBJ> ScoreArray;  //学生成绩表
    private ArrayList<String> StudentArray; //学生名表

    public SubjectSumUpOBJ() {

    }

    public SubjectSumUpOBJ(ArrayList<String> studentArray, String filename, String subject_id,
                           int score_checkin, int score_uncheckin, int score_late, int score_good, int score_bad, int total_points) {
        this.StudentArray = studentArray;
        this.filename = filename;
        this.subject_id = subject_id;
        this.score_checkin = score_checkin;
        this.score_uncheckin = score_uncheckin;
        this.score_late = score_late;
        this.score_good = score_good;
        this.score_bad = score_bad;
        this.total_points = total_points;
    }

    public int getTotal_points() {
        return total_points;
    }

    public void setTotal_points(int total_points) {
        this.total_points = total_points;
    }

    public int getScore_bad() {
        return score_bad;
    }

    public void setScore_bad(int score_bad) {
        this.score_bad = score_bad;
    }

    public int getScore_checkin() {
        return score_checkin;
    }

    public void setScore_checkin(int score_checkin) {
        this.score_checkin = score_checkin;
    }

    public int getScore_good() {
        return score_good;
    }

    public void setScore_good(int score_good) {
        this.score_good = score_good;
    }

    public int getScore_late() {
        return score_late;
    }

    public void setScore_late(int score_late) {
        this.score_late = score_late;
    }

    public int getScore_uncheckin() {
        return score_uncheckin;
    }

    public void setScore_uncheckin(int score_uncheckin) {
        this.score_uncheckin = score_uncheckin;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ArrayList<StudentScoreOBJ> getScoreArray() {
        return ScoreArray;
    }

    public void setScoreArray(ArrayList<StudentScoreOBJ> scoreArray) {
        ScoreArray = scoreArray;
    }

    public ArrayList<String> getStudentArray() {
        return StudentArray;
    }

    public void setStudentArray(ArrayList<String> studentArray) {
        StudentArray = studentArray;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }
}
