package com.example.guesstheidol;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Collections;
import java.util.HashSet;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private GridView choiceItems;
    private ArrayAdapter<String> choiceAdapter;
    private String[] idolNames;
    private AssetManager assetManager;
    private SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Set<String> groupNames = preferences.getStringSet("groupsSelection", new HashSet<String>());
        try {
            assetManager =  new AssetManager(getContext(), groupNames);
        } catch (IOException e) {
            Log.e("AssetManager", e.toString());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        choiceItems.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        choiceItems = (GridView) mainFragmentView.findViewById(R.id.choiceItems);
        choiceAdapter = new ArrayAdapter<>(getContext(), R.layout.choice_item);
        choiceItems.setAdapter(choiceAdapter);

        String choiceStr = preferences.getString("choicesSelection", "0");

        int number_of_choices = Integer.parseInt(choiceStr);

        List<String> allIdolNames = assetManager.allIdolsForGroup("ASTRO");
        List<String> idolNames = allIdolNames;
        Collections.shuffle(idolNames);
        idolNames = idolNames.subList(0, number_of_choices);

        choiceAdapter.addAll(idolNames);

        ImageView imageView = (ImageView) mainFragmentView.findViewById(R.id.idolImage);
        imageView.setImageDrawable((assetManager.imageForIdol(idolNames.get(0))));
        return mainFragmentView;
    }
}
