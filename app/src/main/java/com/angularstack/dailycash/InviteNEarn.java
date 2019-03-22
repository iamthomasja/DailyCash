package com.angularstack.dailycash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appolica.flubber.Flubber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InviteNEarn extends AppCompatActivity {
    DatabaseReference ref;
    View view1,view2,view3,view4;
    String value;
    EditText invite_code;
    Button submit;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_nearn);
        view1=findViewById(R.id.imageView3);
        view2=findViewById(R.id.imageView6);
        view3=findViewById(R.id.textView5);
        view4=findViewById(R.id.textView6);
        submit=findViewById(R.id.button2);
        invite_code=findViewById(R.id.invite_code);
        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_UP)
                .repeatCount(0)
                .duration(1000)
                .createFor(view1)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_LEFT)
                .repeatCount(0)
                .duration(1000)
                .createFor(view2)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_UP)
                .repeatCount(0)
                .duration(1000)
                .createFor(view3)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.SQUEEZE_LEFT)
                .repeatCount(0)
                .duration(1000)
                .createFor(view4)
                .start();
        try
        {

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String cu = currentFirebaseUser.getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReference().child("users").child(cu);
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ReadData post = dataSnapshot.getValue(ReadData.class);
                    try {
                        value = post.refferal;
                        username=post.name;
                        Log.d("kiduve", value);
                        invite_code.setText(value);
                        invite_code.setEnabled(false);


                    } catch (Exception e) {

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
        catch(Exception e)
        {

        }
submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey your friend "+username+" Shared Daily Cashy app. Download from this link . Using this app you can earn unlimited money join with us by entering "+value+" refferal code and happy earning!!");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
});

    }
}
