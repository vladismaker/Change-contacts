package com.example.chengecontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] namesArray = {"Name1", "Name 2", "Name3", "Name4", "Name5", "Name6"};
    String[] worksArray = {"Works1", "Works2", "Works3", "Works4", "Works5", "Works6"};
    //String[] imagesArray = {"https://r4.wallpaperbetter.com/wallpaper/1008/573/262/the-sky-clouds-trees-landscape-wallpaper-dcb4070adceb26342be4e9ebf685ac19.jpg", "https://cdn-icons.flaticon.com/png/512/3006/premium/3006872.png?token=exp=1648918373~hmac=76f97a4524824a9368a4f2257618c4e5", "https://cdn-icons.flaticon.com/png/512/145/premium/145861.png?token=exp=1648918373~hmac=5e884e82b62285e6c1364902c21340e4", "https://cdn-icons.flaticon.com/png/512/805/premium/805370.png?token=exp=1648918373~hmac=7de2d6eada47e7da9cdbdb302e1a3260"};
    //int[] imagesArray = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon4, R.drawable.icon4};
    //int[] imagesArray = {R.drawable.icon_default1, R.drawable.icon_default1, R.drawable.icon_default1, R.drawable.icon_default1, R.drawable.icon_default1, R.drawable.icon_default1};

    ArrayList<String> stringNames = new ArrayList<>();
    ArrayList<String> stringWorks = new ArrayList<>();
    ArrayList<String> stringImages = new ArrayList<>();
    ArrayList<String> stringId = new ArrayList<>();
    static ArrayList<Contact> contacts = new ArrayList<>();
    static ArrayList<Contact> contactsDef = new ArrayList<>();
    DatabaseReference referenceUsers;
    static int read=0;


    //
    private final String   TAG = "CONTACT";
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 10;
    //
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        //

        System.out.println("MAIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MAIN");
        for(int i = 0; i < contactsDef.size(); i++) {
            System.out.println("Id:  " + contactsDef.get(i).getId() + " Phone:  " + MainActivity.contactsDef.get(i).getPhone());
        }
        System.out.println("MAIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MAIN");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (DataSnapshot dS: dataSnapshot.child("users").getChildren()){
                            String phoneString = dS.child("information").child("phone").getValue(String.class);
                            String idString = dS.child("id").getValue(String.class);

                            for (int i = 0; i < contacts.size(); i++) {
                                //System.out.println(contacts.get(i).getPhone() + " !!!!!" + phoneString);
                                //System.out.println(" !!!!!???????????" + idString);
                                //Если номер контакта из телефона совпадает с номером пользователся из БД,
                                //добавляем его в список людей, которых нужно добавить в мои контакты(ContactsDef)
                                if(contacts.get(i).getPhone().equals(phoneString)){
                                    //Если в ContactsDef нет контакта с таким ID, тогда добавляем  список
                                    //Пробегаемся по ContactsDef, сравниваем id и добавляем если такого нет
                                    System.out.println("%%%%%% CONTACTSDEF SIZE " + contactsDef.size());
                                    if(contactsDef.size()==0){
                                        Contact contactDef = new Contact();
                                        contactDef.setId(idString);
                                        contactDef.setPhone(phoneString);
                                        contactsDef.add(contactDef);
                                        System.out.println("МАССИВ НУЛЕВОЙ МАССИВ НУЛЕВОЙ МАССИВ НУЛЕВОЙ");
                                    }else{
                                        for (int j = 0; j < contactsDef.size(); j++) {
                                            if(!contactsDef.get(j).getId().equals(idString)){
                                                Contact contactDef = new Contact();
                                                contactDef.setId(idString);
                                                contactDef.setPhone(phoneString);
                                                contactsDef.add(contactDef);
                                                System.out.println("НЕТ ОШИБКИ НЕТ ОШИБКИ НЕТ ОШИБКИ НЕТ ОШИБКИ НЕТ ОШИБКИ НЕТ ОШИБКИ");
                                            }
                                        }
                                    }
                                }
                            }
                            //FirebaseAuth.getInstance().getCurrentUser().getUid()
                            //myRef.child("users").child(idString).child("contacts").child("phone").setValue("89370001122");
                            //String myRequestString = dS.child("information").child("my request").getValue(String.class);
                            //String yourRequestString = dS.child("information").child("your request").getValue(String.class);
                            stringId.add(phoneString);
                        }
                        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
/*                        for(int i = 0; i < stringId.size(); i++) {
                            System.out.println(stringId.get(i));
                        }*/
                    }
                }, 1000);
                //String myLoginFromDB = dataSnapshot.child("Users").child(myId).child("login").getValue(String.class);



/*                String value = dataSnapshot.child("Users").getValue(String.class);
                Log.d(TAG, "Value is: " + value);*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

/*        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Добавление в список моих контактов в БД
                System.out.println("^^^^^^^^^^^^^^^^" + contactsDef.size());
*//*                for(int i = 0; i < contactsDef.size(); i++) {
                    System.out.println(contactsDef.get(i).getName());
                }*//*
                for (int i = 0; i < contactsDef.size(); i++) {
                    if(!contactsDef.get(i).getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(contactsDef.get(i).getId()).child("phone").setValue(contactsDef.get(i).getPhone());
                        myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(contactsDef.get(i).getId()).child("id").setValue(contactsDef.get(i).getId());
                        if(read==0){
                            myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(contactsDef.get(i).getId()).child("requests").child("sent").setValue("3");
                            myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("contacts").child(contactsDef.get(i).getId()).child("requests").child("received").setValue("3");
                            read=1;
                        }
                     }
                }
                contactsDef.clear();
            }
        }, 2000);*/

        // Проверка разрешения
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            // Разрешения чтения контактов имеются
            Log.d(TAG, "Permission is granted");
            readContacts(this);
        } else {
            // Разрешений нет
            Log.d(TAG, "Permission is not granted");

            // Запрос разрешений
            Log.d(TAG, "Request permissions");

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission
                                    .READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        }

    }

    public void onClickSubmit(View view){
        startActivity(new Intent(MainActivity.this, ChoiceContactActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // При отмене запроса массив результатов пустой
                if ((grantResults.length > 0) &&
                        (grantResults[0] ==
                                PackageManager.PERMISSION_GRANTED)) {
                    // Разрешения получены
                    Log.d(TAG, "Permission was granted!");

                    // Чтение контактов
                    readContacts(this);
                } else {
                    // Разрешения НЕ получены.
                    Log.d(TAG, "Permission denied!");
                }
                return;
            }
            // другой 'case' получения permissions
        }
    }

    private void readContacts(Context context)
    {
        Contact contact;
        Cursor cursor=context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                contact = new Contact();
                @SuppressLint("Range") String id = cursor.getString(
                        cursor.getColumnIndex(
                                ContactsContract.Contacts._ID));
                contact.setId(id);

                @SuppressLint("Range") String name = cursor.getString(
                        cursor.getColumnIndex(
                                ContactsContract.Contacts
                                        .DISPLAY_NAME));
                contact.setName(name);

                @SuppressLint("Range") String has_phone = cursor.getString(
                        cursor.getColumnIndex(
                                ContactsContract.Contacts
                                        .HAS_PHONE_NUMBER));

                if (Integer.parseInt(has_phone) > 0) {
                    // extract phone number
                    Cursor pCur;
                    pCur = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds
                                    .Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds
                                    .Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    while(pCur.moveToNext()) {
                        @SuppressLint("Range") String phone = pCur.getString(
                                pCur.getColumnIndex(
                                        ContactsContract.
                                                CommonDataKinds.
                                                Phone.NUMBER));
                        contact.setPhone(phone);
                    }
                    pCur.close();
                    contact.setImage(R.drawable.image_default);
                }
                Log.d(TAG, "Contact PHOTO_THUMBNAIL_URI="  + contact.getPhone());

                System.out.println(String.valueOf(openPhoto(Integer.parseInt(contact.getId()))));
                contact.setImageBitmap(openPhoto(Integer.parseInt(contact.getId())));

                //
                //FirebaseUser currentUser = mAuth.getUid("hOtPJ95zbtRtGBl8yNAJjf14pNx2");
                //

                contacts.add(contact);

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_him);
                RecyclerViewContactsArrayListAdapter adapter = new RecyclerViewContactsArrayListAdapter(contacts);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
            }
        } 
    }

    public Bitmap openPhoto(int contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContentResolver().query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    return bitmap;
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    public void onClickExit(View view){
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this,LogInActivity.class));
    }

    public void onClickRequests(View view){
        startActivity(new Intent(MainActivity.this, RequestsActivity.class));
    }
}