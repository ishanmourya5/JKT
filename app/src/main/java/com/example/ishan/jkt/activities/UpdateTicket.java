package com.example.ishan.jkt.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.ishan.jkt.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateTicket extends AppCompatActivity implements View.OnClickListener{

    EditText subject_et, message_et;
    Button create_button;
    FirebaseDatabase fb_database;
    SharedPreferences shared_pref;
    String key, message, heading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_ticket);

        fb_database = FirebaseDatabase.getInstance();

        subject_et = (EditText)findViewById(R.id.editText3);
        message_et = (EditText)findViewById(R.id.editText4);
        create_button = (Button)findViewById(R.id.button5);
        create_button.setOnClickListener(this);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Update Ticket");
        shared_pref = getSharedPreferences("my_pref", Context.MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();
        key = extras.getString("key",null);
        message = extras.getString("message",null);
        heading = extras.getString("heading",null);

        message_et.setText(message);
        subject_et.setText(heading);
    }

    @Override
    public void onClick(View v) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date_time = df.format(c.getTime());

        DatabaseReference ref = fb_database.getReference().child("tickets").child(key);
        ref.child("message").setValue(message_et.getText().toString());
        ref.child("heading").setValue(subject_et.getText().toString());
        ref.child("update_date_time").setValue(date_time);
        finish();
    }
}
