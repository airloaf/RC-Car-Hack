package com.example.airloaf.bluetoothrccar.framework;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class designed to facilitate reads and writes to a JSON file
 *
 * @author Vikram Singh
 */
public class JSONFileIO {

    public static final String TAG = "RC_JSON_READER";

    // Name of the file to read
    private String mFileName;


    /**
     * Constructor that sets the file's name
     * @param fileName The name of the file to load
     */
    public JSONFileIO(String fileName){
        mFileName = fileName;
    }

    /**
     * Default blank constructor
     *
     * Sets the file's name to null by default
     */
    public JSONFileIO(){
        mFileName = null;
    }

    /**
     * Checks if the file exists
     *
     * @param context the context for getting internal directory
     * @return boolean indicating whether the file exists or not
     */
    public boolean exists(Context context){
        File file = new File(context.getFilesDir(), mFileName);
        return file.exists();
    }


    private void createFile(Context context){
        File file = new File(context.getFilesDir(), mFileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "File could not be created: " + mFileName);
        }
    }

    /**
     * Returns the contents of a file
     *
     * @param context the context for getting internal directory
     * @return a string that holds the file's contents
     */
    private String readFile(Context context){

        try {
            FileInputStream fis = context.openFileInput(mFileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "File could not be found: " + mFileName);
            createFile(context);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "File could not be read: " + mFileName);
            return null;
        }

    }

    /**
     * Returns the name of the file
     *
     * @return The file's name
     */
    public String getFileName(){
        return mFileName;
    }

    /**
     * Sets the file's name
     *
     * @param fileName The name to be changed to
     */
    public void setFileName(String fileName){
        mFileName = fileName;
    }

    /**
     * Returns the JSON Object loaded from the file
     *
     * @param context a context for locating files
     * @return a JSON Object for the file
     */
    public JSONObject getJSONObject(Context context){

        String contents = readFile(context);

        if(contents == null){
            return null;
        }

        try {
            return new JSONObject(contents);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "File could not be parse: " + mFileName);
            return null;
        }

    }

    /**
     * Writes a JSON object to a file
     * @param context Context for getting the internal directory
     * @param contents JSON Object as contents to write
     */
    public void write(Context context, JSONObject contents){
        write(context, contents.toString());
    }

    /**
     * Writes a String to a file
     * @param context Context for getting the internal directory
     * @param contents String as contents to write
     */
    public void write(Context context, String contents){

        File file = new File(context.getFilesDir(), mFileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "File could not be created: " + mFileName);
            }
        }

        try {
            FileOutputStream outputStream = context.openFileOutput(mFileName, Context.MODE_PRIVATE);
            outputStream.write(contents.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "File could not be found: " + mFileName);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "File could not be written to: " + mFileName);
        }

    }

}
