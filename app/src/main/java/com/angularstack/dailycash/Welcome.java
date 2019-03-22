package com.angularstack.dailycash;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome extends AppCompatActivity {
    CountDownTimer timer;
    View view;
TextView reward;
    String currentuser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mref = database.getInstance().getReference();
int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        reward=findViewById(R.id.reward);
        view=findViewById(R.id.imageView2);
        Flubber.with()
                .animation(Flubber.AnimationPreset.SHAKE)
                .repeatCount(0)
                .duration(1000)
                .createFor(view)
                .start();
        try {
            currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d("vaayoo",currentuser);
        }
        catch(Exception e)
        {
            Toast.makeText(Welcome.this, "No user dectected restart needed", Toast.LENGTH_SHORT).show();
        }
        timer = new CountDownTimer(3000, 1)
        {
            public void onTick(long millisUntilFinished)
            {
                count++;
reward.setText("You Earned "+count);

            }

            public void onFinish()
            {
               Intent myIntent = new Intent(Welcome.this, HomeActivity.class);
              Welcome.this.startActivity(myIntent);
                mref.child("users").child(currentuser).child("reward").setValue(count);
            }
        };
        timer.start();


    }
}
