package com.example.chengecontacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerViewRequestsAdapter extends RecyclerView.Adapter<RecyclerViewRequestsAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;

    private RecyclerViewRequestsAdapter.Listener listener;

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

    public RecyclerViewRequestsAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public int getItemCount(){
        return contacts.size();
    }
    public void setListener(RecyclerViewRequestsAdapter.Listener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_requests, parent, false);
        return new RecyclerViewRequestsAdapter.ViewHolder(cv);
    }

    public void onBindViewHolder(ViewHolder holder, final int position){
        //Работа с массивавми данных

        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView) cardView.findViewById(R.id.iVImageRequests);
        TextView textViewName = (TextView) cardView.findViewById(R.id.tVNameRequests);
        TextView textViewPhone = (TextView) cardView.findViewById(R.id.tVPhoneRequests);
        FrameLayout btnDone = (FrameLayout) cardView.findViewById(R.id.btnDone);
        FrameLayout btnClose = (FrameLayout) cardView.findViewById(R.id.btnClose);

        textViewName.setText(contacts.get(position).getName());
        textViewPhone.setText(contacts.get(position).getPhone());

        if(contacts.get(position).getImageBitmap()==null){
            imageView.setBackgroundResource(contacts.get(position).getImage());
        }else{
            imageView.setImageBitmap(contacts.get(position).getImageBitmap());
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Phone: " + contacts.get(position).getPhone() + " DONE");
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Phone: " + contacts.get(position).getPhone() + " CLOSE");
            }
        });
/*        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });*/
    }
}
