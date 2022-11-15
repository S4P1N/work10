package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity2 extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerview);
        Query query = db.collection("user");
        FirestoreRecyclerOptions<Show_member> options = new FirestoreRecyclerOptions.Builder<Show_member>()
                .setQuery(query, Show_member.class) .build();

        adapter = new FirestoreRecyclerAdapter<Show_member, Data_holder>(options) {
            @NonNull
            @Override
            public Data_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_data, parent, false);
                return new Data_holder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Data_holder holder, int position, @NonNull Show_member model) {
                holder.username.setText(model.getUser_name());
                holder.password.setText(model.getPass_word());
                holder.email.setText(model.getEmail());
                holder.lastname.setText(model.getLast_name());
                holder.name.setText(model.getName());
                holder.status.setText(model.getStatus());

            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private class Data_holder extends RecyclerView.ViewHolder {

        TextView username, password, email, lastname, name,status;

        public Data_holder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user_name);
            password = itemView.findViewById(R.id.pass_word);
            email = itemView.findViewById(R.id.email);
            name =  itemView.findViewById(R.id.name);
            lastname = itemView.findViewById(R.id.last_name);
            status = itemView.findViewById(R.id.status);

        }
    }

}