package com.example.airloaf.bluetoothrccar.framework;

public class MotorInfo {

    public enum MotorType{
        steer,
        drive

    }

    private MotorType mType;
    private int mPin;
    private boolean mEnabled;

    public MotorInfo(MotorType type, int pin, boolean enabled){

        mPin = pin;
        mType = type;
        mEnabled = enabled;

    }

    public int getPin(){
        return mPin;
    }

    public MotorType getType(){
        return mType;
    }

    public boolean isEnabled(){
        return mEnabled;
    }

    public void setPin(int pin){
        mPin = pin;
    }

    public void setType(MotorType type){
        mType = type;
    }

    public void setEnabled(boolean b){
        mEnabled = b;
    }

    public String toJSON(){
        String json;

        // EX: "type":"steer"
        String type = "\"type\":\"" + mType.toString() + "\"";

        // EX: "pin":1
        String pin = "\"pin\":" + mPin;

        // EX "enabled":true
        String enabled = "\"enabled\":" + mEnabled;

        json = "{" + type + "," + pin + "," + enabled + "}";

        return json;
    }

}
