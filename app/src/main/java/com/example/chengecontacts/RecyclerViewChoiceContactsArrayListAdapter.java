package com.example.chengecontacts;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerViewChoiceContactsArrayListAdapter extends RecyclerView.Adapter<RecyclerViewChoiceContactsArrayListAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;
    private RecyclerViewChoiceContactsArrayListAdapter.Listener listener;
    int count=0;
    boolean check;

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

    public RecyclerViewChoiceContactsArrayListAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public int getItemCount(){
        return contacts.size();
    }
    public void setListener(RecyclerViewChoiceContactsArrayListAdapter.Listener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewChoiceContactsArrayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_choice_contacts_list, parent, false);
        return new RecyclerViewChoiceContactsArrayListAdapter.ViewHolder(cv);
    }

    public void onBindViewHolder(ViewHolder holder, final int position){
        //Работа с массивавми данных

        CardView cardView = holder.cardView;

        ImageView iVImage = (ImageView) cardView.findViewById(R.id.iVImage);
        ImageView iVCheck = (ImageView) cardView.findViewById(R.id.iVCheck);
        TextView tVName = (TextView) cardView.findViewById(R.id.tVNameChoice);
        TextView tViPhone = (TextView) cardView.findViewById(R.id.tVPhone);

        tVName.setText(contacts.get(position).getName());
        tViPhone.setText(contacts.get(position).getPhone());

        iVCheck.setBackgroundResource(contacts.get(position).getImageSelect());

        if(contacts.get(position).getImageBitmap()==null){
            iVImage.setBackgroundResource(contacts.get(position).getImage());
        }else{
            iVImage.setImageBitmap(contacts.get(position).getImageBitmap());
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                if(listener != null){
                    listener.onClick(position);
                }*/
                check = contacts.get(position).isCheck();
                count++;
                if(!check){
                    iVCheck.setBackgroundResource(R.drawable.icon_selected);
                    ChoiceContactActivity.contactsArrayList.add(contacts.get(position));

                    check=true;
                    contacts.get(position).setCheck(true);
                }else{
                    iVCheck.setBackgroundResource(R.drawable.icon_unselected);

                    //Удалить все повторяющиеся имена и все выбранные
                    for (int i = 0; i < ChoiceContactActivity.contactsArrayList.size(); i++) {
                        if (ChoiceContactActivity.contactsArrayList.get(i).getId().equals(contacts.get(position).getId())){
                            ChoiceContactActivity.contactsArrayList.remove(i);
                        }
                    }

                    check=false;
                    contacts.get(position).setCheck(false);
                }
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@" + String.valueOf(count));
            }
        });
    }
}
