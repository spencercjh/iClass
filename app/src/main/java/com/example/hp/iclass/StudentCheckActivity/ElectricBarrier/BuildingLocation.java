package com.example.hp.iclass.StudentCheckActivity.ElectricBarrier;

import java.util.Vector;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class BuildingLocation {

    public static Vector<Point> GetClassBuildingOne() {
        Double X1 = 30.8820272993;
        Double Y1 = 121.8970924616;
        Point P1 = new Point(X1, Y1);
        Double X2 = 30.8817395562;
        Double Y2 = 121.8970683217;
        Point P2 = new Point(X2, Y2);
        Double X3 = 30.8817257445;
        Double Y3 = 121.8979722261;
        Point P3 = new Point(X3, Y3);
        Double X4 = 30.8819122021;
        Double Y4 = 121.8989190459;
        Point P4 = new Point(X4, Y4);
        Double X5 = 30.8824853845;
        Double Y5 = 121.8983826041;
        Point P5 = new Point(X5, Y5);
        java.util.Vector<Point> path = new java.util.Vector<>(5);
        path.add(P1);
        path.add(P2);
        path.add(P3);
        path.add(P4);
        path.add(P5);
        return path;
    }

    public static Vector<Point> GetClassBuildingTwo() {
        Double X1 = 30.8824577613;
        Double Y1 = 121.8970763683;
        Point P1 = new Point(X1, Y1);
        Double X2 = 30.8824462517;
        Double Y2 = 121.8975806236;
        Point P2 = new Point(X2, Y2);
        Double X3 = 30.8828836176;
        Double Y3 = 121.8981787562;
        Point P3 = new Point(X3, Y3);
        Double X4 = 30.8832127917;
        Double Y4 = 121.8978059292;
        Point P4 = new Point(X4, Y4);
        Double X5 = 30.8828536927;
        Double Y5 = 121.8970736861;
        Point P5 = new Point(X5, Y5);
        java.util.Vector<Point> path = new java.util.Vector<>(5);
        path.add(P1);
        path.add(P2);
        path.add(P3);
        path.add(P4);
        path.add(P5);
        return path;
    }

    public static Vector<Point> GetClassBuildingThree() {
        Double X1 = 30.8832817218;
        Double Y1 = 121.8978620152;
        Point P1 = new Point(X1, Y1);
        Double X2 = 30.8827706964;
        Double Y2 = 121.8984145503;
        Point P2 = new Point(X2, Y2);
        Double X3 = 30.8832218721;
        Double Y3 = 121.8989134412;
        Point P3 = new Point(X3, Y3);
        Double X4 = 30.8833369676;
        Double Y4 = 121.8987042289;
        Point P4 = new Point(X4, Y4);
        Double X5 = 30.8832264760;
        Double Y5 = 121.8983877282;
        Point P5 = new Point(X5, Y5);
        Double X6 = 30.8832863256;
        Double Y6 = 121.8982804399;
        Point P6 = new Point(X6, Y6);
        Double X7 = 30.8835027049;
        Double Y7 = 121.8985915761;
        Point P7 = new Point(X7, Y7);
        Double X8 = 30.8837098761;
        Double Y8 = 121.8983179908;
        Point P8 = new Point(X8, Y8);
        java.util.Vector<Point> path = new java.util.Vector<>(8);
        path.add(P1);
        path.add(P2);
        path.add(P3);
        path.add(P4);
        path.add(P5);
        path.add(P6);
        path.add(P7);
        path.add(P8);
        return path;
    }

    public static Vector<Point> GetClassBuildingFour() {
        Double X1 = 30.8837973483;
        Double Y1 = 121.8985057454;
        Point P1 = new Point(X1, Y1);
        Double X2 = 30.8836592343;
        Double Y2 = 121.8987203221;
        Point P2 = new Point(X2, Y2);
        Double X3 = 30.8838894242;
        Double Y3 = 121.8990314584;
        Point P3 = new Point(X3, Y3);
        Double X4 = 30.8837835369;
        Double Y4 = 121.8991333823;
        Point P4 = new Point(X4, Y4);
        Double X5 = 30.8835211202;
        Double Y5 = 121.8988866191;
        Point P5 = new Point(X5, Y5);
        Double X6 = 30.8833553829;
        Double Y6 = 121.8990904670;
        Point P6 = new Point(X6, Y6);
        Double X7 = 30.8837973483;
        Double Y7 = 121.8996322732;
        Point P7 = new Point(X7, Y7);
        Double X8 = 30.8842393116;
        Double Y8 = 121.8989778142;
        Point P8 = new Point(X8, Y8);
        java.util.Vector<Point> path = new java.util.Vector<>(8);
        path.add(P1);
        path.add(P2);
        path.add(P3);
        path.add(P4);
        path.add(P5);
        path.add(P6);
        path.add(P7);
        path.add(P8);
        return path;
    }

    public static Vector<Point> ChooseClassroomBuilding(String classroom){
        char B=classroom.charAt(0);
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
        }
    }
}
