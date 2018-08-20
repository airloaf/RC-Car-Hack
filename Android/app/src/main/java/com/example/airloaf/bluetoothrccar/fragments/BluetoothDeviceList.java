package com.example.airloaf.bluetoothrccar.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.airloaf.bluetoothrccar.R;
import com.example.airloaf.bluetoothrccar.framework.BluetoothDeviceAdapter;
import com.example.airloaf.bluetoothrccar.framework.BluetoothDeviceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * Generates a list of bluetooth devices to be selected and sends
 * the MAC Address of the device selected to the parent activity
 *
 * @author Vikram Singh
 */
public class BluetoothDeviceList extends ListFragment implements ListView.OnItemClickListener {

    // Debug TAG for logging
    public static final String TAG = "RC_CAR_BLUETOOTH_DEVICE_LIST";

    // Request bit for bluetooth
    public static final int REQUEST_ENABLE_BT = 88;

    // Main activity reference as listener
    private BluetoothDeviceSelectedListener mListener;

    // Bluetooth adapter for querying paired devices
    private BluetoothAdapter mBluetoothAdapter;

    // List of bluetooth devices
    private List<BluetoothDeviceInfo> mBluetoothDevices;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        // Try to cast the activity as Bluetooth device selection listener
        try{
            mListener = (BluetoothDeviceSelectedListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement BluetoothDeviceSelectedListener");
        }

    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Fragment created");

        mBluetoothAdapter = null;
        mBluetoothDevices = null;

        setupBluetooth();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_bluetooth_device_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // Get devices and add to list
        queryBluetoothDevices();
        addDevicesToList();
    }

    /**
     * Initializes bluetooth adapter for usage
     */
    private void setupBluetooth(){

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            // device doesn't support bluetooth
            return ;
        }

        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }

    /**
     * Queries for bluetooth devices and saves the device information into a list
     */
    private void queryBluetoothDevices(){
        // Obtain the paired bluetooth devices
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();

        // Instantiate the bluetooth devices
        mBluetoothDevices = new ArrayList<>();

        // Add all paired bluetooth devices to list
        for(BluetoothDevice device: devices){

            String name = device.getName();
            String address = device.getAddress();

            BluetoothDeviceInfo deviceInfo = new BluetoothDeviceInfo(name, address);

            mBluetoothDevices.add(deviceInfo);
        }

    }

    /**
     * Adds the paired bluetooth devices to the list view
     */
    private void addDevicesToList(){

        // Get the view for the list
        ListView listView = getActivity().findViewById(R.id.bluetooth_device_list);

        // Create custom list adapter and pass in the list of bluetooth devices
        BluetoothDeviceAdapter listAdapter = new BluetoothDeviceAdapter(getContext(), mBluetoothDevices);

        // Set the adapter for the list view
        listView.setAdapter(listAdapter);

        // Set this class to listen for item clicks
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        // Get the bluetooth device selected
        BluetoothDeviceInfo deviceInfo = (BluetoothDeviceInfo) adapterView.getItemAtPosition(position);

        // Notify listener of the bluetooth address
        mListener.onBluetoothDeviceSelected(deviceInfo.getName(), deviceInfo.getAddress());

    }

    /**
     * This Interface is used to communicate when a bluetooth device has been chosen
     */
    public interface BluetoothDeviceSelectedListener {
        void onBluetoothDeviceSelected(String name, String address); // returns the MAC address of the bluetooth device chosen
    }

}
