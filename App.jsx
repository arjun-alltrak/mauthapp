import {View, Text} from 'react-native';
import React from 'react';
import GoogleButton from './GoogleButton';
import PhoneNumber from './PhoneNumber';
import NewModuleButton from './NewModuleButton';

const App = () => {
  return (
    <View>
      <GoogleButton />
      <PhoneNumber />
      <NewModuleButton />
    </View>
  );
};

export default App;
