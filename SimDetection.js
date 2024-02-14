import React, {useState, useEffect} from 'react';
import DeviceInfo from 'react-native-device-info';
import {
  View,
  Text,
  NativeModules,
  NativeEventEmitter,
  DeviceEventEmitter,
} from 'react-native';

const SimDetection = () => {
  const {SimCardModule, NewSimCard} = NativeModules;

  const handlePhoneNumbersReceived = phoneNumbers => {
    console.log('Phone Numbers:', phoneNumbers);
  };

  useEffect(() => {
    getSimData();
    DeviceEventEmitter.addListener(
      'onPhoneNumbersReceived',
      handlePhoneNumbersReceived,
    );
  }, []);

  const getSimData = async () => {
    try {
      const apiLevel = await DeviceInfo.getApiLevel();

      //   if (apiLevel != 33) return;

      if (!loginInfo.length) {
        const result = await SimCardModule.getSimCardInfo();
        console.log('SIM Card Information:', result);
        if (result.length) {
          setLoginInfo(result);
        }
      }
    } catch (error) {
      console.error('Error retrieving SIM card information:', error);
    }
  };

  return (
    <View>
      <Text>SimDetection</Text>
    </View>
  );
};

export default SimDetection;
