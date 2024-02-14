import {View, Text} from 'react-native';
import React from 'react';
import GoogleButton from './GoogleButton';
import PhoneNumber from './PhoneNumber';
import NewModuleButton from './NewModuleButton';
import SimDetection from './SimDetection';

const App = () => {
  return (
    <View>
      <GoogleButton />
      <PhoneNumber />
      <NewModuleButton />
      <SimDetection />
    </View>
  );
};

export default App;
