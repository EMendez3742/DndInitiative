package com.example.dndinitiative;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/*This activity allows the user to manage the team
* */
public class TeamManagerActivity extends AppCompatActivity implements AdditionDialogFragment.DialogListener {
    private final String CHARACTER_FILE = "characterList.txt";
    private Button addCharacterButton;
    private ImageButton homeButton;
    private RecyclerViewAdapter adapter;
    private ArrayList<Character> characters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager);

        addCharacterButton = findViewById(R.id.buttonAdd);
        homeButton = findViewById(R.id.buttonHome);

        // add character onClick
        View.OnClickListener addCharacterListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openDialog();
            }
        };

        // Home button onClick
        View.OnClickListener homeListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        };

        addCharacterButton.setOnClickListener(addCharacterListener);
        homeButton.setOnClickListener(homeListener);

        // data to populate recyclerView
        characters = new ArrayList<>();

        //read Data from file
        FileIOMethods.readFromFile("characterList.txt", characters, getApplicationContext());
        Log.d("characters", "characterList read: " + characters.toString());

        // set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, characters){
            @Override
            public int getItemCount(){
                return characters.size();
            }
        };
        recyclerView.setAdapter(adapter);

        // Creating a method for item touch helper
        // this is for swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Character deletedCharacter = characters.get(position);

                characters.remove(position);

                adapter.notifyItemRemoved(position);


                FileIOMethods.writeToFile(CHARACTER_FILE, characters, getApplicationContext());

                // Snackbar to ask if user wants to undo
                Snackbar.make(recyclerView, deletedCharacter.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        characters.add(position, deletedCharacter);
                        adapter.notifyItemInserted(position);
                        FileIOMethods.writeToFile(CHARACTER_FILE, characters, getApplicationContext());
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
    // Opens up Dialog Fragment
    public void openDialog(){
        AdditionDialogFragment dialog = new AdditionDialogFragment();
        dialog.show(getSupportFragmentManager(),"addChar");
    }

    @Override
    public void applyText(Character character) {

        // Add character to characters list
        characters.add(character);
        adapter.notifyDataSetChanged();
        FileIOMethods.writeToFile(CHARACTER_FILE, characters, getApplicationContext());
    }
}
