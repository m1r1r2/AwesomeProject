import React from 'react';
import {
  StatusBar,
  StyleSheet,
  useColorScheme,
  View,
} from 'react-native';
import { SafeAreaProvider } from 'react-native-safe-area-context';
//import TwoLineText from './src/TwoLineText';
//mport ChartScreen from './src/ChartScreen';
import Screen from './src/Screen';
function App() {
  const isDarkMode = useColorScheme() === 'dark';

  return (
    <SafeAreaProvider>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <View style={styles.container}>
        {/* <TwoLineText
          text="I give permission to fetch & upload my KYC details from CKYC or KRA. I consent to access and fetch my KYC details and bank account details from our affiliates or third parties."
        /> */}
        <Screen/>
      </View>
    </SafeAreaProvider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
});

export default App;
