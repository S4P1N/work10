package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    EditText user_name,pass_word;
    Button log_in,show_member;
    int counter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_name = findViewById(R.id.user_name);
        pass_word = findViewById(R.id.pass_word);
        log_in = findViewById(R.id.btn_login);
        show_member = findViewById(R.id.btn_show);

        show_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);

            }
        });


        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                if (user_name.getText().toString().equals("")){

                }
                else {

                    DocumentReference doc_rf1 = db.collection("user").document(user_name.getText().toString());


                    doc_rf1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot documentSnapshot = task.getResult();

                            StringBuilder string_username = new StringBuilder("");
                            string_username.append(documentSnapshot.get("user_name"));

                            StringBuilder string_password = new StringBuilder("");
                            string_password.append(documentSnapshot.get("pass_word"));

                            StringBuilder string_status = new StringBuilder("");
                            string_status.append(documentSnapshot.get("status"));


                            if (user_name.getText().toString().equals(string_username.toString())&&
                                    pass_word.getText().toString().equals(string_password.toString())&&
                                    string_status.toString().equals("on") ){

                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();

                            }
                            else {
                                counter = counter+1;
                                if (counter<5){
                                    Toast.makeText(MainActivity.this, "Login Failed"+ counter, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    db.collection("user").document(user_name.getText().toString()).update("status", "off");
                                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                                    LayoutInflater load_layout = MainActivity.this.getLayoutInflater();

                                    View dailogview = load_layout.inflate(R.layout.layout2,null);
                                    adb.setView(dailogview);

                                    Button button = (Button) dailogview.findViewById(R.id.button);
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            finish();
                                            System.exit(0);

                                        }
                                    });

                                    AlertDialog alertDialog = adb.create();
                                    alertDialog.show();

                                }

                            }

                        }
                    });
                }
            }
        });


    }
}