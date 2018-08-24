package com.example.airloaf.bluetoothrccar.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    @SuppressLint("ClickableViewAccessibility")
    private void initDirectionButtons(){
        ImageButton upButton = getActivity().findViewById(R.id.controller_up_direction);
        ImageButton downButton = getActivity().findViewById(R.id.controller_down_direction);
        ImageButton leftButton = getActivity().findViewById(R.id.controller_left_direction);
        ImageButton rightButton = getActivity().findViewById(R.id.controller_right_direction);

        upButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mListener.directionPressed(Direction.UP);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mListener.directionReleased(Direction.UP);
                        return true;
                }
                return false;
            }
        });

        downButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mListener.directionPressed(Direction.DOWN);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mListener.directionReleased(Direction.DOWN);
                        return true;
                }
                return false;
            }
        });

        leftButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mListener.directionPressed(Direction.LEFT);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mListener.directionReleased(Direction.LEFT);
                        return true;
                }
                return false;
            }
        });

        rightButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mListener.directionPressed(Direction.RIGHT);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mListener.directionReleased(Direction.RIGHT);
                        return true;
                }
                return false;
            }
        });

    }

    public interface ControlListener{
        void directionPressed(Direction direction);
        void directionReleased(Direction direction);
    }

}
