import React, {useState, useEffect} from 'react';
import {SafeAreaView, TextInput} from 'react-native';
import DeviceNumber from 'react-native-device-number';

function PhoneNumber() {
  const [number, setNumber] = useState('');

  const getNumber = async () => {
    DeviceNumber.get().then(res => {
      setNumber(res.mobileNumber);
      console.log(res);
    });
  };

  return (
    <SafeAreaView>
      <TextInput
        style={{
          marginBottom: 20,
          color: '#000',
          width: '100%',
          borderBottomColor: '#000',
          borderBottomWidth: 1,
        }}
        placeholder="Enter 10 digit number"
        placeholderTextColor="#fff"
        underlineColorAndroid={'transparent'}
        keyboardType="number-pad"
        onChangeText={value => setNumber(value)}
        onFocus={getNumber}
        value={number}
        maxLength={13}
      />
    </SafeAreaView>
  );
}

export default PhoneNumber;
