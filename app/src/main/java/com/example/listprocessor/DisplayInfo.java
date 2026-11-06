package com.example.listprocessor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DisplayInfo extends AppCompatActivity {

    @Inject
    ItemList the_list;   // reference to singleton Cruise list object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("SetTextI18n")
    public void numCodeData(View view){
        EditText et;
        TextView tv;
        et = (EditText) findViewById(R.id.numCode_et);
        tv = (TextView) findViewById(R.id.numCode_view);
        hideKeyboard();

        String input = et.getText().toString();
        int n;
        if (the_list.isEmpty()) {
            tv.append("The list is empty.");
            return;
            }

        for (n = 0; n < the_list.size(); n++){
            Cruise c = the_list.get(n);

            if(c.getNumCode().equals(input)){
                tv.append("" + c.getCruiseLine() + ", "
                        + c.getShipName() + ", "
                        + c.getCruiseRegion() + ", "
                        + c.getCruiseLength() + ", $"
                        + c.getPrice() + ", $"
                        + c.getGratuity()
                );
            }

        }
    }//end of DisplayInfo

    private void hideKeyboard()
    {
        // This method dismisses the soft keyboard.
        // Code derived from developer.android.com and
        // StackOverflow

        Context context = getCurrentFocus().getContext();

        InputMethodManager inputMethodManager =
                (InputMethodManager)
                        context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

    } // end hideKeyboard
}//End of Activity