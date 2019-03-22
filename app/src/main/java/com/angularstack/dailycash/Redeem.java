package com.angularstack.dailycash;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Redeem extends AppCompatActivity {
    DatabaseReference ref;
    TextView coins;
    int coins_id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        coins=findViewById(R.id.coins_id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String cu = currentFirebaseUser.getUid();
        Log.d("kiduvee", cu);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("users").child(cu);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReadData post = dataSnapshot.getValue(ReadData.class);
                try {
                    coins_id1 = post.reward;
                    coins.setText("Total points you have :"+Integer.toString(coins_id1));

                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
