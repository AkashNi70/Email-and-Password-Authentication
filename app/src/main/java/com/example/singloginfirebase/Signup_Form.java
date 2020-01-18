package com.example.singloginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Form extends AppCompatActivity {
    EditText txt_fullName,txt_username,txt_email,txt_password,txt_confirmpassword;
    Button btn_register;
    String gender = "";
    RadioButton radioGenderMale,radioGenderFemale;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuthe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__form);
        getSupportActionBar().setTitle("Sign Up Form");

        txt_fullName = (EditText)findViewById(R.id.txt_full_name);
        txt_username = (EditText)findViewById(R.id.txt_user_name);
        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_password = (EditText)findViewById(R.id.txt_password);
        txt_confirmpassword = (EditText)findViewById(R.id.txt_cpassword);
        btn_register = (Button)findViewById(R.id.btn_register1);
        radioGenderMale = (RadioButton)findViewById(R.id.radio_male);
        radioGenderFemale = (RadioButton)findViewById(R.id.radio_female);

        databaseReference = FirebaseDatabase.getInstance().getReference("Friends");
        firebaseAuthe = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullName = txt_fullName.getText().toString().trim();
                final String username = txt_username.getText().toString().trim();
                final String email = txt_email.getText().toString().trim();
                String password = txt_password.getText().toString().trim();
                String cpassword = txt_confirmpassword.getText().toString().trim();

                if(radioGenderMale.isChecked()){
                    gender="Male";
                }
                if(radioGenderFemale.isChecked())
                {
                    gender = "Female";
                }

                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(Signup_Form.this, "Please enter fullname", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(Signup_Form.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Signup_Form.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Signup_Form.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(cpassword)){
                    Toast.makeText(Signup_Form.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<6)
                {
                    Toast.makeText(Signup_Form.this, "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals(cpassword)) {
                    firebaseAuthe.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Signup_Form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        student information = new student(
                                                fullName, username, email, gender
                                        );


                                        FirebaseDatabase.getInstance().getReference("Friends")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Signup_Form.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                                txt_fullName.setText("");
                                                txt_username.setText("");
                                                txt_email.setText("");
                                                txt_password.setText("");
                                                txt_confirmpassword.setText("");
                                            }
                                        });

                                    } else {

                                    }
                                }
                            });
                }
            }
        });

    }
}
