package com.example.airloaf.bluetoothrccar.framework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.airloaf.bluetoothrccar.R;

import java.util.List;

public class MotorInfoAdapter extends BaseAdapter implements ListAdapter {

    private Context mContext;
    private List<MotorInfo> mMotors;

    public MotorInfoAdapter(@NonNull Context context, @NonNull List<MotorInfo> motors) {

        mContext = context;
        mMotors = motors;

    }

    public List<MotorInfo> getMotors(){
        return mMotors;
    }

    public void setMotors(List<MotorInfo> motors){
        mMotors = motors;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){
        return mMotors.size();
    }

    @Override
    public Object getItem(int pos){
        return mMotors.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.custom_motor_row, parent, false);
        }

        MotorInfo currentMotor = mMotors.get(position);

        TextView typeText = listItem.findViewById(R.id.tv_motor_type);
        TextView pinText = listItem.findViewById(R.id.tv_motor_pin);

        String type = mContext.getString(R.string.motor_row_type) + " " + currentMotor.getType().toString();
        String pin = mContext.getString(R.string.motor_row_pin) + " " + currentMotor.getPin();

        typeText.setText(type);
        pinText.setText(pin);

        ImageView deleteButton = listItem.findViewById(R.id.button_delete_item);
        Switch enableSwitch = listItem.findViewById(R.id.enable_switch);

        enableSwitch.setChecked(currentMotor.isEnabled());

        deleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mMotors.remove(position);
                notifyDataSetChanged();
            }
        });

        enableSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MotorInfo motor = mMotors.get(position);
                motor.setEnabled(compoundButton.isChecked());
                notifyDataSetChanged();
            }
        });

        return listItem;


    }
}
