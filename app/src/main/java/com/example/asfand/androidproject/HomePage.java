package com.example.asfand.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    ListView myListView;
    ArrayList<ListShow> myRowItems;
    CustomAdapter myAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        myListView=(ListView) findViewById(R.id.listfeeds) ;

        myRowItems=new ArrayList<ListShow>();
        myAdapter=new CustomAdapter(getApplicationContext(),myRowItems);
        myListView.setAdapter(myAdapter);
        setSupportActionBar(toolbar);
        fillArrayList();
       // myAdapter.notifyDataSetChanged();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            return true;
        }
        if (id == R.id.action_add) {
            Intent i=new Intent(this,FormActivity.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fillArrayList() {

        /*ListShow row_one = new ListShow( );
        row_one.setCategory("General");
        row_one.setDate("30-Nov-2017");
        row_one.setDescription("Fall 15 Sports Gala");
        row_one.setName("Abbas Nazar");

        ListShow row_two = new ListShow( );
        row_two.setCategory("General");
        row_two.setDate("30-Nov-2017");
        row_two.setDescription("Fall 15 Sports Gala");
        row_two.setName("Abbas Nazar");


        myRowItems.add( row_one );
        myRowItems.add( row_two );*/

        DatabaseReference myRef = database.getReference("post");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                    int i=0;
                myRowItems=new ArrayList<ListShow>();
                myAdapter=new CustomAdapter(getApplicationContext(),myRowItems);
                myListView.setAdapter(myAdapter);

                for (DataSnapshot childd : dataSnapshot.getChildren())
                {
                    try {
                        i++;
                        ListShow row_two = new ListShow( );
                        row_two.setCategory("asjhdskja");
                        row_two.setDate(childd.child("time").getValue(String.class).toString());
                        row_two.setDescription(childd.child("desc").getValue(String.class).toString());
                        row_two.setName(childd.child("banda").getValue(String.class).toString());
                        myRowItems.add( row_two );
                        Log.d("abcd",""+childd.child("time").getValue(String.class).toString()+ i);
                    }
                    catch (Exception e)
                    {

                    }
                  myAdapter.notifyDataSetChanged();


                }
                /*String s = dataSnapshot.child("abc-abc").child("desc").getValue().toString();
                Log.d("abcdefghij",s);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
}
