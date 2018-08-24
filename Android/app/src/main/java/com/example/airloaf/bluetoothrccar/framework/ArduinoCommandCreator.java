package com.example.airloaf.bluetoothrccar.framework;

/**
 * Class used to generate arduino commands
 *
 * @author Vikram Singh
 */
public class ArduinoCommandCreator {

    /**
     * Generates the SetSpeed arduino command
     *
     * Example:
     * generateSetSpeed(128) will output the following
     * {"Command":"SetSpeed","Params":{"speed":128}}
     *
     * @param speed The speed you want to set the motors to
     * @return String json object representing the set speed command
     */
    public static String generateSetSpeed(int speed){
        return "{\"Command\":\"SetSpeed\",\"Params\":{\"speed\":" + speed + "}";
    }

    /**
     * Generates the SetDriveDirection arduino command
     *
     * Example:
     * generateSetDriveDirection("forward") will output the following
     * {"Command":"SetDriveDirection","Params":{"direction":"forward"}}
     *
     * @param direction The direction you want to set the drive motors to
     * @return String json object representing the set drive direction command
     */
    public static String generateSetDriveDirection(String direction){
        return "{\"Command\":\"SetDriveDirection\",\"Params\":{\"direction\":\"" + direction + "\"}";
    }

    /**
     * Generates the SetSteerDirection arduino command
     *
     * Example:
     * generateSetSteerDirection("forward") will output the following
     * {"Command":"SetSteerDirection","Params":{"direction":"forward"}}
     *
     * @param direction The direction you want to set the steer motors to
     * @return String json object representing the set steer direction command
     */
    public static String generateSetSteerDirection(String direction){
        return "{\"Command\":\"SetSteerDirection\",\"Params\":{\"direction\":\"" + direction + "\"}";
    }
}
