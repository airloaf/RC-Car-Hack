package com.example.airloaf.bluetoothrccar.activities;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.airloaf.bluetoothrccar.R;
import com.example.airloaf.bluetoothrccar.fragments.ControlDirectionsFragment;

import java.io.IOException;

public class ControlActivity extends AppCompatActivity implements ControlDirectionsFragment.ControlListener {

    public static final String TAG = "RC_CONTROL_ACTIVITY";

    // The bluetooth socket
    private BluetoothSocket mSocket;

    // Toggle status
    private boolean mToggle;

    private TextView mSpeedText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_control);

        mSocket = null;
        mToggle = true;

        if(savedInstanceState == null) {
            // Load the Control fragment to the frame layout
            loadDirectionFragment();

        }

        // Set buttons up
        setupWidgets();


        getBluetoothSocket();

    }

    private void getBluetoothSocket(){
        // Gets the Bluetooth socket statically
        mSocket = MainActivity.BLUETOOTH_SOCKET;

        Log.d(TAG, "Getting Bluetooth Socket");

        if(mSocket == null){
            Log.d(TAG, "SOCKET IS NULL");
        }

    }

    private void sendBluetoothMessage(String msg){
        try {
            mSocket.getOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Could not write to output stream");
        }
    }

    private void disconnectBluetooth(){
        // close the socket
        if(mSocket != null){
            try{
                mSocket.close();
            }catch(IOException e){
                e.printStackTrace();
            }

            // set the socket to null
            mSocket = null;
            MainActivity.BLUETOOTH_SOCKET = null;
        }
    }

    private void setupWidgets(){
        ImageButton button = findViewById(R.id.toggle_button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mToggle = !mToggle;
            }
        });

        ImageButton settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                intent.putExtra(getString(R.string.extra_bluetooth_address), mSocket.getRemoteDevice().getAddress());
                startActivityForResult(intent, 1);

            }
        });

        mSpeedText = findViewById(R.id.speed_text);

        SeekBar speedSeekBar = findViewById(R.id.speed_seekbar);
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String speedText = getString(R.string.speed_text) + i + "";
                mSpeedText.setText(speedText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void loadDirectionFragment(){

        // Create new fragment
        ControlDirectionsFragment controlFragment = new ControlDirectionsFragment();

        // Add it to the container
        getSupportFragmentManager().beginTransaction().add(R.id.control_fragment, controlFragment).commit();
    }

    @Override
    public void directionPressed(ControlDirectionsFragment.Direction direction) {
        switch(direction){
            case UP:
                sendBluetoothMessage("up");
                break;
            case DOWN:
                sendBluetoothMessage("down");
                break;
            case LEFT:
                sendBluetoothMessage("left");
                break;
            case RIGHT:
                sendBluetoothMessage("right");
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Check if the activity is being destroyed or restarted
        if(isFinishing()){
            disconnectBluetooth();
        }

    }

}
