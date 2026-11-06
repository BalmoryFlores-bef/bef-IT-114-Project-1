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

import com.example.listprocessor.ItemList;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoadFileActivity extends AppCompatActivity {

    @Inject
    ItemList the_list;   // reference to singleton Cruise list object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_load_file);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("SetTextI18n")
    public void loadFile(View view) throws IOException {
        EditText et;
        et = (EditText) findViewById(R.id.load_file);
        hideKeyboard();

        try{
            URL file_url = new URL(et.getText().toString());
            Scanner sc = new Scanner(file_url.openStream());

            the_list.clear();
            while (sc.hasNextLine()){
                String firstLine = sc.nextLine();
                if (firstLine.isEmpty()){
                    continue;
                }

                Cruise temp = new Cruise();

                temp.setCruiseLine(firstLine);
                temp.setShipName(sc.nextLine());
                temp.setNumCode(sc.nextLine());
                temp.setCruiseRegion(sc.nextLine());
                temp.setCruiseLength(Integer.parseInt(sc.nextLine()));
                temp.setPrice(Double.parseDouble(sc.nextLine()));
                temp.setGratuity(Double.parseDouble(sc.nextLine()));

                the_list.add(temp);
            }
            sc.close();

            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    " added to the list",
                    Snackbar.LENGTH_SHORT).show();

        }catch (IOException e){
            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "File does not exist",
                    Snackbar.LENGTH_SHORT).show();
        }
    }//end of load file
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
}//End of load activity