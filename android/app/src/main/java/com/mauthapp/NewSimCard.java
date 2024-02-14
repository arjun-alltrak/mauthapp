package com.mauthapp;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.List;

public class NewSimCard extends ReactContextBaseJavaModule {

    public NewSimCard(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "NewSimCard";
    }

    @ReactMethod
    public void getPhoneNumbers() {
        String[] phoneNumbers = getPhoneNumbers(getReactApplicationContext());

        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onPhoneNumbersReceived", Arguments.fromArray(phoneNumbers));
    }

    private String[] getPhoneNumbers(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager != null) {
            try {
                SubscriptionManager subscriptionManager = SubscriptionManager.from(context);

                if (subscriptionManager != null) {
                    List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

                    System.out.println("subscriptionInfoList  " + subscriptionInfoList.size());

                    if (subscriptionInfoList != null && subscriptionInfoList.size() >= 2) {
                        String[] phoneNumbers = new String[2];

                        for (int i = 0; i < 2; i++) {
                            SubscriptionInfo info = subscriptionInfoList.get(i);
                            System.out.println("infooooo" + info);
                            phoneNumbers[i] = telephonyManager.getLine1Number();
                        }

                        return phoneNumbers;
                    }
                    else if(subscriptionInfoList != null && subscriptionInfoList.size() >= 1){
                        String[] phoneNumbers = new String[1];

                        for (int i = 0; i < 1; i++) {
                            SubscriptionInfo info = subscriptionInfoList.get(i);
                            System.out.println(" new info  " + info);
                            phoneNumbers[i] = telephonyManager.getLine1Number();
                        }

                        return phoneNumbers;
                    }
                }
            } catch (SecurityException e) {
                    System.out.println("subscriptionInfoList Catch Error" + e);
                e.printStackTrace();
            }
        }

        return new String[]{"Phone number not available", "Phone number not available"};
    }
}