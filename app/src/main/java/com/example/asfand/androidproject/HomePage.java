package com.example.asfand.androidproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
    Spinner spH;
    SharedPreferences list;
    String department= "department";
    String userID="ID";
    String departmentH;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    dbase DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         list= PreferenceManager.getDefaultSharedPreferences(this);
        myListView=(ListView) findViewById(R.id.listfeeds) ;
        spH = (Spinner) findViewById(R.id.spinnerHomepage);
        Log.d("userIDH",list.getString(userID,""));

//        database = FirebaseDatabase.getInstance();
        DatabaseReference myRefD = database.getReference("users");
        //DatabaseReference u=myRef.child(id);
        DB=new dbase(getApplicationContext());
        myRefD.child(list.getString(userID,"")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                departmentH=dataSnapshot.child("department").getValue(String.class).toString();
                Log.d("departHfromUser",departmentH);
                updateSpinner();

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });





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

    public void updateSpinner() {
        String[] categoryList= {"All","General",departmentH };
        // Log.d("departH",list.getString(department,""));

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, categoryList);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spH.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
        } else {
            super.onBackPressed();
            finish();
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

        if (id == R.id.logout) {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.about) {

        } else if (id == R.id.Myposts) {

        } else if (id == R.id.update_info) {

        }

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
            General();


    }

    public void DBread()
    {
        Cursor cursor=DB.dataRead();
        while(cursor.moveToNext())
        {
            ListShow row_two = new ListShow( );

            row_two.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_category)));
            row_two.setDate(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_time)));
            row_two.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_desc)));
            row_two.setName(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_Pname)));
            //Log.d("abcd",""+childd.child("time").getValue(String.class).toString()+ i);
            myRowItems.add( row_two );
        }
        myAdapter.notifyDataSetChanged();
        cursor.close();
    }

    public void General()
    {
        DatabaseReference myRef = database.getReference("post").child("General");
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
                        ContentValues values = new ContentValues();
                        values.put(postSchema.postEntry.COLUMN_postid, childd.getKey().toString());
                        values.put(postSchema.postEntry.COLUMN_title,childd.child("title").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_desc,childd.child("desc").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_email,childd.child("email").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_phone,childd.child("phone").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_userid,childd.child("uid").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_category,childd.child("category").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_time,childd.child("time").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_Pname,childd.child("banda").getValue(String.class).toString());
                        DB.dataInsert(values);

                        /*ListShow row_two = new ListShow( );
                        row_two.setCategory("asjhdskja");
                        row_two.setDate(childd.child("time").getValue(String.class).toString());
                        row_two.setDescription(childd.child("desc").getValue(String.class).toString());
                        row_two.setName(childd.child("banda").getValue(String.class).toString());
                        Log.d("abcd",""+childd.child("time").getValue(String.class).toString()+ i);
                        myRowItems.add( row_two );*/
                    }
                    catch (Exception e) {

                    }


                }
                /*String s = dataSnapshot.child("abc-abc").child("desc").getValue().toString();
                Log.d("abcdefghij",s);*/

                DBread();
                Category();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void Category()
    {
        DatabaseReference myRef = database.getReference("post").child(departmentH);
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
                        ContentValues values = new ContentValues();
                        values.put(postSchema.postEntry.COLUMN_postid, childd.getKey().toString());
                        values.put(postSchema.postEntry.COLUMN_title,childd.child("title").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_desc,childd.child("desc").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_email,childd.child("email").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_phone,childd.child("phone").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_userid,childd.child("uid").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_category,childd.child("category").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_time,childd.child("time").getValue(String.class).toString());
                        values.put(postSchema.postEntry.COLUMN_Pname,childd.child("banda").getValue(String.class).toString());
                        DB.dataInsert(values);

                        /*ListShow row_two = new ListShow( );
                        row_two.setCategory("asjhdskja");
                        row_two.setDate(childd.child("time").getValue(String.class).toString());
                        row_two.setDescription(childd.child("desc").getValue(String.class).toString());
                        row_two.setName(childd.child("banda").getValue(String.class).toString());
                        Log.d("abcd",""+childd.child("time").getValue(String.class).toString()+ i);
                        myRowItems.add( row_two );*/
                    }
                    catch (Exception e)
                    {

                    }


                }

                DBread();
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
