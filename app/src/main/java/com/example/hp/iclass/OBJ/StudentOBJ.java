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

    public StudentOBJ() {
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

}
