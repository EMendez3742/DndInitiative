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

public class AdditionActivity extends AppCompatActivity {
    EditText nameText, hpCurrentText, hpMaxText, initiativeText, acText;
    String name;
    int hpCurrent = 0, hpMax = 0, ac = 0, initiative = 0;
    Character newChar;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        nameText = findViewById(R.id.nameEditText);

        hpCurrentText = findViewById(R.id.currentHpEditText);

        hpMaxText = findViewById(R.id.maxHpEditText);

        initiativeText = findViewById(R.id.inititativeEditText);
        //acText;

        newChar = new Character(name, hpMax, initiative);


        View.OnClickListener saveButtonListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                newChar.name = nameText.getText().toString();
                newChar.currentHp = Integer.parseInt(hpCurrentText.getText().toString());
                newChar.maxHp = Integer.parseInt(hpMaxText.getText().toString());
                newChar.initiative = Integer.parseInt(initiativeText.getText().toString());


                //Log.d("HELLO", name);
            }
        };

        saveButton.setOnClickListener(saveButtonListener);
    }
}
