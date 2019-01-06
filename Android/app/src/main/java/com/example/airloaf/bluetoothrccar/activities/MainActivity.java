package com.example.airloaf.bluetoothrccar.activities;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airloaf.bluetoothrccar.R;
import com.example.airloaf.bluetoothrccar.fragments.BluetoothConnectionFragment;
import com.example.airloaf.bluetoothrccar.fragments.BluetoothDeviceList;

import java.io.IOException;

/**
 *
 * This activity is dedicated to allowing the user to
 * choose a bluetooth device to interact with.
 *
 * @author Vikram Singh
 *
 */
public class MainActivity extends AppCompatActivity implements BluetoothDeviceList.BluetoothDeviceSelectedListener, BluetoothConnectionFragment.BluetoothConnectionListener {

    public static final String TAG = "RC_CAR_MAIN_ACTIVITY";

    // The text view to display the current status
    private TextView mStatusText;

    public static BluetoothSocket BLUETOOTH_SOCKET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BLUETOOTH_SOCKET = null;
        Log.d(TAG, "BLUETOOTH SOCKET IS NULL");

        // Get the text view
        mStatusText = findViewById(R.id.choose_device_text);

        // If there was no previous instance state, then create new fragment
        if(savedInstanceState == null){
            createBluetoothDeviceFragment();
        }

    }

    // Initial fragment for the fragment container
    private void createBluetoothDeviceFragment(){

        String statusString = getString(R.string.choose_device_text);
        mStatusText.setText(statusString);

        // Create new fragment
        BluetoothDeviceList deviceList = new BluetoothDeviceList();

        // Add it to the container
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, deviceList).commit();

    }

    /**
     *
     * Creates the connection fragment and sends the address selected
     *
     * @param name The name of the bluetooth device
     * @param address The MAC address of the bluetooth device
     */
    @Override
    public void onBluetoothDeviceSelected(String name, String address) {

        String statusString = getString(R.string.connecting_to_device) + " " + name;
        mStatusText.setText(statusString);

        // Create new bluetooth address
        BluetoothConnectionFragment connectionFragment = new BluetoothConnectionFragment();
        connectionFragment.setBluetoothAddress(address);

        // Create a fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace fragment and add to back stack
        transaction.replace(R.id.fragment_container, connectionFragment);
        transaction.addToBackStack(null);

        // Commit changes
        transaction.commit();
    }

    /**
     * Handles the connection result when connecting to a bluetooth device
     *
     * If connected it will set BLUETOOTH_SOCKET to the socket passed in and switch activities to the control activity
     *
     * If not connected it will go back to the bluetooth device list fragment
     *
     * @param status The connection status to the device
     * @param socket The resulting BluetoothSocket reference
     */
    @Override
    public void onConnectionResult(BluetoothConnectionFragment.BluetoothConnectionStatus status, BluetoothSocket socket) {

        // Set the socket
        BLUETOOTH_SOCKET = socket;

        // Check if the bluetooth was able to connect
        if(status == BluetoothConnectionFragment.BluetoothConnectionStatus.CONNECTED){


            // Toast to let the user know they connected
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();

            // pop off current fragment
            getSupportFragmentManager().popBackStack();

            // Go to control activity
            Intent intent = new Intent(this, ControlActivity.class);
            startActivity(intent);

        }else{

            // Toast to let the user know they were not able to connect
            Toast.makeText(this, "Not Connected", Toast.LENGTH_LONG).show();

            // Set status text to connection failed
            mStatusText.setText(R.string.connection_failed);

            // pop off current fragment
            getSupportFragmentManager().popBackStack();
        }

    }
}
