package com.example.guesstheidol;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class AssetManager {
    private Dictionary<String, String> idolPaths;
    private Dictionary<String, List<String>> groupMembers;
    private Context context;

    public AssetManager(Context context, Set<String> groupNames) throws IOException {
        this.context = context;
        idolPaths = new Hashtable<>();
        groupMembers = new Hashtable<>();

        for (String groupName : groupNames) {
            List<String> idolNames = new Vector<>();
            try {
                String[] localPaths = context.getAssets().list(groupName);
                System.out.println("localPaths: " + Arrays.toString(localPaths));

                for(String localPath : localPaths) {
                    String idolName = localPath
                            .replace("-", " ")
                            .replace(".jpg", "")
                            .replace(".jpeg", "");
                    idolNames.add(idolName);
                    idolPaths.put(idolName, groupName + "/" + localPath);
                }
                groupMembers.put(groupName, idolNames);

            } catch (IOException e){
                Log.e("AssetManager", e.toString());
            }
        }
        System.out.println("groupMembers: " + groupMembers);
    }

    public List<String> allIdolsForGroup (String groupName){
        return groupMembers.get(groupName);
    }

    public Drawable imageForIdol (String idolName){
        try {
            InputStream stream = context.getAssets().open(idolPaths.get(idolName));
            return Drawable.createFromStream(stream, idolName);
        } catch (IOException e) {
            Log.e("AssetManager", e.toString());
        }
        return null;
    }
}
