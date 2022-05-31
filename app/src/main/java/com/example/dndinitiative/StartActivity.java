package com.example.dndinitiative;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/* This activity is the one that starts off the app and allows the user to open up the main activity and the manage team activity.
* */
public class StartActivity extends AppCompatActivity{

    private Button startButton;
    private Button teamManageButton;
    private View.OnClickListener startClickListener;
    private View.OnClickListener manageTeamListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startButton = findViewById(R.id.buttonStart);
        teamManageButton = findViewById(R.id.buttonManageTeam);

        // Start onClick
        Intent startIntent = new Intent(this, MainActivity.class);
        startClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(startIntent);
            }
        };

        // Manage Team onClick
        Intent manageIntent = new Intent(this, TeamManagerActivity.class);
        manageTeamListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(manageIntent);
            }
        };

        startButton.setOnClickListener(startClickListener);
        teamManageButton.setOnClickListener(manageTeamListener);
    }
}