package com.example.dndinitiative;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

// Fragment for the dialog box that appears when a new character is being added to the order
public class AdditionDialogFragment extends AppCompatDialogFragment {
    // EditTexts for the character's name, current hp, max hp, initiative, and ac
    private EditText nameText, hpCurrentText, hpMaxText, initiativeText, initiativeBonusText, acText;
    private String name;
    private int hpCurrent = 0, hpMax = 0, ac = 0, initiative = 0, initiativeBonus = 0;
    private Character newChar;
    private DialogListener listener;

    // Dialog for saving a new character
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // AlertDialog Builder created
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflater for the Layout and View to be inflated created
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_addition, null);

        // Views found
        nameText = view.findViewById(R.id.nameEditText);
        hpCurrentText = view.findViewById(R.id.currentHpEditText);
        hpMaxText = view.findViewById(R.id.maxHpEditText);
        initiativeText = view.findViewById(R.id.inititativeEditText);
        initiativeBonusText = view.findViewById(R.id.initBonusEditText);
        acText = view.findViewById(R.id.acEditText);

        // The builder's view is set using the previous view
        builder.setView(view)
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
                        String initBonusText = initiativeBonusText.getText().toString();
                        initiativeBonus = initBonusText.equals("") ? 0 : Integer.parseInt(initBonusText);
                        ac = Integer.parseInt(acText.getText().toString());

                        // New character is created with the parsed information
                        newChar = new Character(name, hpCurrent, hpMax, initiative, initiativeBonus, ac);

                        // New character is sent to the Activity
                        listener.applyText(newChar);
                    }
                });

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
