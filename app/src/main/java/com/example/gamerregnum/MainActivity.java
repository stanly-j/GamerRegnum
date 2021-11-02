package com.example.gamerregnum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText Email;
    private EditText Pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = findViewById(R.id.TextEmail);
        Pass = findViewById(R.id.TextPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null){
            currentUser.getUid();
        }
    }

    public void BTNLogin(View view)
    {
        final String email = Email.getText().toString();
        final String password = Pass.getText().toString();
        if (Email.getText().toString().isEmpty() || Pass.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Hay campos vacios",Toast.LENGTH_SHORT).show();
        }
        if (!Email.getText().toString().isEmpty() && !Pass.getText().toString().isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent Main = new Intent (MainActivity.this, PagePrimary.class);
                                Toast.makeText(MainActivity.this, "successful authentication",Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                String UID =  user.getUid();
                                String Name = user.getDisplayName();
                                String Correo = user.getEmail();
                                SharedPreferences preferences10 = getSharedPreferences("ID", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences10.edit();
                                editor.putString("ID2", UID);
                                editor.apply();
                                SharedPreferences preferences11 = getSharedPreferences("Name", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = preferences11.edit();
                                editor2.putString("NameID", Name);
                                editor2.apply();
                                SharedPreferences preferences12 = getSharedPreferences("Correo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor3 = preferences12.edit();
                                editor3.putString("CorreoID", Correo);
                                editor3.apply();
                                startActivity(Main);
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }

    }

    public void Forgot(View view)
    {
        Intent forget = new Intent (this, ForgetAccount.class);
        startActivity(forget);
    }

    public void BTNSignup(View view)
    {
        Intent Main = new Intent (this, Signup.class);
        startActivity(Main);
    }


}