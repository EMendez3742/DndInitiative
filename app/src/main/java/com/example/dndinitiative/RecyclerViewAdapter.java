package com.example.dndinitiative;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*
* This is a class for the RecyclerView Adapter to display the characters on the screen
* It extends the RecyclerView.Adapter class
* */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<Character> list;
    private LayoutInflater inflator;
    private ItemClickListener clickListener;

    // Pass character list data into constructor
    RecyclerViewAdapter(Context context, List<Character> charList){
        this.inflator = LayoutInflater.from(context);
        this.list = charList;
    }

    // ViewHolder is created and inflates the row layout from xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflator.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // ViewHolder bind data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        int listNum = list.size();

        Character character = list.get(position%listNum);
        holder.textView.setText(character.getName());

        int currentHp = character.getCurrentHp();
        int maxHp = character.getMaxHp();
        holder.healthView.setText(currentHp+"/"+maxHp);

        int init = character.getInitiative();
        String initString = String.valueOf(init);
        holder.initView.setText(initString);

        int healthPercent = (currentHp/maxHp)*100;
        holder.hpProgress.setProgress(healthPercent);
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
        TextView textView;
        TextView healthView;
        TextView initView;
        ProgressBar hpProgress;
        Button deleteButton;

        // ViewHolder Constructor
        ViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.row_name);
            healthView = itemView.findViewById(R.id.textHp);
            initView = itemView.findViewById(R.id.textInitiative);
            hpProgress = itemView.findViewById(R.id.hpProgressBar);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            itemView.setOnClickListener(this);
            deleteButton.setOnClickListener(deleteButtonListener);
        }

        // onClick
        @Override
        public void onClick(View view){
            if(clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }

        void deleteCharacter(){
            //getAdapterPosition()
        }

        View.OnClickListener deleteButtonListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), "deleted", Toast.LENGTH_SHORT).show();

                int position = getAdapterPosition();

                list.remove(position);
                notifyDataSetChanged();

                //Toast.makeText(view.getContext(), n, Toast.LENGTH_SHORT).show();
            }
        };
    }

    // Method for getting data at click
    Character getItem(int id){
        return list.get(id);
    }

    // Allows click events to be caught
    void setClickListener(ItemClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
