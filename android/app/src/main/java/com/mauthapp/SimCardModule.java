package com.mauthapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import androidx.core.app.ActivityCompat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;


import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;

import java.util.List;

public class SimCardModule extends ReactContextBaseJavaModule {

    private Promise simCardPromise;  // Store the Promise to resolve/reject later

    public SimCardModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SimCardModule";
    }

    @ReactMethod
    public void getSimCardInfo(Promise promise) {
        try {
            simCardPromise = promise;  // Store the Promise

            if (hasPermission()) {
                TelephonyManager telephonyManager = (TelephonyManager) getCurrentActivity().getSystemService(Context.TELEPHONY_SERVICE);

                if (telephonyManager != null) {
                    SubscriptionManager subscriptionManager = SubscriptionManager.from(getReactApplicationContext());
                    List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

                    if (subscriptionInfoList != null && !subscriptionInfoList.isEmpty()) {
                        WritableArray simArray = Arguments.createArray();

                        for (SubscriptionInfo info : subscriptionInfoList) {
                            WritableMap simData = Arguments.createMap();
                            simData.putString("carrierName", info.getCarrierName().toString());
                            simData.putString("phoneNumber", subscriptionManager.getPhoneNumber(info.getSubscriptionId()));
                            simArray.pushMap(simData);
                        }

                        simCardPromise.resolve(simArray);
                    } else {
                        simCardPromise.reject("SIM_CARD_ERROR", "No active SIM cards found.");
                    }
                }
            } else {
                // Ask for permission
                requestPermission();
            }
        } catch (SecurityException e) {
            simCardPromise.reject("SIM_CARD_ERROR", "Permission error: " + e.getMessage());
        } catch (Exception e) {
            simCardPromise.reject("SIM_CARD_ERROR", e.getMessage());
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return ActivityCompat.checkSelfPermission(getCurrentActivity(), READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ActivityCompat.checkSelfPermission(getCurrentActivity(), READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(getCurrentActivity(), new String[]{READ_PHONE_NUMBERS, READ_PHONE_STATE}, 100);
            ActivityCompat.requestPermissions(getCurrentActivity(),
                    new String[]{READ_PHONE_NUMBERS,READ_PHONE_STATE}, 100);

        }
    }

    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        System.out.println("grantResults " + grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, fetch sim card info
                    TelephonyManager telephonyManager = (TelephonyManager) getCurrentActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephonyManager != null) {
                        SubscriptionManager subscriptionManager = SubscriptionManager.from(getReactApplicationContext());
                        List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

                        if (subscriptionInfoList != null && !subscriptionInfoList.isEmpty()) {
                            WritableArray simArray = Arguments.createArray();

                            for (SubscriptionInfo info : subscriptionInfoList) {
                                WritableMap simData = Arguments.createMap();
                                simData.putString("carrierName", info.getCarrierName().toString());
                                simData.putString("phoneNumber", subscriptionManager.getPhoneNumber(info.getSubscriptionId()));
                                simArray.pushMap(simData);
                            }

                            simCardPromise.resolve(simArray);
                        } else {
                            simCardPromise.reject("SIM_CARD_ERROR", "No active SIM cards found.");
                        }
                    }
                } else {
                    // Permission denied, handle accordingly
                    simCardPromise.reject("SIM_CARD_ERROR", "Permission denied.");
                }
                return true; // Return true to indicate that the permissions have been handled
            default:
                return false; // Return false for other request codes
        }
    }
}