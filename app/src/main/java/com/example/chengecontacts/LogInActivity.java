package com.example.chengecontacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LogInActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    DatabaseReference users;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText edTxtEmail;
    private EditText edTxtPhone;
    private EditText edTxtPassword;
    private EditText edTxtRepeatPassword;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View register_window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("users");
    }

    public void init(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
/*        edTxtPhone = register_window.findViewById(R.id.edTxtPhone);
        edTxtEmail = register_window.findViewById(R.id.edTxtEmail);
        edTxtPassword = register_window.findViewById(R.id.edTxtPassword);
        edTxtRepeatPassword = register_window.findViewById(R.id.edTxtRepeatPassword);*/
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            Toast.makeText(this, "Пользователь вошел", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Пользователь не вошел", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkCorrectInfo(EditText email, EditText login, EditText password, EditText rePassword){
        int check = 0;

        if (TextUtils.isEmpty(login.getText().toString())){
            Toast.makeText(getApplicationContext(), "Введите логин!", Toast.LENGTH_SHORT).show();
        }else {
            check++;
        }

        if (TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(getApplicationContext(), "Введите почту!", Toast.LENGTH_SHORT).show();
        }else {
            check ++;
        }

        if (password.getText().toString().length() < 5){
            Toast.makeText(getApplicationContext(), "Введите пароль, который более 5 символов", Toast.LENGTH_SHORT).show();
        }else {
            check++;
        }

        if (!password.getText().toString().equals(rePassword.getText().toString())){
            Toast.makeText(getApplicationContext(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
        }else {
            check ++;
        }

        if (check == 4){
            return true;
        }else {
            return false;
        }
    }

    public void onClickLogIn(View view){
        if (TextUtils.isEmpty(editTextEmail.getText().toString())){
            Toast.makeText(getApplicationContext(), "Введите почту!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editTextPassword.getText().toString().length() < 5){
            Toast.makeText(getApplicationContext(), "Введите пароль, который более 5 символов", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Пользователя с таким Email нет", Toast.LENGTH_SHORT).show();
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nОшибка входа!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            }
        });

/*        //startActivity(new Intent(this, MainActivity.class));
        String numberPhone = editTextPhone.getText().toString().trim();

        if (numberPhone.isEmpty() || numberPhone.length() < 12) {
            editTextPhone.setError("Enter a valid mobile");
            editTextPhone.requestFocus();
            return;
        }

        Intent intent = new Intent(LogInActivity.this, CreateActivity.class);
        intent.putExtra("number_phone", numberPhone);
        startActivity(intent);*/
    }

    public void onClickCreateAccount(View view){
        dialog = new AlertDialog.Builder(this, R.style.myFullscreenAlertDialogStyle);

        inflater = LayoutInflater.from(this);
        register_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(register_window);
        dialog.show();
    }

    public void onClickContinue(View view) {
        edTxtPhone = register_window.findViewById(R.id.edTxtPhone);
        edTxtEmail = register_window.findViewById(R.id.edTxtEmail);
        edTxtPassword = register_window.findViewById(R.id.edTxtPassword);
        edTxtRepeatPassword = register_window.findViewById(R.id.edTxtRepeatPassword);

        if(checkCorrectInfo(edTxtPhone, edTxtEmail, edTxtPassword, edTxtRepeatPassword)){
            //Регистрация пользователя
            mAuth.createUserWithEmailAndPassword(edTxtEmail.getText().toString(), edTxtPassword.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            User user = new User();
                            user.setPhone(edTxtPhone.getText().toString());
                            user.setEmail(edTxtEmail.getText().toString());
                            //user.setPassword(edTxtPassword.getText().toString());

                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("information")
                                    .setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "Пользователь добавлен!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("requests").child("sent").setValue("3");
                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("requests").child("received").setValue("3");
                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("id").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Пользователь с такой почтой уже существует!", Toast.LENGTH_SHORT).show();
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nОшибка регистрации!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    System.out.println("\nОшибка регистрации!\n" + e.toString());
                }
            });
        }
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}