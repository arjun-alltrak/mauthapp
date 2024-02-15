// Your React Native component

import React, {useEffect} from 'react';
import {NativeModules, Button, NativeEventEmitter} from 'react-native';

const eventEmitter = new NativeEventEmitter(NativeModules.SimModule);

const Sim = () => {
  useEffect(() => {
    const subscription = eventEmitter.addListener(
      'PhoneNumberEvent',
      phoneNumber => {
        console.log('Received phone number:', phoneNumber);
      },
    );

    return () => {
      subscription.remove();
    };
  }, []);

  const onPress = async () => {
    try {
      await NativeModules.SimModule.getPhoneNumber();
    } catch (error) {
      console.error('Error getting phone number:', error);
    }
  };

  return <Button title="Get Phone Number" onPress={onPress} />;
};

export default Sim;
