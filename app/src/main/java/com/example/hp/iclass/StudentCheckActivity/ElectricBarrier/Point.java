package com.example.hp.iclass.StudentCheckActivity.ElectricBarrier;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class Point {
    Double x;   //Latitude纬度
    Double y;   //Longitude经度

    Point() {
    }

    Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public void swap(Point p) {
        Double tmp;
        tmp = this.x;
        this.x = p.x;
        p.x = tmp;

        tmp = this.y;
        this.y = p.y;
        p.y = tmp;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
