package com.example.airloaf.bluetoothrccar.activities;

import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.airloaf.bluetoothrccar.R;
import com.example.airloaf.bluetoothrccar.fragments.ControlDirectionsFragment;

import java.io.IOException;

public class ControlActivity extends AppCompatActivity implements ControlDirectionsFragment.ControlListener {

    private BluetoothSocket mSocket;

    private boolean mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_control);

        mSocket = null;
        mToggle = true;

        // Load the Control fragment to the frame layout
        loadDirectionFragment();

        // Set buttons up
        setupButtons();

        getBluetoothSocket();

    }
//
//    @Override
//    protected void onStart(){
//        super.onStart();
//
//        getBluetoothSocket();
//
//    }

    private void setupButtons(){
        ImageButton button = findViewById(R.id.toggle_button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mToggle = !mToggle;
            }
        });

    }

    private void loadDirectionFragment(){

        // Create new fragment
        ControlDirectionsFragment controlFragment = new ControlDirectionsFragment();

        // Add it to the container
        getSupportFragmentManager().beginTransaction().add(R.id.control_fragment, controlFragment).commit();
    }

    private void getBluetoothSocket(){
        // Gets the Bluetooth socket statically
        mSocket = MainActivity.BLUETOOTH_SOCKET;
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

    private void sendBluetoothMessage(String msg){
        try {
            mSocket.getOutputStream().write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
