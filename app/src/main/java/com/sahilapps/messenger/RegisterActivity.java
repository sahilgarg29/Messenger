package com.sahilapps.messenger;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button mLoginActivityButton;
    private Button mRegisterButton;
    private TextInputEditText mUserNameEditText;
    private TextInputEditText mUserPasswordEditText;
    private TextInputEditText mUserEmailEditText;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mLoginActivityButton = findViewById(R.id.login_activity_button);
        mRegisterButton = findViewById(R.id.login_button);
        mUserNameEditText = findViewById(R.id.name_register);
        mUserPasswordEditText = findViewById(R.id.password_register);
        mUserEmailEditText = findViewById(R.id.email_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mLoginActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = mUserNameEditText.getText().toString();
                String userEmail = mUserEmailEditText.getText().toString();
                String userPassword = mUserPasswordEditText.getText().toString();

                if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword)){
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String user_id = firebaseAuth.getCurrentUser().getUid();

                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("user_name", userName);

                                firebaseFirestore.collection("users").document(user_id).set(userMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent mainActivityIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(mainActivityIntent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "failed to register", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });
    }
}
