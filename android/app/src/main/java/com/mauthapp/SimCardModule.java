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

            // if (hasPermission()) {
            //     TelephonyManager telephonyManager = (TelephonyManager) getCurrentActivity().getSystemService(Context.TELEPHONY_SERVICE);

            //     if (telephonyManager != null) {
            //         SubscriptionManager subscriptionManager = SubscriptionManager.from(getReactApplicationContext());
            //         List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

            //         if (subscriptionInfoList != null && !subscriptionInfoList.isEmpty()) {
            //             WritableArray simArray = Arguments.createArray();

            //             for (SubscriptionInfo info : subscriptionInfoList) {
            //                 WritableMap simData = Arguments.createMap();
            //                 simData.putString("carrierName", info.getCarrierName().toString());
            //                 simData.putString("phoneNumber", subscriptionManager.getPhoneNumber(info.getSubscriptionId()));
            //                 simArray.pushMap(simData);
            //             }

            //             simCardPromise.resolve(simArray);
            //         } else {
            //             simCardPromise.reject("SIM_CARD_ERROR", "No active SIM cards found.");
            //         }
            //     }
            // } else {
            //     // Ask for permission
            //     // requestPermission();
            // }
        } catch (SecurityException e) {
            simCardPromise.reject("SIM_CARD_ERROR", "Permission error: " + e.getMessage());
        } catch (Exception e) {
            simCardPromise.reject("SIM_CARD_ERROR", e.getMessage());
        }
    }
}
