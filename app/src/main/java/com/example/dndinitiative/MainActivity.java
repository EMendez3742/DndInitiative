package com.example.dndinitiative;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* Main Activity of the app that contains a recyclerview to see all of the characters
* */
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, AdditionDialogFragment.DialogListener{

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Character> characters;
    private Button backButton;
    private Button nextButton;
    private Button addButton;
    private TextView roundText;
    private Button addHealth;
    private Button takeHealth;
    int roundCount;

    ArrayList<Character> getCharacters(){
        return characters;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The current combat round
        roundCount = 1;

        // Finding the Id of the views
        backButton = findViewById(R.id.button);
        nextButton = findViewById(R.id.button2);
        addButton = findViewById(R.id.buttonAddMain);
        roundText = findViewById(R.id.roundView);


        // Data to populate recyclerView
        characters = new ArrayList<>();

        // Read Data from file
        loadAdapter(R.raw.characters, characters);

        // set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically(){
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(this, characters);
        recyclerView.setAdapter(adapter);


        // previous player onClick
        View.OnClickListener previousPlayerListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Context c = getApplicationContext();
                //Toast t = new Toast(c);
                //Toast.makeText(c, "Back Clicked", Toast.LENGTH_SHORT).show();
                int topOfList = layoutManager.findFirstVisibleItemPosition();

                int position = topOfList%characters.size();
                // When we reach the top of the initiative order
                if(position == (0)){
                    if(roundCount > 1) roundCount--;
                    roundText.setText(""+roundCount);
                    Log.d("Round","First Visible is: "+roundCount);
                }

                layoutManager.scrollToPosition(topOfList-1);
            }
        };

        // next player onClick
        View.OnClickListener nextPlayerListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Context c = getApplicationContext();
                //Toast t = new Toast(c);
                //Toast.makeText(c, "Next Clicked", Toast.LENGTH_SHORT).show();

                // Old implementation, remove first, add last
                /* Character tempCharacter = characters.get(0);
                characters.remove(0);
                characters.add(tempCharacter);
                updateAdapter(adapter);*/

                int currentSpot = layoutManager.findLastCompletelyVisibleItemPosition();
                int topOfList = layoutManager.findFirstVisibleItemPosition();

                int position = topOfList%characters.size();
                // When we reach the top of the initiative order
                if(position == (characters.size()-1)){
                    roundCount++;
                    roundText.setText(""+roundCount);
                    Log.d("Round","First Visible is: "+roundCount);
                }

                layoutManager.scrollToPosition(currentSpot+1);
            }
        };

        //Back and next button onclick set
        backButton.setOnClickListener(previousPlayerListener);
        nextButton.setOnClickListener(nextPlayerListener);

        //Intent addIntent = new Intent(this, AdditionActivity.class);
        View.OnClickListener addCharacterListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //startActivity(addIntent);
                openDialog();
            }
        };

        addButton.setOnClickListener(addCharacterListener);
    }


    // Opens up Dialog Fragment
    public void openDialog(){
        AdditionDialogFragment dialog = new AdditionDialogFragment();
        dialog.show(getSupportFragmentManager(),"example");
    }

    // adding character to "characters" list
    @Override
    public void applyText(Character character) {
        //Context c = getApplicationContext();
        //Toast t = new Toast(c);

        /* String text = "Character is: \n"
                + character.name + "\n"
                + character.currentHp + "\n"
                + character.maxHp + "\n"
                + character.initiative; */
        //addCharacterButton.setText(text);

        // Add character to characters list
        characters.add(character);
        adapter.notifyDataSetChanged();
        //Toast.makeText(c, text, Toast.LENGTH_SHORT).show();

    }


    // Read from file and load adapter
    public void loadAdapter(int rawFile, ArrayList<Character> characters){
        String data = "";
        String temp[] = new String[3];

        StringBuffer sbuffer = new StringBuffer();

        InputStream is = this.getResources().openRawResource(rawFile);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        if(is != null){
            try{
                while((data = reader.readLine()) != null){
                    // Split up string into pieces of information
                    temp = data.split("-+");
                    //sbuffer.append(data + "\n");
                    characters.add(new Character(temp[0], Integer.valueOf(temp[1]), Integer.valueOf(temp[2])));
                }
                //list.add(String.valueOf(sbuffer));
                is.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    // Updating Adapter
    public void updateAdapter(RecyclerViewAdapter adapter){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position){
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}