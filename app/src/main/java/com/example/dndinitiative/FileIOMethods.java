package com.example.dndinitiative;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class FileIOMethods {
    public static void readFromFile(String fileName, List<Character> list, Context context){
        String data = ""; // data to be split
        String temp[] = new String[3];// array holding character info

        StringBuffer sbuffer = new StringBuffer();// string buffer


        Log.d("characters", "before");
        File characterFile = new File(fileName);
        Log.d("characters", "after");
        if(!characterFile.exists()) {
            try {
                Log.d("characters", "inside");
                // Try to create file if not already existing
                boolean value = characterFile.createNewFile();
                Log.d("characters", "createNewFile: " + value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            InputStream is = context.openFileInput(fileName);// inputStream from file

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));


            while((data = reader.readLine()) != null){
                // Split up string into pieces of information
                temp = data.split("-+");
                list.add(new Character(temp[0], Integer.valueOf(temp[1]), Integer.valueOf(temp[2])));
            }
            is.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Log.d("characters","end of try and catch");
    }

    public static void writeToFile(String fileName, List<Character> list, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("characterList.txt", Context.MODE_PRIVATE));
            for(Character c: list){
                outputStreamWriter.write(c.name + "-" + c.currentHp + "-" + c.maxHp + "-" + c.initiative + "\n");
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
