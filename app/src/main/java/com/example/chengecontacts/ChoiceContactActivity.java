package com.example.chengecontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ChoiceContactActivity extends AppCompatActivity {
    ArrayList<Contact> contacts = new ArrayList<>();
    static ArrayList<Contact> contactsArrayList = new ArrayList<>();
    Contact contact1, contact2, contact3, contact4;
    DatabaseReference myRef;
    static Contact contact;
    String phoneString;
    String idString;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_contact);
/*        contact1 = new Contact("Vlad", "111111", R.drawable.image_default, R.drawable.icon_unselected);
        contact2 = new Contact("Vitalick", "222222", R.drawable.image_default, R.drawable.icon_unselected);
        contact3 = new Contact("Albert", "333333", R.drawable.image_default, R.drawable.icon_unselected);
        contact4 = new Contact("Lesha", "444444", R.drawable.image_default, R.drawable.icon_unselected);
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);
        contacts.add(contact4);*/

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for(int i = 0; i < MainActivity.contactsDef.size(); i++) {
            System.out.println("Id:  " + MainActivity.contactsDef.get(i).getId() + " Phone:  " + MainActivity.contactsDef.get(i).getPhone());
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        for (int i = 0; i < MainActivity.contactsDef.size(); i++) {
            if(!MainActivity.contactsDef.get(i).getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(MainActivity.contactsDef.get(i).getId()).child("phone").setValue(MainActivity.contactsDef.get(i).getPhone());
                myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(MainActivity.contactsDef.get(i).getId()).child("id").setValue(MainActivity.contactsDef.get(i).getId());
                myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(MainActivity.contactsDef.get(i).getId()).child("requests").child("sent").setValue("3");
                myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(MainActivity.contactsDef.get(i).getId()).child("requests").child("received").setValue("3");
            }
        }

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dS: dataSnapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").getChildren()){
                            contact = new Contact();
                            idString = dS.child("id").getValue(String.class);
                            phoneString = dS.child("phone").getValue(String.class);
                            contact.setId(idString);
                            contact.setPhone(phoneString);
                            contact.setImageSelect(R.drawable.icon_unselected);
                            contact.setImage(R.drawable.image_default);
                            contact.setCheck(false);

                            for (int i = 0; i < MainActivity.contacts.size(); i++) {
                                //System.out.println("############" + MainActivity.contacts.get(i).getPhone() + "@@@@" +phoneString);
                                if(MainActivity.contacts.get(i).getPhone().equals(phoneString)){
                                    //System.out.println("%%%" + MainActivity.contacts.get(i).getPhone() + "%%%" +phoneString);
                                    contact.setName(MainActivity.contacts.get(i).getName());
                                }
                            }
                            //Если в списке нет контакта с таким id, тогда добавляем его
                            //Если список пуст, добавляем контакт, иначе бежим по списку.
                            if(contacts.size()!=0){
                                int count=0;
                                for (int i = 0; i < contacts.size(); i++) {
                                    if(contacts.get(i).getId().equals(idString)){
                                        count++;
                                    }
                                }
                                if(count==0){
                                    contacts.add(contact);
                                }
                            }else{
                                contacts.add(contact);
                            }


                            //contactsArrayList.add(contact);
                        }

                        //Удалять повторяющиеся контакты на этом этапе

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        RecyclerViewChoiceContactsArrayListAdapter adapter = new RecyclerViewChoiceContactsArrayListAdapter(contacts);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                        recyclerView.setAdapter(adapter);

                        adapter.setListener(new RecyclerViewChoiceContactsArrayListAdapter.Listener() {
                            @Override
                            public void onClick(int position) {
                                //contactsArrayName.add(contacts.get(position).getName());
                            }
                        });
/*                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {



                    }
                }, 200);*/
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        }, 3000);

    }

    public void onClickSend(View view){
        if(contactsArrayList.size()>0){
            for(int i = 0; i < contactsArrayList.size(); i++) {
                System.out.println(contactsArrayList.get(i).getId() + "!!");
                myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(contactsArrayList.get(i).getId()).child("requests").child("sent").setValue("1");
                myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(contactsArrayList.get(i).getId()).child("requests").child("received").setValue("3");
                myRef.child("users").child(contactsArrayList.get(i).getId()).child("contacts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("requests").child("sent").setValue("3");
                myRef.child("users").child(contactsArrayList.get(i).getId()).child("contacts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("requests").child("received").setValue("1");
            }
            MainActivity.contacts.clear();
            MainActivity.contactsDef.clear();
            startActivity(new Intent(ChoiceContactActivity.this, MainActivity.class));
        }

    }

    public void onClickExit(View view){
        mAuth.signOut();
        startActivity(new Intent(ChoiceContactActivity.this,LogInActivity.class));
    }
}