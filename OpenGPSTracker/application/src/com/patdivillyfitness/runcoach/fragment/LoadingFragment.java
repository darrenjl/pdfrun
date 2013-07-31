package com.patdivillyfitness.runcoach.fragment;

import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.FacebookException;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.patdivillyfitness.runcoach.R;

public class LoadingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);        
        return view;
    }
}
