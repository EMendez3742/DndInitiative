package com.example.dndinitiative;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        //Intent addIntent = new Intent(getApplicationContext(), AdditionActivity.class);
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
        loadAdapter(R.raw.characters, characters);

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
        //Context c = getApplicationContext();
        //Toast t = new Toast(c);

        /*String text = "Character is: \n"
                + character.name + "\n"
                + character.currentHp + "\n"
                + character.maxHp + "\n"
                + character.initiative;*/
        //addCharacterButton.setText(text);

        characters.add(character);
        adapter.notifyDataSetChanged();
        //Toast.makeText(c, text, Toast.LENGTH_SHORT).show();

    }

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
                    //names.add(temp[0]);
                    //hp.add(temp[1]);
                    //initiative.add(temp[2]);
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
}
