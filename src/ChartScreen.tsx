import React from "react";
import { View, StyleSheet, requireNativeComponent } from "react-native";

const MyLineChart = requireNativeComponent<any>("MyLineChart");

const ChartScreen = () => {
  return (
    <View style={styles.container}>
      <MyLineChart
  style={styles.chart}
  data={[10, 30, 20, 50, 40,60,70,80,90,100]}
/>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
  },
  chart: {
    height: 300,
    width: "100%",
  },
});

export default ChartScreen;