#include <SoftwareSerial.h>

#define rxPin 15
#define txPin 14

SoftwareSerial bluetooth(rxPin, txPin); // RX, TX

String message; // sent message

void setup() {
  Serial.begin(115200);   
  Serial.println("Hello Wordl!");

  bluetooth.begin(115200);
  bluetooth.println("Hello world (BT)");
}

void loop(){

  char letter;
  message = "";
  while(bluetooth.available()){
    letter = bluetooth.read();
    message += letter;
  }
  if(message != "")
    Serial.println(message);
  
}
