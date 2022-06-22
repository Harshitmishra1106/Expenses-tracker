package com.example.expensestracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registrationActivity extends AppCompatActivity {

    private EditText email,password;
    private Button registerBtn;
    private TextView registerQn;

    private FirebaseAuth mAuth;
    private android.app.ProgressDialog ProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email =findViewById(R.id.email);
        password =findViewById(R.id.password);
        registerBtn =findViewById(R.id.registerBtn);
        registerQn =findViewById(R.id.registerQn);

        mAuth =FirebaseAuth.getInstance();
        ProgressDialog =new ProgressDialog(this);

        registerQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                if(TextUtils.isEmpty(emailString)){
                    email.setError("Email is required");
                }
                if(TextUtils.isEmpty(passwordString)){
                    password.setError("Password is required");
                }
                else{

                    ProgressDialog.setMessage("Registration in progress");
                    ProgressDialog.setCanceledOnTouchOutside(false);
                    ProgressDialog.show();

                    mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Intent intent = new Intent(registrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                ProgressDialog.dismiss();
                            }else{
                                Toast.makeText(registrationActivity.this,task.getException().toString() , Toast.LENGTH_SHORT).show();
                                ProgressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }
}