import {View, Text} from 'react-native';
import React from 'react';
import GoogleButton from './GoogleButton';
import PhoneNumber from './PhoneNumber';
import NewModuleButton from './NewModuleButton';
import Sim from './Sim';

const App = () => {
  return (
    <View>
      {/* <GoogleButton />
      <PhoneNumber />
      <NewModuleButton /> */}
      <Sim />
    </View>
  );
};

export default App;
