import React from 'react';
import {NativeModules, Button} from 'react-native';

const {CalendarModule} = NativeModules;


const NewModuleButton = () => {

  const onPress = () => {
    CalendarModule.createCalendarEvent(
      "Dinner Party",
      "My House"
    );
  };

  return (
    <Button
      title="Create Calendar Event"
      onPress={onPress}
    />
  );
};

export default NewModuleButton;