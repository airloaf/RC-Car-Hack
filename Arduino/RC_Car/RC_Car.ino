#include <SoftwareSerial.h>

#include "MotorSteerWheel.h"
#include "MotorDriveWheel.h"

#define rxPin 15
#define txPin 14

SoftwareSerial bluetooth(rxPin, txPin); // RX, TX

String message; // sent message

MotorSteerWheel steeringMotor(1, 255);
MotorDriveWheel frontWheel(2,255);
MotorDriveWheel backWheel(4,255);

void setup() {
  Serial.begin(115200);
  Serial.println("Hello Wordl!");

  bluetooth.begin(115200);
  bluetooth.println("Hello world (BT)");

  backWheel.setEnable(false
  );

}

void loop() {

  char letter;
  message = "";
  while (bluetooth.available()) {
    letter = bluetooth.read();
    message += letter;
  }

  if (message == "")
    return;
  Serial.println(message);

  if(message == "left"){
    steeringMotor.setDirection(STEER_LEFT);
  }else if(message == "right"){
    steeringMotor.setDirection(STEER_RIGHT);
  }

  if(message == "up"){
    frontWheel.setDirection(DRIVE_FORWARD);
    backWheel.setDirection(DRIVE_FORWARD);
  }else if(message == "down"){
    frontWheel.setDirection(DRIVE_BACKWARD);
    backWheel.setDirection(DRIVE_BACKWARD);
  }


}
