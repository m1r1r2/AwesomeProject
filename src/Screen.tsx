import React from "react";
import { StyleSheet, View, requireNativeComponent, Text } from "react-native";

const KundliView = requireNativeComponent<any>("KundliView");

export default function Screen() {
  const chartData = {
    ascendant: 1,
    houses: [
      { planet: "Asc", degree: "15°", house: 1} ,
      { planet: "Su", degree: "23°15'", house: 2 },
      { planet: "Mo", degree: "10°23'", house: 4 },
      { planet: "Ma(R)", degree: "5°0'", house: 3 },
      { planet: "Ve", degree: "18°23'", house: 5 },
      { planet: "Ke", degree: "18°24'", house: 6},
      { planet: "Sa(R)", degree: "4°23'", house: 12 },
      { planet: "Ra", degree: "1°59'", house: 7 },
      { planet: "Ju(R)", degree: "12°78'", house: 8},
      { planet: "Pl", degree: "6°90", house: 9 },
      { planet: "Ur", degree: "5°55'", house: 10 },
      { planet: "Ne", degree: "10°67", house: 11},
    ],
  };

  return (
    <View style={styles.container}>
      {/* Information Section */}
      <View style={styles.infoSection}>
        <Text style={styles.title}>Astrological Chart</Text>
        <Text style={styles.text}>Name: Mayuri Khandelwal</Text>
        <Text style={styles.text}>Place of Birth: xyz</Text>
        <Text style={styles.text}>Time of Birth: 1:00 PM</Text>
      </View>

      {/* Chart Section */}
      <View style={styles.chartWrapper}>
        <KundliView
          style={styles.kundli}
          data={chartData}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#FFFFFF", // Use Hex code for "Absolute" white
    paddingTop: 60,            // Extra padding for Android Status Bar
  },
  infoSection: {
    // Remove flexDirection: "column" (it is the default anyway)
    paddingHorizontal: 20,
    marginBottom: 20,
    minHeight: 120,           // Force a minimum height so it can't be crushed
    zIndex: 10,               // Ensure it sits "above" the native view
  },
  title: { 
    fontSize: 22, 
    fontWeight: "bold", 
    color: "#000000",         // Use Hex code for "Absolute" black
    marginBottom: 10 
  },
  text: { 
    fontSize: 18, 
    color: "#333333",         // Dark grey/black
    marginVertical: 2 
  },
  chartWrapper: {
    // REMOVE flexDirection: "row" -> This is likely squishing your chart
    alignItems: "center", 
    justifyContent: "center",
    marginTop: 10,
  },
  kundli: {
    width: 380,
    height: 380,
    // On Android, sometimes the native view needs a background to "exist"
    backgroundColor: "transparent", 
  },
});

