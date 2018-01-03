package com.example.hp.iclass.StudentCheckActivity.ElectricBarrier;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

/**
 * Created by spencercjh on 2018/1/3.
 * iClass
 */

public class GetLocation {
    public static Point getLocation(final Activity activity, Context context) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
           /* AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setMessage("GPS未打开，是否打开?");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {*/
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    // 设置完成后返回到原来的界面
                    activity.startActivityForResult(intent, 0);
      /*          }
            });
            dialog.show();*/
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
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
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        Log4Lsy.i(TAG, "纬度："+location.getLatitude());
//        Log4Lsy.i(TAG, "经度："+location.getLongitude());
//        Log4Lsy.i(TAG, "海拔："+location.getAltitude());
//        Log4Lsy.i(TAG, "时间："+location.getTime());
        return new Point(location.getLongitude(), location.getLatitude());
    }
}