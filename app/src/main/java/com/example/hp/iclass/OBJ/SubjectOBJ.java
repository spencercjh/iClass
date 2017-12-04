package com.example.hp.iclass.OBJ;

import java.io.Serializable;

/**
 * Created by spencercjh on 2017/11/27.
 * iClass
 */

public class SubjectOBJ implements Serializable {
    private String subject_id;
    private String subject_name;
    private int subject_th;
    private String teacher_id;
    private int class_type;
    private int student_num;
    private String classroom;
    private int check_situation;
    private String teacher_name;    //服务器mysql中并没有这一列！


    public SubjectOBJ() {
    }

    //学生用的subjectOBJ对象
    public SubjectOBJ(String subject_id, String subject_name, String teacher_name, String classroom) {
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.teacher_name = teacher_name;
        this.classroom = classroom;
    }

    //从servlet里接收的时候创建subjectOBJ对象
    public SubjectOBJ(String subject_id, String subject_name, int student_num, String classroom, int check_situation) {
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.student_num = student_num;
        this.classroom = classroom;
        this.check_situation = check_situation;
    }

    //从List导出数据结构的时候用到的构造函数，只显示以下属性所以只能获得到以下属性
    public SubjectOBJ(String subject_id, String subject_name, String teacher_id,
                      int student_num, String classroom, int check_situation) {
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.teacher_id = teacher_id;
        this.student_num = student_num;
        this.classroom = classroom;
        this.check_situation = check_situation;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getSubject_th() {
        return subject_th;
    }

    public void setSubject_th(int subject_th) {
        this.subject_th = subject_th;
    }

    public int getClass_type() {
        return class_type;
    }

    public void setClass_type(int class_type) {
        this.class_type = class_type;
    }

    public int getStudent_num() {
        return student_num;
    }

    public void setStudent_num(int student_num) {
        this.student_num = student_num;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getCheck_situation() {
        return check_situation;
    }

    public void setCheck_situation(int check_situation) {
        this.check_situation = check_situation;
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
}
