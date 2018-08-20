package com.example.airloaf.bluetoothrccar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.airloaf.bluetoothrccar.R;

public class ControlDirectionsFragment extends Fragment {

    private ControlListener mListener;

    public enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            mListener = (ControlListener) context;
        }catch(ClassCastException e){
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_control_directions, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initDirectionButtons();
    }

    private void initDirectionButtons(){
        ImageButton upButton = getActivity().findViewById(R.id.controller_up_direction);
        ImageButton downButton = getActivity().findViewById(R.id.controller_down_direction);
        ImageButton leftButton = getActivity().findViewById(R.id.controller_left_direction);
        ImageButton rightButton = getActivity().findViewById(R.id.controller_right_direction);

        upButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mListener.directionPressed(Direction.UP);
            }
        });

        downButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mListener.directionPressed(Direction.DOWN);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mListener.directionPressed(Direction.LEFT);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mListener.directionPressed(Direction.RIGHT);
            }
        });

    }

    public interface ControlListener{
        void directionPressed(Direction direction);
    }

}
