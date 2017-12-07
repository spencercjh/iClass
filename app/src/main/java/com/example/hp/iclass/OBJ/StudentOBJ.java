package com.example.hp.iclass.OBJ;

import java.io.Serializable;

/**
 * Created by SpencerCJH on 2017/9/25.
 * iClass
 */

public class StudentOBJ implements Serializable {

    private String student_id;
    private String student_password;
    private String student_name;
    private int seat_index;
    private int student_sex;
    private String student_class;
    private String student_college;

    public StudentOBJ() {
    }

    public StudentOBJ(String student_id) {
        this.student_id = student_id;
    }

    //    全体学生列表中点击一个item重新构造数据结构
    public StudentOBJ(String student_id, String student_name, String student_college, String student_class) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_college = student_college;
        this.student_class = student_class;
    }

    //    获取全体学生列表时建立数据结构
    public StudentOBJ(String student_id, String student_name, int student_sex, String student_class, String student_college) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_sex = student_sex;
        this.student_class = student_class;
        this.student_college = student_college;
    }

    public StudentOBJ(String student_id, String student_name, String student_password) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_password = student_password;
    }

    public StudentOBJ(String student_id, int seat_index) {
        this.student_id = student_id;
        this.seat_index = seat_index;
    }

    public StudentOBJ(String student_id, String student_password) {
        this.student_id = student_id;
        this.student_password = student_password;
    }

    public int getSeat_index() {
        return seat_index;
    }

    public void setSeat_index(int seat_index) {
        this.seat_index = seat_index;
    }

    public String getStudent_password() {
        return student_password;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
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

    public int getStudent_sex() {
        return student_sex;
    }

    public void setStudent_sex(int student_sex) {
        this.student_sex = student_sex;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getStudent_college() {
        return student_college;
    }

    public void setStudent_college(String student_college) {
        this.student_college = student_college;
    }

}