package com.example.dndinitiative;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        FileIOMethods.readFromFile("characterList.txt", characters, getApplicationContext());

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

        // Add character to characters list
        characters.add(character);
        adapter.notifyDataSetChanged();
        FileIOMethods.writeToFile("characterList.txt", characters, getApplicationContext());

    }

    @Override
    public void onItemClick(View view, int position){
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}