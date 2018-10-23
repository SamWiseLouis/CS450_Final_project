package edu.stlawu.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View mainView =
                inflater.inflate(R.layout.fragment_main,container,false);

        // button stuff for starting new game and continuing old game
        View new_button = mainView.findViewById(R.id.new_button);
        View continue_button = mainView.findViewById(R.id.continue_button);
        View about_button = mainView.findViewById(R.id.about_button);


        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);

                //update the string saved to see if new game or load old game

            }
        });

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return mainView;
    }
}
