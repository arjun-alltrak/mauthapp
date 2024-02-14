package com.mauthapp;

import android.util.Log;
import com.facebook.react.bridge.Callback; // Import Callback class
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class CalendarModule extends ReactContextBaseJavaModule {
   CalendarModule(ReactApplicationContext context) {
       super(context);
   }

    @Override
    public String getName() {
        return "CalendarModule"; // Provide a unique name for your module
   }

   @ReactMethod
   public void createCalendarEvent(Callback callback) {
       try {
           // Perform your event creation logic here
           Log.d("CalendarModule", "Create event called with name: ");

           // Call the callback with any result or arguments
           callback.invoke("Data returned from native calendar module");
       } catch (Exception e) {
           // Call the error callback in case of an error
           callback.invoke("Error creating event: " + e.getMessage());
       }
   }
}
