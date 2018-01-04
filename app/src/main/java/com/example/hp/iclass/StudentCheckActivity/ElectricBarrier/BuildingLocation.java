package com.example.hp.iclass.StudentCheckActivity.ElectricBarrier;

import java.util.Vector;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class BuildingLocation {

 /*   public static Vector<Point> GetClassBuildingOne() {
        Point P1 = new Point(30.8821445715,121.8969983439);
        Point P2 = new Point(30.8814908190,121.8969661574);
        Point P3 = new Point(30.8817256179,121.8991816621);
        Point P4 = new Point(30.8826187694,121.8983179908);
        java.util.Vector<Point> path = new java.util.Vector<>(4);
        path.add(P1);
        path.add(P2);
        path.add(P3);
        path.add(P4);
        return path;
    }

    public static Vector<Point> GetClassBuildingTwo() {
        Point P1 = new Point(30.8828443579,121.8968535047);
        Point P2 = new Point(30.8821491753,121.8969715219);
        Point P3 = new Point(30.8825174846,121.8986613135);
        Point P4 = new Point(30.8833415715,121.8976903539);
        java.util.Vector<Point> path = new java.util.Vector<>(4);
        path.add(P1);
        path.add(P2);
        path.add(P3);
        path.add(P4);
        return path;
    }

    public static Vector<Point> GetClassBuildingThree() {
        Point P1 = new Point(30.8834474592,121.8973953109);
        Point P2 = new Point(30.8824300113,121.8985808473);
        Point P3 = new Point(30.8830653420,121.8993908744);
        Point P4 = new Point(30.8839630848,121.8982965331);
        java.util.Vector<Point> path = new java.util.Vector<>(4);
        path.add(P1);
        path.add(P2);
        path.add(P3);
        path.add(P4);
        return path;
    }

    public static Vector<Point> GetClassBuildingFour() {
        Point P1 = new Point(30.8839308583,121.8979585748);
        Point P2 = new Point(30.8830929650,121.8992943149);
        Point P3 = new Point(30.8836224039,121.9000828843);
        Point P4 = new Point(30.8845247452,121.8989563565);
        java.util.Vector<Point> path = new java.util.Vector<>(4);
        path.add(P1);
        path.add(P2);
        path.add(P3);
        path.add(P4);
        return path;
    }*/

    private static Vector<Point> GetTeachingBuildingArea() {
        Point P1 = new Point(30.8867256142, 121.8915724754);
        Point P2 = new Point(30.8829964864, 121.8919265270);
        Point P3 = new Point(30.8831346048, 121.8940776587);
        Point P4 = new Point(30.8860580649, 121.8972802162);
        Point P5 = new Point(30.8884747822, 121.8956226110);
        java.util.Vector<Point> path = new java.util.Vector<>(5);
        path.add(P1);
        path.add(P2);
        path.add(P3);
        path.add(P4);
        path.add(P5);
        return path;
    }

    public static Vector<Point> ChooseClassroomBuilding(String classroom) {
    /*    char B=classroom.charAt(0);
        System.out.println("教学楼Location:    "+B);
        if(B=='1'){
            return GetClassBuildingOne();
        }else if(B=='2'){
            return GetClassBuildingTwo();
        }else if(B=='3'){
            return GetClassBuildingThree();
        }else if(B=='4'){
            return GetClassBuildingFour();
        }else{
            return null;
        }*/
        return GetTeachingBuildingArea();
    }
}
