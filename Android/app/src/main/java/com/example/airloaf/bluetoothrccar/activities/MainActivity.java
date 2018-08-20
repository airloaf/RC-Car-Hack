package com.example.airloaf.bluetoothrccar.activities;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airloaf.bluetoothrccar.R;
import com.example.airloaf.bluetoothrccar.fragments.BluetoothConnectionFragment;
import com.example.airloaf.bluetoothrccar.fragments.BluetoothDeviceList;

/**
 *
 * This activity is dedicated to allowing the user to
 * choose a bluetooth device to interact with.
 *
 * @author Vikram Singh
 *
 */
public class MainActivity extends AppCompatActivity implements BluetoothDeviceList.BluetoothDeviceSelectedListener, BluetoothConnectionFragment.BluetoothConnectionListener {

    // The frame Layout for the fragment container
    private FrameLayout mFragmentContainer;

    private TextView mStatusText;

    public static BluetoothSocket BLUETOOTH_SOCKET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the fragment container
        mFragmentContainer = findViewById(R.id.fragment_container);

        // Get the text view
        mStatusText = findViewById(R.id.choose_device_text);

        createBluetoothDeviceFragment();

        BLUETOOTH_SOCKET = null;

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

    @Override
    public void onConnectionResult(BluetoothConnectionFragment.BluetoothConnectionStatus status, BluetoothSocket socket) {

        BLUETOOTH_SOCKET = socket;

        // Check if the bluetooth was able to connect
        if(status == BluetoothConnectionFragment.BluetoothConnectionStatus.CONNECTED){

            // Go to control activity
            Intent intent = new Intent(this, ControlActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(this, "Not Connected", Toast.LENGTH_LONG).show();
        }

    }
}
