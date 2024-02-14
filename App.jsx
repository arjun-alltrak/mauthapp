import React, {useState, useEffect} from 'react';
import {
  TouchableOpacity,
  SafeAreaView,
  StatusBar,
  StyleSheet,
  Text,
  TextInput,
  View,
  DeviceEventEmitter,
  NativeModules,
} from 'react-native';
import auth from '@react-native-firebase/auth';
import {GoogleSignin} from '@react-native-google-signin/google-signin';
import DeviceNumber from 'react-native-device-number';
import DeviceInfo from 'react-native-device-info';

GoogleSignin.configure({
  webClientId:
    '963921972784-6ododnqjsrlgtjkis041obnj5285vkcn.apps.googleusercontent.com',
});

function App() {
  const [phoneNumber, setPhoneNumber] = useState('');
  const {SimCardModule} = NativeModules;

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

      if (apiLevel !== 33) return;

      const result = await SimCardModule.getSimCardInfo();
      console.log('SIM Card Information:', result);
      if (result.length) {
        // setLoginInfo(result); // loginInfo variable not defined in your code
      }
    } catch (error) {
      console.error('Error retrieving SIM card information:', error);
    }
  };

  const onGoogleButtonPress = async () => {
    try {
      // Check if your device supports Google Play
      await GoogleSignin.hasPlayServices({showPlayServicesUpdateDialog: true});
      // Get the users ID token
      const {idToken} = await GoogleSignin.signIn();

      // Create a Google credential with the token
      const googleCredential = auth.GoogleAuthProvider.credential(idToken);

      // Sign-in the user with the credential
      await auth().signInWithCredential(googleCredential);
      console.log('Signin Successfully');
    } catch (error) {
      console.log('Error : ', error);
    }
  };

  const getNumber = async () => {
    DeviceNumber.get().then(res => {
      setPhoneNumber(res.mobileNumber);
      console.log(res);
    });
  };

  return (
    <SafeAreaView>
      <View>
        <TouchableOpacity onPress={onGoogleButtonPress}>
          <Text>Sign In with Google</Text>
        </TouchableOpacity>
      </View>

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
        onChangeText={value => setPhoneNumber(value)}
        onFocus={getNumber}
        value={phoneNumber}
        maxLength={13}
      />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({});

export default App;
