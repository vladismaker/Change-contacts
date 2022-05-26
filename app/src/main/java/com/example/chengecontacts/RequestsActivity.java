package com.example.chengecontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RequestsActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        mAuth = FirebaseAuth.getInstance();

        Contact contact = new Contact();
        contact.setName("Vlad Lebedev");
        contact.setPhone("+79373590701");
        contact.setImage(R.drawable.image_default);

        Contact contact2 = new Contact();
        contact2.setName("Vitalick Lebedev");
        contact2.setPhone("+79370001122");
        contact2.setImage(R.drawable.image_default);

        contacts.add(contact);
        contacts.add(contact2);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_requests);
        RecyclerViewRequestsAdapter adapter = new RecyclerViewRequestsAdapter(contacts);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public void onClickExit(View view){
        mAuth.signOut();
        startActivity(new Intent(RequestsActivity.this,LogInActivity.class));
    }
    public void onClickMain(View view){
        startActivity(new Intent(RequestsActivity.this, MainActivity.class));
    }
}