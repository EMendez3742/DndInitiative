package com.example.dndinitiative;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/*
* This is a class for the RecyclerView Adapter to display the characters on the screen
* It extends the RecyclerView.Adapter class
* */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<Character> list;
    private LayoutInflater inflator;
    private CharacterClickListener characterClickListener;

    // Pass character list data into constructor
    RecyclerViewAdapter(Context context, List<Character> charList){
        this.inflator = LayoutInflater.from(context);
        this.list = charList;
    }

    // Pass character list data into constructor
    RecyclerViewAdapter(Context context, List<Character> charList, CharacterClickListener characterClickListener){
        this.inflator = LayoutInflater.from(context);
        this.list = charList;
        this.characterClickListener = characterClickListener;
    }

    // ViewHolder is created and inflates the row layout from xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflator.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view, characterClickListener);
    }

    // ViewHolder bind data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        int listNum = list.size();

        if(listNum != 0) {
            Collections.sort(list, new Comparator<Character>() {
                @Override
                public int compare(Character c1, Character c2) {
                    int c1Sum, c2Sum;
                    c1Sum = c1.getInitiative() + c1.getInitiativeBonus();
                    c2Sum = c2.getInitiative() + c2.getInitiativeBonus();

                    if(c1Sum == c2Sum) return 0;
                    else if(c1Sum < c2Sum) return 1;
                    else return -1;
                }
            });

            Character character = list.get(position % listNum);
            holder.nameView.setText(character.getName());

            int currentHp = character.getCurrentHp();
            int maxHp = character.getMaxHp();
            holder.healthView.setText(currentHp + "/" + maxHp);

            int init = character.getInitiative();

            double health = ((double)currentHp) / maxHp;
            int healthPercent = (int) (health * 100);
            System.out.println(character.toString());
            holder.hpProgress.setProgress(healthPercent);

            holder.acView.setText(""+character.getAc());

            //get image for character
            switch(position % 3){
                case 0:
                    holder.characterImageView.setImageResource(R.drawable.chess_knight);
                    break;
                case 1:
                    holder.characterImageView.setImageResource(R.drawable.spartan_helmet);
                    break;
                case 2:
                    holder.characterImageView.setImageResource(R.drawable.swords);
                    break;
            }
        }
        else{
            listNum = 1;
        }
    }

    // total number of rows
    @Override
    public int getItemCount(){
        //return list.size();
        return Integer.MAX_VALUE;
    }

    /*ViewHolder class that extends RecyclerView.ViewHolder
     Stores and recycles views as scrolls off screen */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView characterCardView;
        TextView nameView;
        TextView healthView;
        ProgressBar hpProgress;
        TextView acView;
        ImageView characterImageView;
        CharacterClickListener characterClickListener;

        // ViewHolder Constructor
        ViewHolder(@NonNull View itemView, CharacterClickListener characterClickListener){
            super(itemView);
            nameView = itemView.findViewById(R.id.row_name);
            healthView = itemView.findViewById(R.id.textHp);
            hpProgress = itemView.findViewById(R.id.hpProgressBar);
            acView = itemView.findViewById(R.id.acTextView);
            characterCardView = itemView.findViewById(R.id.characterBlock);
            characterImageView = itemView.findViewById(R.id.charImage);

            this.characterClickListener = characterClickListener;

            itemView.setOnClickListener(this);
        }

        // onClick
        @Override
        public void onClick(View view){
            if(characterClickListener != null) characterClickListener.onCharacterClick(getAdapterPosition());
        }

        View.OnClickListener deleteButtonListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), "deleted", Toast.LENGTH_SHORT).show();

                int position = getAdapterPosition();

                list.remove(position);
                notifyDataSetChanged();
                FileIOMethods.writeToFile("characterList.txt", list, view.getContext());
            }
        };
    }

    // Method for getting data at click
    Character getItem(int id){
        return list.get(id);
    }

    // Allows click events to be caught
    void setClickListener(CharacterClickListener listener){
        this.characterClickListener = listener;
    }

    // Parent activity will implement this method to respond to click events
    public interface CharacterClickListener{
        void onCharacterClick(int position);
    }
}
