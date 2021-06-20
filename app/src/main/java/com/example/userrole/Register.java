package com.example.userrole;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText fullName,email,password,phone;
    Button registerBtn,goToLogin;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        fullName = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        phone = findViewById(R.id.registerPhone);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.gotoLogin);





        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName=fullName.getText().toString().trim();
                String eMail=email.getText().toString().trim();
                String pAss=password.getText().toString().trim();
                String pHone=phone.getText().toString().trim();

                checked(fullName);
                checked(email);
                checked(password);
                checked(phone);

                if(valid)
                {
                    fAuth.createUserWithEmailAndPassword(eMail,pAss).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user=fAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                            DocumentReference df=fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo=new HashMap<>();
                            userInfo.put("FullName",fName);
                            userInfo.put("Email",eMail);
                            userInfo.put("Password",pAss);
                            userInfo.put("Phone",pHone);

                            //Specify if user is admin

                            userInfo.put("isUser","1");
                            df.set(userInfo);


                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "Fail to Create Account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });



    }

    public boolean checked(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Error");
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }

}