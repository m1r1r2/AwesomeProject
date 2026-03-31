
import React, { useState } from "react";
import {
  Text,
  View,
  TextLayoutEvent,
  StyleSheet,

} from "react-native";
import NewModuleButton from "./NewModuleButton";

type Props = {
  text: string;
};

const TwoLineText = ({ text }: Props) => {
  const [expanded, setExpanded] = useState(false);
  const [canTrim, setCanTrim] = useState(false);
  const [visibleText, setVisibleText] = useState("");

  const READ_MORE = " Read more";

  const onTextLayout = (
    e: TextLayoutEvent)  => {
    if (canTrim) return;

    const lines = e.nativeEvent.lines;

    if (lines.length > 2) {
      const firstLine = lines[0].text;
      let secondLine = lines[1].text;

      // Reserve space for "... Read more"
      const suffix = "...";

      // Trim characters from second line
      //The loop only cares about second line, not first line. It uses the length of the second line (lines[1].text.length) 
      // as the maximum allowed space, and trims secondLine until the extra “… Read more” fits.
      while ((secondLine + suffix + READ_MORE).length > lines[1].text.length) {
        secondLine = secondLine.slice(0, -1);
      }

      setVisibleText(firstLine + secondLine + suffix);
      setCanTrim(true);
    }
  };

  return (
    <View>
      {!expanded ? (
        <Text onTextLayout={onTextLayout}>
          {canTrim ? visibleText : text}
          {canTrim && (
            <Text
              style={styles.readMore}
              onPress={() => setExpanded(true)}
            >
              {READ_MORE}
            </Text>
          )}
        </Text>
      ) : (
        <Text>
          {text}
          <Text
            style={styles.readMore}
            onPress={() => setExpanded(false)}
          >
            {" Read less"}
          </Text>
        </Text>
      )}
      <NewModuleButton/>
    </View>
  );
};

const styles = StyleSheet.create({
  readMore: {
    color: "blue",
    fontWeight: "500",
  },
});

export default TwoLineText;
