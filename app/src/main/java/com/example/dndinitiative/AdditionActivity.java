package com.example.dndinitiative;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/*  Activity for the addition of a new character to the initiative order
*
public class AdditionActivity extends AppCompatActivity {
    // EditTexts for new character's information
    EditText nameText, hpCurrentText, hpMaxText, initiativeText, acText;
    String name;
    int hpCurrent = 0, hpMax = 0, ac = 0, initiative = 0;
    Character newChar;
    Button saveButton;

    // onCreate method for the AdditionActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        // Finding the View Id's for the name, current hp, max hp, and initiative
        nameText = findViewById(R.id.nameEditText);
        hpCurrentText = findViewById(R.id.currentHpEditText);
        hpMaxText = findViewById(R.id.maxHpEditText);
        initiativeText = findViewById(R.id.inititativeEditText);


        // Creating a new character with blank information
        newChar = new Character(name, hpMax, initiative);

        // onClickListener for when the save button is pressed in order to save a character to the list.
        View.OnClickListener saveButtonListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Setting the new character's name, current hp, max hp, and initiative
                newChar.name = nameText.getText().toString();
                newChar.currentHp = Integer.parseInt(hpCurrentText.getText().toString());
                newChar.maxHp = Integer.parseInt(hpMaxText.getText().toString());
                newChar.initiative = Integer.parseInt(initiativeText.getText().toString());

                //Log.d("HELLO", name);
            }
        };

        // saveButtonListener is set for the save button
        saveButton.setOnClickListener(saveButtonListener);
    }
}*/
