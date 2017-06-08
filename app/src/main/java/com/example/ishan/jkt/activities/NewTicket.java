package com.example.ishan.jkt.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.ishan.jkt.R;
import com.example.ishan.jkt.classes.Ticket;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


public class NewTicket extends AppCompatActivity implements View.OnClickListener{

    EditText subject_et, message_et;
    Spinner priority_sp,department_sp;
    Button create_button;
    FirebaseDatabase fb_database;
    SharedPreferences shared_pref;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_ticket);

        fb_database = FirebaseDatabase.getInstance();

        subject_et = (EditText)findViewById(R.id.editText3);
        message_et = (EditText)findViewById(R.id.editText4);
        department_sp = (Spinner)findViewById(R.id.spinner);
        priority_sp = (Spinner)findViewById(R.id.spinner2);
        create_button = (Button)findViewById(R.id.button5);
        create_button.setOnClickListener(this);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Create New Ticket");
        shared_pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        String subject, department, priority, message, date_time, status = "Open", username, userid;
        subject = subject_et.getText().toString();
        message = message_et.getText().toString();
        department = department_sp.getSelectedItem().toString();
        priority = priority_sp.getSelectedItem().toString();
        username = shared_pref.getString("name",null);
        userid = shared_pref.getString("userid",null);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        date_time = df.format(c.getTime());

        Ticket t = new Ticket(subject, priority, status, date_time, message, department, username, userid, date_time);
        DatabaseReference ref = fb_database.getReference().child("tickets");
        ref.push().setValue(t);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
