package com.angularstack.dailycash;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import am.appwise.components.ni.NoInternetDialog;

public class MainActivity extends AppCompatActivity {
    private static final int Value = 1;
    Boolean mExitOnBackPress=true;
    private EditText name_id;
    private Button submit_id;
    String uid;
    String name;
    Intent i= getIntent();
    String phone_number;
    String email;
    EditText phone_id;
    EditText email_id;
    String refferal;
    View v1,v2,v3,v4,v5;
    int index;
    String phonpattern="^\\+[0-9]{10,13}$";
    Pattern pattern;
    Matcher matcher;
    ProgressDialog progress;
    String currentuser;
    String name11,email11,phone11;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mref = database.getInstance().getReference();
    private Context context=MainActivity.this;
    NoInternetDialog noInternetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name11 = user.getDisplayName();
            email11 = user.getEmail();
            String uid11 = user.getUid();
           phone11 = user.getPhoneNumber();
        } else {
// No user is signed in.
        }
        try {
            currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d("vaayoo",currentuser);
        }
        catch(Exception e)
        {
            Toast.makeText(context, "No user dectected restart needed", Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_main);
        v1=findViewById(R.id.name_id);
        v2=findViewById(R.id.phone_id);
        v3=findViewById(R.id.email_id);
        v4=findViewById(R.id.submit_id);
        v5=findViewById(R.id.refferal_id);

try {

    Flubber.with()
            .animation(Flubber.AnimationPreset.SLIDE_UP)
            .repeatCount(0)
            .duration(1000)
            .createFor(v1)
            .start();
    Flubber.with()
            .animation(Flubber.AnimationPreset.SLIDE_UP)
            .repeatCount(0)
            .duration(1000)
            .createFor(v2)
            .start();
    Flubber.with()
            .animation(Flubber.AnimationPreset.SLIDE_UP)
            .repeatCount(0)
            .duration(1000)
            .createFor(v3)
            .start();
    Flubber.with()
            .animation(Flubber.AnimationPreset.SLIDE_UP)
            .repeatCount(0)
            .duration(1000)
            .createFor(v4)
            .start();
    Flubber.with()
            .animation(Flubber.AnimationPreset.SLIDE_UP)
            .repeatCount(0)
            .duration(1000)
            .createFor(v5)
            .start();
}
catch (Exception e)
{

}

        try {
            noInternetDialog = new NoInternetDialog.Builder(context).build();
        }
        catch(Exception e)
        {

        }
        submit_id=findViewById(R.id.submit_id);
        name_id=findViewById(R.id.name_id);
        phone_id=findViewById(R.id.phone_id);
        email_id=findViewById(R.id.email_id);
        if(!name11.equals("")) {
            name_id.setText(name11);
            email_id.setText(email11);
            phone_id.setText(phone11);
            name_id.setEnabled(false);
            email_id.setEnabled(false);
        }
        else
        {
            phone_id.setText(phone11);
            phone_id.setEnabled(false);
        }
        submit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try {
    final EditText emailValidate = (EditText) findViewById(R.id.email_id);
    final EditText phoneValidate = findViewById(R.id.phone_id);
    final EditText nameValidate = findViewById(R.id.name_id);
    String email = emailValidate.getText().toString().trim();
    index = email.indexOf("@");
    refferal = email.substring(0, index);
    Log.d("index111", refferal);
    String phone = phoneValidate.getText().toString().trim();

    String name = nameValidate.getText().toString().trim();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    if (name.equals("")) {
        nameValidate.setError("Please enter your name");

    } else if (!phone.matches(phonpattern)) {
        phoneValidate.setError("Please enter the correct Phone format");
    } else if (!email.matches(emailPattern)) {
        emailValidate.setError("Please enter correct email address");
    } else {
        name = name_id.getText().toString();
        phone_number = phone_id.getText().toString();
        email = email_id.getText().toString();
        writeNewUser(name, email, phone_number, refferal);
    }


}
catch (Exception e)
{

}

            }
        });

    }
    private void writeNewUser(String name,String email,String phone,String refferal) {
        User user = new User(name,email,phone,refferal);
        mref.child("users").child(currentuser).setValue(user);
        Intent myIntent = new Intent(context, HomeActivity.class);
        Globe.message="new";
        MainActivity.this.startActivity(myIntent);
        String value1=  mref.child("users").child(currentuser).child("name").toString();
        Toast.makeText(this, "Succeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent _intentOBJ= new Intent(Intent.ACTION_MAIN);
                        _intentOBJ.addCategory(Intent.CATEGORY_HOME);
                        _intentOBJ.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        _intentOBJ.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(_intentOBJ);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}
