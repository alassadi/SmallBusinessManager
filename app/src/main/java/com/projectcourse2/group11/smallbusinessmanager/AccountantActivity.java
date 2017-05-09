package com.projectcourse2.group11.smallbusinessmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AccountantActivity extends AppCompatActivity  {
    private Button button;
    private Button button2;
    private ListView listView;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountant);
        listView=(ListView)findViewById(R.id.listView);
        button =(Button) findViewById(R.id.button);

        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("companyEmployees").child("company1");
        mAdapter=new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_1,
                ref) {
            @Override
            protected String parseSnapshot(DataSnapshot snapshot) {
                return snapshot.child("firstName").getValue(String.class) + " " + snapshot.child("lastName").getValue(String.class);
            }
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView=(TextView)v.findViewById(android.R.id.text1);
                textView.setText(model);
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listView.setAdapter(mAdapter);

            }
        });

    }
}