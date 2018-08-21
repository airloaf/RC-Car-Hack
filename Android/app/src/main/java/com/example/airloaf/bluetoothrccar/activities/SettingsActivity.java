package com.example.airloaf.bluetoothrccar.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.airloaf.bluetoothrccar.R;
import com.example.airloaf.bluetoothrccar.framework.MotorFileIO;
import com.example.airloaf.bluetoothrccar.framework.MotorInfo;
import com.example.airloaf.bluetoothrccar.framework.MotorInfoAdapter;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    MotorInfoAdapter mAdapter;

    private String mAddress;
    private String mFileName;

    private ArrayList<MotorInfo> mMotors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        mAddress = intent.getStringExtra(getString(R.string.extra_bluetooth_address));

        mFileName = mAddress + ".json";

        loadMotorInfo();
        loadList();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void loadMotorInfo(){
        MotorFileIO motorFileIO = new MotorFileIO(this, mFileName);

        // Get the motors
        mMotors = motorFileIO.getMotors();

    }

    private void loadList(){

        // get the view for the list
        ListView listView = findViewById(R.id.list_motor_info);

        // creates a list adapter
        MotorInfoAdapter listAdapter = new MotorInfoAdapter(this, mMotors);

        // set the adapter to the custom motor info adapter
        listView.setAdapter(listAdapter);

        // set the class to listen for item clicks
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
