package com.example.airloaf.bluetoothrccar.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.airloaf.bluetoothrccar.R;
import com.example.airloaf.bluetoothrccar.framework.MotorInfo;
import com.example.airloaf.bluetoothrccar.framework.MotorInfo.MotorType;

/**
 *
 * Custom Dialog fragment for allowing the user to set motor information
 *
 * @author Vikram Singh
 */
public class MotorDeviceFragment extends DialogFragment {

    // Debug TAG for Log Cat
    public static final String TAG = "RC_MOTOR_DEVICE_FRAG";

    MotorDialogListener mListener;

    private MotorType mMotorType;
    private int mMotorPin;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (MotorDialogListener) context;
        }catch(ClassCastException e){
            Log.e(TAG, "Context needs to implement MotorDialogListener");
        }


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create the Alert Dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Create the view via an inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_motor_device, null);

        // Bind the spinner data
        bindSpinnerData(view);

        // Set the dialog title as well as buttons for positive and negative buttons ("Ok" and "Cancel")
        builder.setTitle("Edit Motor").setView(view)
        .setPositiveButton(R.string.dialog_motor_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onMotorInformationSet(new MotorInfo(mMotorType, mMotorPin, true));
            }
        }).setNegativeButton(R.string.dialog_motor_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_motor_device, container, false);
    }

    /**
     * Adds the data for the type and pin spinners
     * @param view
     */
    private void bindSpinnerData(View view){

        // Get the type spinner
        Spinner mTypeSpinner = view.findViewById(R.id.spinner_motor_type);

        // Set the adapter
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_motor_type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(typeAdapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner) adapterView;
                String typeString = spinner.getSelectedItem().toString();

                Log.d(TAG, "Motor type was: " + typeString);

                if(typeString.equals(MotorType.drive.toString())){
                    mMotorType = MotorType.drive;
                }else{
                    mMotorType = MotorType.steer;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Get the pin spinner
        Spinner mPinSpinner = view.findViewById(R.id.spinner_motor_pin);

        // Set the adapter
        ArrayAdapter<CharSequence> pinAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_motor_pin, android.R.layout.simple_spinner_item);
        pinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPinSpinner.setAdapter(pinAdapter);
        mPinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner) adapterView;
                String pinString = spinner.getSelectedItem().toString();
                mMotorPin = Integer.parseInt(pinString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public interface MotorDialogListener{
        void onMotorInformationSet(MotorInfo info);
    }

}
