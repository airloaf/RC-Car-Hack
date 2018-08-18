package com.example.airloaf.bluetoothrccar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.airloaf.bluetoothrccar.fragments.BluetoothConnectionFragment;
import com.example.airloaf.bluetoothrccar.fragments.BluetoothDeviceList;

import java.util.UUID;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the fragment container
        mFragmentContainer = findViewById(R.id.fragment_container);

        createBluetoothDeviceFragment();

    }

    // Initial fragment for the fragment container
    private void createBluetoothDeviceFragment(){
        // Create new fragment
        BluetoothDeviceList deviceList = new BluetoothDeviceList();

        // Add it to the container
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, deviceList).commit();
    }

    @Override
    public void onBluetoothDeviceSelected(String address) {

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
    public void onConnectionResult(BluetoothConnectionFragment.BluetoothConnectionStatus status) {

        // Check if the bluetooth was able to connect
        if(status == BluetoothConnectionFragment.BluetoothConnectionStatus.CONNECTED){
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Not Connected", Toast.LENGTH_LONG).show();
        }

    }
}
