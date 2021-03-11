package com.example.pro1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ValueEventListener {
    private TextView headingtext;
    private EditText headinginput;
    private RadioButton redb,blueb;
    private FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootreference = firebasedatabase.getReference();
    private DatabaseReference mHeadingReference = mRootreference.child("heading");
    private DatabaseReference mFontColorReference = mRootreference.child("fontcolor");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headinginput = (EditText)findViewById(R.id.headinginput);
        headingtext = (TextView)findViewById(R.id.headingid);
        redb = (RadioButton)findViewById(R.id.redradio);
        blueb = (RadioButton)findViewById(R.id.blueradio);
    }

    public void screen2(View view) {
        Intent a = new Intent(this,MainActivity2.class);
        startActivity(a);
    }

    public void blueradio(View view) {
        mFontColorReference.setValue("blue");
    }

    public void redradio(View view) {
        mFontColorReference.setValue("red");
    }

    public void submitheading(View view) {
        String heading =headinginput.getText().toString();
        mHeadingReference.setValue(heading);
        headinginput.setText("");
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.getValue(String.class)!= null){
            String key = snapshot.getKey();
            if (key.equals("heading")){
                String heading = snapshot.getValue(String.class);
                headingtext.setText(heading);
            }
            else if (key.equals("fontcolor")){
                String color = snapshot.getValue(String.class);
                if(color.equals("red")){
                    headingtext.setTextColor(ContextCompat.getColor(this,R.color.red));
                    redb.setChecked(true);

                }
                else if (color.equals("blue")){
                    headingtext.setTextColor(ContextCompat.getColor(this,R.color.blue));
                    blueb.setChecked(true);
                }
            }
        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
    @Override
    protected void onStart() {

        super.onStart();
        mHeadingReference.addValueEventListener(this);
        mFontColorReference.addValueEventListener(this);
    }
}