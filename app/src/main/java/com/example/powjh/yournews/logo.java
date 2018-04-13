package com.example.powjh.yournews;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class logo extends Fragment implements View.OnClickListener {
    public logo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_logo, container, false);
        RelativeLayout r = v.findViewById(R.id.logo);
        r.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v){
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }

}
