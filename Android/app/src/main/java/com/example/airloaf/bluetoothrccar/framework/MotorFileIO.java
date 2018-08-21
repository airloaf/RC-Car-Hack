package com.example.airloaf.bluetoothrccar.framework;

import android.content.Context;
import android.util.Log;

import com.example.airloaf.bluetoothrccar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.airloaf.bluetoothrccar.framework.MotorInfo.MotorType;

/**
 * Class designed to facilitate reading and writing to motor json files
 *
 * @author Vikram Singh
 */
public class MotorFileIO {

    public static final String TAG = "RC_MOTOR_FILE_IO";

    private JSONFileIO mJSONIO;
    private ArrayList<MotorInfo> mMotors;
    private String mFileName;
    private Context mContext;

    public MotorFileIO(Context context, String fileName){
        mContext = context;
        mFileName = fileName;

        mJSONIO = new JSONFileIO(mFileName);

        mMotors = new ArrayList<>();

        readFile();

    }
    public MotorFileIO(Context context){
        mContext = context;

        mFileName = null;
        mJSONIO = new JSONFileIO();

        mMotors = new ArrayList<>();

    }

    public void readFile(){
        JSONObject motorFile = mJSONIO.getJSONObject(mContext);

        // Nothing was found in the object
        if(motorFile == null) return;

        try {
            JSONArray motorArray = motorFile.getJSONArray(mContext.getString(R.string.json_motor_array));

            for(int motorIndex = 0; motorIndex < motorArray.length(); motorIndex++){

                /// get the motor object from array
                JSONObject motorObject = motorArray.getJSONObject(motorIndex);

                // get the values of the motor fields
                String typeString = motorObject.getString(mContext.getString(R.string.json_motor_type));
                int pin = motorObject.getInt(mContext.getString(R.string.json_motor_pin));
                boolean enabled = motorObject.getBoolean(mContext.getString(R.string.json_motor_enabled));

                // get the motor type
                MotorInfo.MotorType type;
                if(typeString.equals(MotorType.steer.toString())) {
                    type = MotorType.steer;
                } else {
                    type = MotorType.drive;
                }

                // create the motor info object
                MotorInfo info = new MotorInfo(type, pin, enabled);

                // add to array
                mMotors.add(info);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Could not parse motors in file: " + mFileName);
        }

    }

    public void writeToFile(){
        JSONObject contentObject = new JSONObject();

        // Get JSON key strings
        String typeKey = mContext.getString(R.string.json_motor_type);
        String pinKey = mContext.getString(R.string.json_motor_pin);
        String enableKey = mContext.getString(R.string.json_motor_enabled);
        String arrayKey = mContext.getString(R.string.json_motor_array);

        try {
            JSONArray motorArray = new JSONArray();

            for(MotorInfo motorInfo: mMotors){
                // Add JSON Object with motor fields
                JSONObject currentMotor = new JSONObject();
                currentMotor.put(typeKey, motorInfo.getType().toString());
                currentMotor.put(pinKey, motorInfo.getPin());
                currentMotor.put(enableKey, motorInfo.isEnabled());

                motorArray.put(currentMotor);
            }

            contentObject.put(arrayKey, motorArray);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Could not create JSON File for: " + mFileName);
        }
        mJSONIO.write(mContext, contentObject);

    }

    public String getFileName(){
        return mFileName;
    }
    public ArrayList<MotorInfo> getMotors(){
        return mMotors;
    }

    public void setFileName(String fileName){
        mFileName = fileName;
        readFile();
    }
    public void setMotors(ArrayList<MotorInfo> motors){
        mMotors = motors;
    }

}
