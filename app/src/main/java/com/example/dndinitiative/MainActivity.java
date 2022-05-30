package com.example.dndinitiative;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/* Main Activity of the app that contains a recyclerview to see all of the characters
* */
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.CharacterClickListener, HpChangeDialogFragment.DialogListener{

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Character> characters;
    private ImageButton backButton, nextButton, homeButton;
    //private Button addButton;
    private TextView roundText;
    //private Button addHealth;
    //private Button takeHealth;
    int roundCount;

    private TextView nameText, hpCurrentText;
    private EditText hpChange;
    private Button minusButton, plusButton;
    private ProgressBar progressBar;

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
        backButton = findViewById(R.id.buttonBack);
        nextButton = findViewById(R.id.buttonNext);
        //addButton = findViewById(R.id.buttonAddMain);
        homeButton = findViewById(R.id.buttonHome);
        roundText = findViewById(R.id.textRoundNum);


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

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position){
                //create smooth scroller
                RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext());

                smoothScroller.setTargetPosition(position);
                layoutManager.startSmoothScroll(smoothScroller);
            }
        };

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(this, characters, this);
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

                int currentSpot = layoutManager.findLastVisibleItemPosition();
                int topOfList = layoutManager.findFirstVisibleItemPosition();

                int position = topOfList%characters.size();
                // When we reach the top of the initiative order
                if(position == (characters.size()-1)){
                    roundCount++;
                    roundText.setText(""+roundCount);
                    Log.d("Round","First Visible is: "+roundCount);
                }

                //layoutManager.scrollToPosition(recyclerView, null, currentSpot+1);
                //smoothScroller.setTargetPosition(currentSpot+1);
                //layoutManager.startSmoothScroll(smoothScroller);
                layoutManager.scrollToPosition(currentSpot+1);
                //recyclerView.smoothScrollToPosition(currentSpot+1);
            }
        };

        // Home button onClick
        View.OnClickListener homeListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        };

        //Back and next button onclick set
        backButton.setOnClickListener(previousPlayerListener);
        nextButton.setOnClickListener(nextPlayerListener);
        homeButton.setOnClickListener(homeListener);

        //Intent addIntent = new Intent(this, AdditionActivity.class);
        View.OnClickListener changeHpListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //startActivity(addIntent);
                //openDialog();
            }
        };

        //addButton.setOnClickListener(addCharacterListener);
    }


    // Opens up Dialog Fragment
    public void openDialog(Character c, int position){
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putInt("currentHp", c.getCurrentHp());
        bundle.putInt("maxHp", c.getMaxHp());
        bundle.putString("name", c.getName());

        //double hp = ((double)currentHp) / maxHp;
        //int hpPercent = (int)(hp * 100);

        HpChangeDialogFragment dialog = new HpChangeDialogFragment();
        dialog.setArguments(bundle);
        //dialog.setContentView(R.layout.fragment_hp_change);

        /*nameText = dialog.findViewById(R.id.nameText_hp_change);
        hpCurrentText = dialog.findViewByIdz(R.id.textHp_hp_change);
        progressBar = dialog.findViewById(R.id.hpProgressBar_hp_change);
        hpChange = dialog.findViewById(R.id.hpEditText_hp_change);
        minusButton = dialog.findViewById(R.id.minusButton);
        plusButton = dialog.findViewById(R.id.plusButton);

        nameText.setText(name);
        hpCurrentText.setText(currentHp+"/"+maxHp);
        progressBar.setProgress(hpPercent);*/

        dialog.show(getSupportFragmentManager(),"changeHp");


    }

    void updateHp(int position, int newHp){
        Character c = characters.get(position);
        c.setCurentHp(newHp);
        adapter.notifyDataSetChanged();
        FileIOMethods.writeToFile("characterList.txt", characters, getApplicationContext());
    }

    // adding character to "characters" list
    @Override
    public void applyText(Character character) {

        // Add character to characters list
        characters.add(character);
        adapter.notifyDataSetChanged();
        FileIOMethods.writeToFile("characterList.txt", characters, getApplicationContext());

    }

    /* On Character Click
     * This is what occurs when a character is clicked in the list
     * */
    @Override
    public void onCharacterClick(int position) {
        Character c = characters.get(position);
        openDialog(c, position);
        //Toast.makeText(this, "Character: "+characters.get(position).name, Toast.LENGTH_SHORT).show();
    }
}