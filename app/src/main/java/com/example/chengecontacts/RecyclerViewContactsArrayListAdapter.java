package com.example.chengecontacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerViewContactsArrayListAdapter extends RecyclerView.Adapter<RecyclerViewContactsArrayListAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;

    private RecyclerViewContactsArrayListAdapter.Listener listener;

    interface Listener{
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    public RecyclerViewContactsArrayListAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public int getItemCount(){
        return contacts.size();
    }
    public void setListener(RecyclerViewContactsArrayListAdapter.Listener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewContactsArrayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_contacts_list, parent, false);
        return new RecyclerViewContactsArrayListAdapter.ViewHolder(cv);
    }

    public void onBindViewHolder(ViewHolder holder, final int position){
        //Работа с массивавми данных

        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView) cardView.findViewById(R.id.imageViewId);
        TextView textViewName = (TextView) cardView.findViewById(R.id.textViewName);
        TextView textViewWorks = (TextView) cardView.findViewById(R.id.textViewWorks);

        textViewName.setText(contacts.get(position).getName());
        textViewWorks.setText(contacts.get(position).getPhone());

        if(contacts.get(position).getImageBitmap()==null){
            imageView.setBackgroundResource(contacts.get(position).getImage());
        }else{
            imageView.setImageBitmap(contacts.get(position).getImageBitmap());
        }


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });
    }
}
