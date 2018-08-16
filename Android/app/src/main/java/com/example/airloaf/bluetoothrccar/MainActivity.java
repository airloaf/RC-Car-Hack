package com.example.airloaf.bluetoothrccar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.airloaf.bluetoothrccar.fragments.BluetoothDeviceList;

/**
 *
 * This activity is dedicated to allowing the user to
 * choose a bluetooth device to interact with.
 *
 * @author Vikram Singh
 *
 */
public class MainActivity extends AppCompatActivity implements BluetoothDeviceList.BluetoothDeviceSelectedListener {

    private FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the fragment container
        mFragmentContainer = findViewById(R.id.fragment_container);

        createBluetoothDeviceFragment();

    }

    /**
     * Initial fragment for the fragment container
     */
    private void createBluetoothDeviceFragment(){
        // Create new fragment
        BluetoothDeviceList deviceList = new BluetoothDeviceList();

        // Add it to the container
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, deviceList).commit();
    }

    @Override
    public void onBluetoothDeviceSelected(String address) {
        System.out.println(address);
        Toast.makeText(this, address, Toast.LENGTH_LONG).show();
    }
}
