package com.example.guesstheidol;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.io.IOException;
import java.util.List;
import java.util.Random;


public class MainActivityFragment extends Fragment {

    private GridView choiceItems;
    private ArrayAdapter<String> choiceAdapter;
    private AssetManager assetManager;
    private SharedPreferences preferences;

    private SharedPreferences pref;

    private int number_of_choices;
    private int correctIdol;

    private GridView gridView;

    private List<String> allIdolNames;
    private List<String> idolNames;

    private int questionNumber;
    private int score;

    private TextView questionStatus;
    private TextView scoreStatus;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        try {
            assetManager = new AssetManager(getContext());
        } catch (IOException e) {
            Log.e("AssetManager", e.toString());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
//        pref = this.getActivity().getSharedPreferences("value", Context.MODE_PRIVATE);
        choiceItems.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false);


//        questionNumber = preferences.getInt("current question number", 0);
//        score = preferences.getInt("current score number", 0);

        gridView = (GridView) mainFragmentView.findViewById(R.id.choiceItems);
        questionStatus = (TextView)  mainFragmentView.findViewById(R.id.questionStatus);
        scoreStatus = (TextView)  mainFragmentView.findViewById(R.id.scoresStatus);

        choiceItems = (GridView) mainFragmentView.findViewById(R.id.choiceItems);
        choiceAdapter = new ArrayAdapter<>(getContext(), R.layout.choice_item);
        choiceItems.setAdapter(choiceAdapter);

        String choiceStr = preferences.getString("choicesSelection", "0");

        number_of_choices = Integer.parseInt(choiceStr);


        allIdolNames = assetManager.allIdolsForGroup();
        idolNames = allIdolNames;
        Collections.shuffle(idolNames);
        idolNames = idolNames.subList(0, number_of_choices);

        Random rand = new Random();
        correctIdol = rand.nextInt(number_of_choices);


        choiceAdapter.addAll(idolNames);

        ImageView imageView = (ImageView) mainFragmentView.findViewById(R.id.idolImage);
        imageView.setImageDrawable((assetManager.imageForIdol(idolNames.get(correctIdol))));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String selectedChoice = parent.getItemAtPosition(position).toString();

                if(selectedChoice.equals(idolNames.get(correctIdol))){
                    //get item for display before set and sort
                    Toast.makeText(getActivity(),
                            "Correct!", Toast.LENGTH_LONG).show();
                    ++score;
                    ++questionNumber;

                }
                else{
                    Toast.makeText(getActivity(),
                            "Wrong!", Toast.LENGTH_LONG).show();
                    ++questionNumber;
                }

                questionStatus.setText("Question " + questionNumber + " of 10");
                scoreStatus.setText("Score " + score + "/10");

            }
        });

        return mainFragmentView;
    }

}
