package com.example.listprocessor;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

/////////////////////////////////////////////////////
//
// This app manipulates a list of strings.  Options
// are chosen from the main app bar.
//
// Some options just display results in the main
// TextView.  Others invoke new activities to carry
// out their tasks.
//
// Author: M. Halper
//
/////////////////////////////////////////////////////

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ItemList the_list;   // reference to singleton string list object

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

           tv = (TextView) findViewById(R.id.text_main);


    } // end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //My personal testing webpage is https://web.njit.edu/~bef/befP1Test.txt
    //The asset file to add a cruise from is newCruise.txt
    public void onOption1(MenuItem i)
    {
        // show the list in order

        int j;

        tv.setText("");

        // display the list items

        for(j = 0; j < the_list.size(); j++) {
            Cruise currentCruise = the_list.get(j);
            tv.append(currentCruise.getCruiseLine() + ", "
                    + currentCruise.getShipName() + ", "
                    + currentCruise.getNumCode() + ", "
                    + currentCruise.getCruiseRegion() + ", "
                    + currentCruise.getCruiseLength() + ", $"
                    + currentCruise.getPrice() + ", $"
                    + currentCruise.getGratuity() + "\n"
            );
        }
    } // end onOption1

    public void onOption2(MenuItem i)
    {
        //Load data from a webpage URL
        startActivity(new Intent(this, LoadFileActivity.class));

    } // end onOption2

    public void onOption3(MenuItem i)
    {
        // Start the activity to add a new item to the list
        startActivity(new Intent(this, AddItemActivity.class));

    } // end onOption3

    public void onOption4(MenuItem i)
    {
        //Activity to get data from existing list based on numCode
        startActivity(new Intent(this, DisplayInfo.class));
    } // end onOption4

    public void onOption5(MenuItem i)
    {

        // YYY: Start the activity to remove an item from the list

        tv.setText("Removing an item from the list.");
        startActivity(new Intent(this, RemoveItemActivity.class));

    } // end onOption5

    public void onOption6(MenuItem i)
    {
        //Show most expensive cruise
        tv.setText("");
        int e;
        if (the_list.isEmpty()) {
            tv.append("The list is empty.");
            return;
        }

        int currentMostExpensive = 0;
        double PpDVal = -1;
        //most_expensive keeps track of the index of the current most expensive value
        //counter is the value of the most expensive per-day price

        for (e = 0; e < the_list.size(); e++){
            Cruise c = the_list.get(e);
            double PpD = c.getPrice()/c.getCruiseLength();

            if (PpD > PpDVal){
                PpDVal = PpD;
                currentMostExpensive = e;
            }

        }

        Cruise mostExpensive = the_list.get(currentMostExpensive);
        tv.append(mostExpensive.getCruiseLine() + ", "
            + mostExpensive.getShipName() + ", "
                + mostExpensive.getNumCode() + ", $"
                + String.format("%.2f", PpDVal)
        );


    } // end onOption6

    public void onOption7(MenuItem i)
    {
        //Show average length of all cruises
        tv.setText("");
        int a;
        double totalLength = 0;
        if (the_list.isEmpty()) {
            tv.append("The list is empty.");
            return;
        }

        for(a = 0; a < the_list.size(); a++){
            totalLength += the_list.get(a).getCruiseLength();
        }
        double avg = totalLength/the_list.size();

        tv.append("Average length of all cruises is " + String.format("%.1f", avg) + " days");
    } // end onOption7

    public void onOption8(MenuItem i)
    {
        //Show the average per day price with respect to all the cruises
        tv.setText("");
        int b;
        double totalPrice = 0;
        int totalDays = 0;

        if (the_list.isEmpty()) {
            tv.append("The list is empty.");
            return;
        }

        for(b = 0; b < the_list.size(); b++){
            Cruise c = the_list.get(b);
            totalPrice += c.getPrice();
            totalDays += c.getCruiseLength();
        }
        double avgpDP = totalPrice/totalDays;

        tv.append("The average per day price of all cruises is $" + String.format("%.2f", avgpDP));
    } // end onOption8

} // end MainActivity
