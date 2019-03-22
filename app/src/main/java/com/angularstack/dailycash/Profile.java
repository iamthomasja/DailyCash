package com.angularstack.dailycash;

import android.support.annotation.RequiresPermission;
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

public class Profile extends AppCompatActivity {
    DatabaseReference ref;
    String value;
    TextView user,points;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user=findViewById(R.id.user_id);
        email=findViewById(R.id.email_id);
        points=findViewById(R.id.points_id);

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
                    value = post.name;
                    user.setText(value);
                    int coins=post.reward;
                    points.setText(Integer.toString(coins));

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email11 = user.getEmail();
        email.setText(email11);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
