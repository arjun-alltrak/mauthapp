// SimModule.java

package com.mauthapp;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class SimModule extends ReactContextBaseJavaModule {

    public SimModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SimModule";
    }

    @ReactMethod
    public void getPhoneNumber() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getCurrentActivity().getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = telephonyManager.getLine1Number();
            // Emit the phone number as an event or handle it as needed
            sendEvent(getReactApplicationContext(), "PhoneNumberEvent", phoneNumber);
        } catch (SecurityException e) {
            // Handle the exception, e.g., by emitting an error event
            sendEvent(getReactApplicationContext(), "PhoneNumberErrorEvent", "Permission error: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions, e.g., by emitting an error event
            sendEvent(getReactApplicationContext(), "PhoneNumberErrorEvent", e.getMessage());
        }
    }

    private void sendEvent(ReactApplicationContext reactContext, String eventName, String params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
    
}
