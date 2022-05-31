package com.example.dndinitiative;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

// Fragment for the dialog box that appears when a new character is being added to the order
public class HpChangeDialogFragment extends AppCompatDialogFragment {
    // EditTexts for the character's name, current hp, max hp, initiative, and ac
    private TextView nameText, hpCurrentText;
    private EditText hpChange;
    private Button minusButton, plusButton;
    private ProgressBar progressBar;

    private int currentHp, maxHp, position;
    private String name;
    private int hpCurrent = 0, hpMax = 0;
    private Character character;
    private DialogListener listener;

    // Dialog for saving a new character
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //get input from bundle
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        currentHp = bundle.getInt("currentHp");
        maxHp = bundle.getInt("maxHp");
        position = bundle.getInt("position");
        double hp = ((double)currentHp) / maxHp;
        int hpPercent = (int)(hp * 100);

        // AlertDialog Builder created
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflater for the Layout and View to be inflated created
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_hp_change, null);

        // Views found
        nameText = view.findViewById(R.id.nameText_hp_change);
        hpCurrentText = view.findViewById(R.id.textHp_hp_change);
        progressBar = view.findViewById(R.id.hpProgressBar_hp_change);
        hpChange = view.findViewById(R.id.hpEditText_hp_change);

        minusButton = view.findViewById(R.id.minusButton);
        View.OnClickListener minusListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int change = Integer.parseInt(hpChange.getText().toString());
                if(currentHp - change >= 0){
                    currentHp -= change;
                    ((MainActivity)getActivity()).updateHp(position, currentHp);
                    dismiss();
                }
                else Toast.makeText(getContext(),"Health cannot be less than 0",Toast.LENGTH_SHORT).show();
            }
        };
        minusButton.setOnClickListener(minusListener);


        plusButton = view.findViewById(R.id.plusButton);
        View.OnClickListener plusListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int change = Integer.parseInt(hpChange.getText().toString());
                if(currentHp + change <= 250){
                    currentHp += change;
                    ((MainActivity)getActivity()).updateHp(position, currentHp);
                    dismiss();
                }
                else Toast.makeText(getContext(),"Health cannot be this high",Toast.LENGTH_SHORT).show();
            }
        };
        plusButton.setOnClickListener(plusListener);

        nameText.setText(name);
        hpCurrentText.setText(currentHp+"/"+maxHp);
        progressBar.setProgress(hpPercent);



        // The builder's view is set using the previous view
        builder.setView(view)
                // This button will cancel the addition
                /*.setNegativeButton("-", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                // This button will send over the character data
                .setPositiveButton("+", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Character information is parsed
                        name = nameText.getText().toString();
                        hpCurrent = Integer.parseInt(hpCurrentText.getText().toString());
                        //hpMax = Integer.parseInt(hpMaxText.getText().toString());

                        // New character is sent to the Activity
                        listener.applyText(character);
                    }
                })*/;

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
