package com.example.airloaf.bluetoothrccar.framework;

/**
 * Class to hold bluetooth device information
 * for creating lists.
 *
 * @author Vikram Singh
 */
public class BluetoothDeviceInfo {

    private String mName;
    private String mAddress;

    public BluetoothDeviceInfo(String name, String address){
        mName = name;
        mAddress = address;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }
}
