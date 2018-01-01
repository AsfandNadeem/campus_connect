package com.example.asfand.androidproject;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    String departmentH,nameH,emailH;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String SPValue="All";
    dbase DB;
    ProgressDialog progress;
    TextView drawerName,drawerEm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         list= PreferenceManager.getDefaultSharedPreferences(this);
        myListView=(ListView) findViewById(R.id.listfeeds) ;
        spH = (Spinner) findViewById(R.id.spinnerHomepage);
        Log.d("userIDH",list.getString(userID,""));
        progress=new ProgressDialog(getApplicationContext());
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Fetching Data");
        progress.setIndeterminate(true);

        //fillArrayList();
//        database = FirebaseDatabase.getInstance();
        DatabaseReference myRefD = database.getReference("users");
        //DatabaseReference u=myRef.child(id);
        DB=new dbase(getApplicationContext());
        myRefD.child(list.getString(userID,"")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                departmentH=dataSnapshot.child("department").getValue(String.class).toString();
                nameH=dataSnapshot.child("name").getValue(String.class).toString();
                emailH=dataSnapshot.child("email").getValue(String.class).toString();
                Log.d("UsernameH",nameH);
                Log.d("UseremailH",emailH);
                Log.d("UserdepartHfromUser",departmentH);
                SharedPreferences.Editor e=list.edit();
                e.putString(department,departmentH);
                e.commit();
                updateSpinner();

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });




        myRowItems=new ArrayList<ListShow>();
        myAdapter=new CustomAdapter(getApplicationContext(),myRowItems);
        myListView.setAdapter(myAdapter);
        setSupportActionBar(toolbar);
        //DB.dbclear();
        fillArrayList();

        spH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                    SPValue=spH.getSelectedItem().toString();
                    DBread();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        View headerView = navigationView.getHeaderView(0);
//        navUsername = (TextView) headerView.findViewById(R.id.navUsername);
//        navUsernam.setText("Your Text Here");

       drawerName=(TextView) headerView.findViewById(R.id.drawerText);
        drawerEm=(TextView) headerView.findViewById(R.id.drawerEmail);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListShow abc=myRowItems.get(position);
                String ab=abc.getName()+abc.getDescription();
                Intent a=new Intent(getApplicationContext(),PostInfo.class);
                a.putExtra("1234",ab);
                startActivity(a);

            }
        });
    }

    //   @Override
//    protected void onResume() {
//        super.onResume();
//        if(spH.getSelectedItem()!="All")
//        {
//            updateSpinner();
//        }
//    }

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

        drawerName.setText(nameH);
        drawerEm.setText(emailH);


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            finish();
            System.exit(0);
        } else {
            super.onBackPressed();
            finish();
            System.exit(0);
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
            fillArrayList();
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
            SharedPreferences.Editor editor = list.edit();
           editor.putString(userID, "");

            editor.commit();
//            Intent i=new Intent(this,MainActivity.class);
//            startActivity(i);
            finish();
            System.exit(0);
        } else if (id == R.id.about) {
            Intent i=new Intent(this,AboutActivity.class);
            startActivity(i);


        } else if (id == R.id.Myposts) {
            Intent i=new Intent(this,MyPosts.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fillArrayList() {
        Toast.makeText(getApplicationContext(),"Fetching Data",Toast.LENGTH_LONG).show();
        DB.dbclear();
            General();


    }


    public void DBread()
    {
        myRowItems=new ArrayList<ListShow>();
        myAdapter=new CustomAdapter(getApplicationContext(),myRowItems);
        myListView.setAdapter(myAdapter);
        if(SPValue=="All") {

            Cursor cursor = DB.dataRead();
            while (cursor.moveToNext()) {
                ListShow row_two = new ListShow();

                row_two.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_category)));
                row_two.setDate(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_time)));
                row_two.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_title)));
                row_two.setName(cursor.getString(cursor.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_Pname)));
                //Log.d("abcd",""+childd.child("time").getValue(String.class).toString()+ i);
                myRowItems.add(row_two);
            }
            cursor.close();
        }
        else if(SPValue=="General") {
            Cursor cursor1 = DB.dataReadCategory("General");
            while (cursor1.moveToNext()) {
                ListShow row_two = new ListShow();

                row_two.setCategory(cursor1.getString(cursor1.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_category)));
                row_two.setDate(cursor1.getString(cursor1.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_time)));
                row_two.setDescription(cursor1.getString(cursor1.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_title)));
                row_two.setName(cursor1.getString(cursor1.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_Pname)));
                //Log.d("abcd",""+childd.child("time").getValue(String.class).toString()+ i);
                myRowItems.add(row_two);
            }
            cursor1.close();
        }
        else if(SPValue==departmentH)
        {

                Cursor cursor2=DB.dataReadCategory(departmentH);
                while(cursor2.moveToNext()) {
                    ListShow row_two = new ListShow();

                    row_two.setCategory(cursor2.getString(cursor2.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_category)));
                    row_two.setDate(cursor2.getString(cursor2.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_time)));
                    row_two.setDescription(cursor2.getString(cursor2.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_title)));
                    row_two.setName(cursor2.getString(cursor2.getColumnIndexOrThrow(postSchema.postEntry.COLUMN_Pname)));
                    //Log.d("abcd",""+childd.child("time").getValue(String.class).toString()+ i);
                    myRowItems.add(row_two);
                }
                cursor2.close();


        }

        myAdapter.notifyDataSetChanged();
    }

    public void General()
    {
        DatabaseReference myRef = database.getReference("post").child("General");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int i=0;

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
        DatabaseReference myRef = database.getReference("post").child(list.getString(department,""));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int i=0;

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
