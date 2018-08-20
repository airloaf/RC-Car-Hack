package com.example.airloaf.bluetoothrccar.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.airloaf.bluetoothrccar.R;

import java.io.IOException;
import java.util.UUID;

/**
 * Fragment designed for facilitating a connection with bluetooth
 *
 * @author Vikram Singh
 */
public class BluetoothConnectionFragment extends Fragment {

    public static final String TAG = "RC_BLUETOOTH_CONNECTION";

    // Listener for the activity
    private BluetoothConnectionListener mListener;

    // MAC Address for the bluetooth device to connect to
    private String mBluetoothAddress;

    private final String ADDRESS_KEY = "MAC_ADDRESS";

    // Bluetooth socket for connection with the bluetooth device
    private BluetoothSocket mSocket;

    private ConnectBluetooth mConnectBluetooth;

    /**
     * Sets the MAC address of the bluetooth device to connect to
     * @param address the MAC address of the device
     */
    public void setBluetoothAddress(String address){
        mBluetoothAddress = address;
    }

    public BluetoothConnectionFragment(){
        mBluetoothAddress = null;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        // Try to cast the activity as Bluetooth Connection listener
        try{
            mListener = (BluetoothConnectionListener) context;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Creates the appropriate view for the fragment
        return inflater.inflate(R.layout.fragment_bluetooth_connection, container, false);
    }

    /**
     * Check if there was a previous state for the fragment. If so load the mac address
     *
     * @param savedInstanceState previous state of the fragment
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Check if there was a previous state, then load the key
        if(savedInstanceState != null) {
            mBluetoothAddress = savedInstanceState.getString(ADDRESS_KEY);
        }

    }

    /**
     * Saves the mac address of the bluetooth device
     *
     * @param outState The state information of the fragment before it gets destroyed
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Put the mac address into the bundle for orientation changes
        outState.putString(ADDRESS_KEY, mBluetoothAddress);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Creates new task and executes
        mConnectBluetooth = new ConnectBluetooth();
        mConnectBluetooth.execute();

        Log.d(TAG, "STARTING TASK");

    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "STOPPING TASK");

        mConnectBluetooth.cancel(true);
        mConnectBluetooth = null;
    }

    /**
     * Interface for returning connection results
     */
    public interface BluetoothConnectionListener{
        void onConnectionResult(BluetoothConnectionStatus status, BluetoothSocket socket);
    }

    /**
     * Enum for the connection status
     */
    public enum BluetoothConnectionStatus{
        CONNECTED,
        FAILED
    }

    /**
     * Class designed for handling the connection using an asynchronous task
     *
     * @author Vikram Singh
     */
    public class ConnectBluetooth extends AsyncTask<Void, Void, Void> {

        private BluetoothConnectionStatus mStatus;

        // UUID for HC-05
        public final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        public ConnectBluetooth(){
            mStatus = BluetoothConnectionStatus.CONNECTED;
            mSocket = null;

        }

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Void doInBackground(Void... devices){

            try {
                // Get the bluetooth device to connect to through the MAC Address
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice device = adapter.getRemoteDevice(mBluetoothAddress);

                // Create a connection
                mSocket = device.createInsecureRfcommSocketToServiceRecord(BT_UUID);

                // Turn off discovery to make connection faster
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

                // Start connection
                mSocket.connect();

            }catch(IOException e){
                mStatus = BluetoothConnectionStatus.FAILED;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            mListener.onConnectionResult(mStatus, mSocket);

        }

    }


}
