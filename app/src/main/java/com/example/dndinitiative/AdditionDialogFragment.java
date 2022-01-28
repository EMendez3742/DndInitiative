package com.example.dndinitiative;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

// Fragment for the dialog box that appears when a new character is being added to the order
public class AdditionDialogFragment extends AppCompatDialogFragment {
    // EditTexts for the character's name, current hp, max hp, initiative, and ac
    private EditText nameText, hpCurrentText, hpMaxText, initiativeText, acText;
    private String name;
    private int hpCurrent = 0, hpMax = 0, ac = 0, initiative = 0;
    private Character newChar;
    private Button saveButton;
    private DialogListener listener;

     /* @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        nameText = findViewById(R.id.nameEditText);

        hpCurrentText = findViewById(R.id.currentHpEditText);

        hpMaxText = findViewById(R.id.maxHpEditText);

        initiativeText = findViewById(R.id.inititativeEditText);
        //acText;

        saveButton = findViewById(R.id.saveButton);

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
    } */

    // Dialog for saving a new character
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // AlertDialog Builder created
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflater for the Layout and View to be inflated created
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_addition, null);

        // The builder's view is set using the previous view
        builder.setView(view)
                .setTitle("Add Character")
                // This button will cancel the addition
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                // This button will send over the character data
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Character information is parsed
                        name = nameText.getText().toString();
                        hpCurrent = Integer.parseInt(hpCurrentText.getText().toString());
                        hpMax = Integer.parseInt(hpMaxText.getText().toString());
                        initiative = Integer.parseInt(initiativeText.getText().toString());
                        //ac = Integer.parseInt(acText.getText().toString());

                        // New character is created with the parsed information
                        newChar = new Character(name, hpCurrent, hpMax, initiative, ac);

                        // New character is sent to the Activity
                        listener.applyText(newChar);
                    }
                });

        nameText = view.findViewById(R.id.nameEditText);
        hpCurrentText = view.findViewById(R.id.currentHpEditText);
        hpMaxText = view.findViewById(R.id.maxHpEditText);
        initiativeText = view.findViewById(R.id.inititativeEditText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+"must implement DialogListener");
        }
    }

    public interface DialogListener{
        void applyText(Character character);
    }
}
