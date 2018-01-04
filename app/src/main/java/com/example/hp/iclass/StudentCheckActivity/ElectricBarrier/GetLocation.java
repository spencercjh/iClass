package com.example.hp.iclass.StudentCheckActivity.ElectricBarrier;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class GetLocation {
    LocationManager locationManager;
    Location location;
    Activity activity;
    Context context;
    Location answer;

    public GetLocation(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        init();
    }

    public void init() {
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        // 获取location对象
        location = getBestLocation(locationManager);
        updateLocation(location);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, 8, new LocationListener() {
                    @Override
                    public void onStatusChanged(String provider, int status,
                                                Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                        PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        updateLocation(locationManager
                                .getLastKnownLocation(provider));
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        updateLocation(null);
                    }

                    @Override
                    public void onLocationChanged(Location location) {
                        location = getBestLocation(locationManager);// 每次都去获取GPS_PROVIDER优先的location对象
                        updateLocation(location);
                    }
                });
    }

    private Point updateLocation(Location location) {
        if (location != null) {
           /* StringBuffer sb = new StringBuffer();
            sb.append("位置信息：\n");
            sb.append("经度：" + location.getLongitude() + ", 纬度："
                    + location.getLatitude());*/
            return new Point(location.getLongitude(), location.getLatitude());
        } else {
            return new Point(0d, 0d);
        }
    }

    public Point getLocation() {
        answer = location;  ///X:Latitude纬度Y:Longitude经度
        if (answer != null) {
            System.out.println("Location:       "+answer.getLatitude()+","+answer.getLongitude());
            return new Point(answer.getLatitude(),answer.getLongitude());
        } else {
            return new Point(0d, 0d);
        }
    }

    /**
     * 获取location对象，优先以GPS_PROVIDER获取location对象，当以GPS_PROVIDER获取到的locaiton为null时
     * ，则以NETWORK_PROVIDER获取location对象，这样可保证在室内开启网络连接的状态下获取到的location对象不为空
     *
     * @param locationManager
     * @return
     */
    private Location getBestLocation(LocationManager locationManager) {
        Location result = null;
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            result = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (result != null) {
                return result;
            } else {
                result = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                return result;
            }
        }
        return null;
    }

}