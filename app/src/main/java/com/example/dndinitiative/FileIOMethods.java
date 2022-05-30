package com.example.dndinitiative;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileIOMethods {
    public static void readFromFile(String fileName, List<Character> list, Context context){
        /*Log.d("characters", "before making character file");
        File characterFile = new File(fileName);
        Log.d("characters", "after making character file");

        if(!characterFile.exists()) {
            try {
                Log.d("characters", "characterFile does not exist");
                // Try to create file if not already existing
                boolean value = characterFile.createNewFile();
                Log.d("characters", "createNewFile: " + value);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("characters", "error " + e);
            }
        }*/
        try {
            String stringToSplit = ""; // data to be split
            String charInfoArray[];// array holding character info

            InputStream is = context.openFileInput(fileName);// inputStream from file
            if(is != null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                while((stringToSplit = reader.readLine()) != null){
                    // Split up string into pieces of information
                    charInfoArray = stringToSplit.split("-+");
                    Character c = new Character(
                            charInfoArray[0],
                            Integer.valueOf(charInfoArray[1]),
                            Integer.valueOf(charInfoArray[2]),
                            Integer.valueOf(charInfoArray[3]),
                            Integer.valueOf(charInfoArray[4]),
                            Integer.valueOf(charInfoArray[5]));
                    list.add(c);
                    Log.d("characters", "ADDED: " + c.toString());
                }
            }
            is.close();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.d("error",""+e);
        }
        Log.d("characters","end of try and catch");
    }

    public static void writeToFile(String fileName, List<Character> list, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            for(Character c: list){
                outputStreamWriter.write(
                c.getName() + "-" +
                    c.getCurrentHp() + "-" +
                    c.getMaxHp() + "-" +
                    c.getInitiative() + "-" +
                    c.getInitiativeBonus() + "-" +
                    c.getAc()+"\n");
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
