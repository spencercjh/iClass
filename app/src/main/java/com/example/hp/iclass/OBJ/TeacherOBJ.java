package com.example.hp.iclass.OBJ;

import java.io.Serializable;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class TeacherOBJ implements Serializable {

    private String teacher_id;
    private String teacher_name;
    private String teacher_password;

    public TeacherOBJ() {
    }

    public TeacherOBJ(String teacher_id, String teacher_password) {
        this.teacher_id = teacher_id;
        this.teacher_password = teacher_password;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_password() {
        return teacher_password;
    }

    public void setTeacher_password(String teacher_password) {
        this.teacher_password = teacher_password;
    }

}