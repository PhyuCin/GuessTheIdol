package com.example.guesstheidol;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class AssetManager {
    private Dictionary<String, String> idolPaths;
    private Dictionary<String, List<String>> groupMembers;

    public AssetManager(Context context, Set<String> groupNames) throws IOException {
        idolPaths = new Hashtable<>();
        groupMembers = new Hashtable<>();

        for (String groupName : groupNames) {
            List<String> idolNames = new Vector<>();
            try {
                String[] localPaths = context.getAssets().list(groupName);
                System.out.println("localPaths: " + Arrays.toString(localPaths));
                for(String localPath : localPaths){
                    String idolName = localPath
                            .split("-")[1]
                            .split("\\.")[0]
                            .replace("-", " ");
                    idolNames.add(idolName);
                }

                groupMembers.put(groupName, idolNames);
                System.out.println("groupMembers: " + groupMembers);

            } catch (IOException e){
                Log.e("AssetManager", e.toString());
            }

        }
    }
}
