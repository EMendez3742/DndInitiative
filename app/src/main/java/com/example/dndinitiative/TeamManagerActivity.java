package com.example.dndinitiative;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*This activity allows the user to manage the team
* */
public class TeamManagerActivity extends AppCompatActivity implements AdditionDialogFragment.DialogListener {
    private Button addCharacterButton;
    private RecyclerViewAdapter adapter;
    private ArrayList<Character> characters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager);

        addCharacterButton = findViewById(R.id.buttonAdd);

        // add character onClick
        View.OnClickListener addCharacterListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //startActivity(addIntent);
                openDialog();
            }
        };

        addCharacterButton.setOnClickListener(addCharacterListener);

        // data to populate recyclerView
        characters = new ArrayList<>();

        //read Data from file
        FileIOMethods.readFromFile("characterList.txt", characters, getApplicationContext());

        // set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerManageView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, characters){
            @Override
            public int getItemCount(){
                return characters.size();
            }
        };
        recyclerView.setAdapter(adapter);

    }
    // Opens up Dialog Fragment
    public void openDialog(){
        AdditionDialogFragment dialog = new AdditionDialogFragment();
        dialog.show(getSupportFragmentManager(),"example");
    }

    @Override
    public void applyText(Character character) {

        // Add character to characters list
        characters.add(character);
        adapter.notifyDataSetChanged();
        FileIOMethods.writeToFile("characterList.txt", characters, getApplicationContext());
    }
}
