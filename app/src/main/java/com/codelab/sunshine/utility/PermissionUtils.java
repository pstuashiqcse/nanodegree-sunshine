package com.codelab.sunshine.utility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by Ashiq on 11/18/17.
 */
public class PermissionUtils {


    public static final int REQUEST_SMS = 111,
            REQUEST_WRITE_STORAGE = 112,
            REQUEST_LOCATION = 113,
            REQUEST_CALL = 114,
            REQUEST_MY_PERMISSIONS = 115;

    // permission to read phone state and receive SMS
    public static String[] SMS_PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_SMS
    };

    // permission to make a phone call
    public static String[] CALL_PERMISSIONS = {
            Manifest.permission.CALL_PHONE
    };

    // permission to write sd card
    public static String[] SD_WRITE_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // permission to get location access
    public static String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static String[] MY_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };



    public static boolean isPermissionGranted(Activity activity, String[] permissions, int requestCode) {
        boolean requirePermission = false;
        if(permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if ((ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)) {
                    requirePermission = true;
                    break;
                }
            }
        }

        if (requirePermission) {
            //ActivityCompat.requestPermissions(activity, permissions, requestCode);
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            return false;
        } else {
            return true;
        }
    }

    public static boolean isPermissionResultGranted(int[] grantResults) {
        boolean allGranted = true;
        if(grantResults != null && grantResults.length > 0) {
            for (int i : grantResults) {
                if(i != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
        }
        return allGranted;
    }

    /**
     *
     * usage
     if (PermissionUtils.isPermissionGranted(mActivity, PermissionUtils.SD_WRITE_PERMISSIONS, PermissionUtils.REQUEST_WRITE_STORAGE)) {
        loadPdf();
     }

     @Override
     public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtils.REQUEST_WRITE_STORAGE: {
                if(PermissionUtils.isPermissionResultGranted(grantResults)) {
                    loadPdf();
                }
            }
         }
     }

     */

}
