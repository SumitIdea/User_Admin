package com.example.userrole;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.core.View;

public class Admin extends AppCompatActivity {

    TextView textView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        textView=findViewById(R.id.adminview);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String user=firebaseUser.getEmail();
        textView.setText("Welcome "+user);
    }


    public void logoutAdmin(android.view.View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Admin.this,Login.class));
        finish();
    }
}