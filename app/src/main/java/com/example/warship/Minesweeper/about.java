package com.example.warship.Minesweeper;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;


/**
 * A simple {@link Fragment} subclass.
 */
public class about extends Fragment {


    public about() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setMovementMethod(new ScrollingMovementMethod());

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("About");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        setHasOptionsMenu(true);
        return view;
    }

}
