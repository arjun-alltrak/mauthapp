import React from 'react';
import {TouchableOpacity, SafeAreaView, Text, View} from 'react-native';
import auth from '@react-native-firebase/auth';
import {GoogleSignin} from '@react-native-google-signin/google-signin';

GoogleSignin.configure({
  webClientId:
    '963921972784-6ododnqjsrlgtjkis041obnj5285vkcn.apps.googleusercontent.com',
});

function GoogleButton() {
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

  return (
    <SafeAreaView>
      <View>
        <TouchableOpacity onPress={onGoogleButtonPress}>
          <Text>Sign In with Google</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
}

export default GoogleButton;
