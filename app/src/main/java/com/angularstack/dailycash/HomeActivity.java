package com.angularstack.dailycash;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import am.appwise.components.ni.NoInternetDialog;
import ru.bullyboo.text_animation.AlphaBuilder;
import ru.bullyboo.text_animation.AnimationBuilder;
import ru.bullyboo.text_animation.TextCounter;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView t1,t2;
    DatabaseReference ref;
    String value,str;
    ImageView imageview;
    ProgressDialog progress;
    NoInternetDialog noInternetDialog;
    private FirebaseAuth mAuth;
    Context context=HomeActivity.this;
    Dialog myDialog,myDialog1;
    TextView text_reward;
    WebView webView;
    TextView points;

View view,view1,view2;
String url="https://api.whatsapp.com/send?phone=917034874169&text=Regarding%20DailyCashy%3A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        t2=findViewById(R.id.ip_address);

        view1=findViewById(R.id.id_welcome);
        webView=findViewById(R.id.webview);
        String str = intent.getStringExtra("message");
        text_reward=findViewById(R.id.reward);
        String ip=getLocalIpAddress();
        t2.setText(ip);
        try {
str=Globe.message;
if(str.equals("new"))
{
    Intent myIntent = new Intent(HomeActivity.this, Welcome.class);
    HomeActivity.this.startActivity(myIntent);
    Globe.message="old";
}

        myDialog = new Dialog(this);
        myDialog1 = new Dialog(this);
        view=findViewById(R.id.custom_view);

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String cu = currentFirebaseUser.getUid();
            //Log.d("kiduvee", cu);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReference().child("issue");
        }
        catch (Exception e)
        {

        }
        try {
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Object post = dataSnapshot.getValue();
                    try {
                        value = post.toString();

                        Log.d("vanakkam", value);


                        if (value.equals("0")) {


                        } else {
                            new AlertDialog.Builder(context)
                                    .setTitle("Upgrade!")
                                    .setMessage("You are using the older version of DailyCash Please Upgrade!")
                                    .setCancelable(false)

                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(context, "This will open the web page ", Toast.LENGTH_SHORT).show();

                                        }
                                    })


                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    } catch (Exception e) {

                        Toast.makeText(context, "Exception occured", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
        catch (Exception e)
        {

        }

        try {
            noInternetDialog = new NoInternetDialog.Builder(HomeActivity.this).build();
        }
        catch(Exception e)
        {

        }
        progress = new ProgressDialog(HomeActivity.this);
        progress.setTitle("Loading");
        progress.setMessage("We are working on it please wait!!");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
try {

    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String cu = currentFirebaseUser.getUid();
    Log.d("kiduvee", cu);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ref = database.getReference().child("users").child(cu);
}
catch (Exception e)
{

}
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReadData post = dataSnapshot.getValue(ReadData.class);
                try {
                    value = post.name.toString();
                    Log.d("kiduve", value);
                    if (value.equals("")) {
                        Toast.makeText(HomeActivity.this, "Error: Something went wrong :(", Toast.LENGTH_SHORT).show();
                        progress.dismiss();

                    } else {
                        t1=findViewById(R.id.avatar_name);
                      t1.setText(value);
                       // Toast.makeText(HomeActivity.this, value, Toast.LENGTH_SHORT).show();
                      progress.dismiss();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(HomeActivity.this, "Error: Something went wrong :(", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Only for testing purpose", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(i);
        return true;
    }

    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.customlayout);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent myIntent = new Intent(context, Profile.class);
            HomeActivity.this.startActivity(myIntent);

        } else if (id == R.id.nav_slideshow) {
            Intent myIntent = new Intent(HomeActivity.this, InviteNEarn.class);
            HomeActivity.this.startActivity(myIntent);

        } else if (id == R.id.nav_manage) {
            Intent myIntent = new Intent(HomeActivity.this, Redeem.class);
            HomeActivity.this.startActivity(myIntent);

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey your friend "+value+" Shared Daily Cashy app. Download from this link . Using this app you can earn unlimited money join with us by entering  refferal code and happy earning!!");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_send) {

            shouldOverrideUrlLoading(webView,url);

        }
        else if(id==R.id.nav_signout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent I = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(I);
            Toast.makeText(this, "Successfully signed out", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }

}
