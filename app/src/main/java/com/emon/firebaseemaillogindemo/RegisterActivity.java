package com.emon.firebaseemaillogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmailEdit, mPasswordEdit;
    private Button mRegister;
    private Button mLogin;
    private TextView gotoLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mEmailEdit = (EditText) findViewById(R.id.email);
        mPasswordEdit = (EditText) findViewById(R.id.password);
        mRegister = (Button) findViewById(R.id.register_btn);
        mLogin = (Button) findViewById(R.id.login_btn);



        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setTitle("Please wait");
                pd.setMessage("Loading....");
                pd.show();


                String email = mEmailEdit.getText().toString().trim();
                String password = mPasswordEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                pd.dismiss();


                            } else {
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

     mLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
             finish();
         }
     });


    }

    public void forgetpass(View view) {
        startActivity(new Intent(RegisterActivity.this, ForgetpassActivity.class));
    }


}
