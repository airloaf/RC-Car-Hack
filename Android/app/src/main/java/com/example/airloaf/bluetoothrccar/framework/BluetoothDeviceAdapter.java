package com.example.airloaf.bluetoothrccar.framework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.airloaf.bluetoothrccar.R;

import java.util.List;

/**
 * A custom ArrayAdapter to bind Bluetooth Device information to a list
 */
public class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDeviceInfo> {

    private Context mContext;
    private List<BluetoothDeviceInfo> mBluetoothDeviceList;

    public BluetoothDeviceAdapter(@NonNull Context context, @NonNull List<BluetoothDeviceInfo> devices) {
        super(context, 0, devices);
        mContext = context;
        mBluetoothDeviceList = devices;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.custom_bluetooth_device_row, parent, false);
        }

        BluetoothDeviceInfo currentDevice = mBluetoothDeviceList.get(position);

        TextView name = listItem.findViewById(R.id.bluetooth_device_name);
        TextView address = listItem.findViewById(R.id.bluetooth_device_address);

        name.setText(currentDevice.getName());
        address.setText(currentDevice.getAddress());

        return listItem;

    }
}
