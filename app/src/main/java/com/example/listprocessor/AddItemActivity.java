package com.example.listprocessor;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@AndroidEntryPoint
public class AddItemActivity extends AppCompatActivity {

    @Inject
    ItemList the_list;   // reference to singleton string list object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    } // end onCreate

    // add the item to the list

    public void addItem(View view) throws IOException {

        EditText et;
        et = (EditText) findViewById(R.id.add_item);


        try
        {
            String inFile = et.getText().toString();
            Scanner sca = new Scanner(getAssets().open(inFile));
            Cruise temp;

                Cruise fileCruise = new Cruise();

                fileCruise.setCruiseLine(sca.nextLine());
                fileCruise.setShipName(sca.nextLine());
                fileCruise.setNumCode(sca.nextLine());
                fileCruise.setCruiseRegion(sca.nextLine());
                fileCruise.setCruiseLength(Integer.parseInt(sca.nextLine()));
                fileCruise.setPrice(Double.parseDouble(sca.nextLine()));
                fileCruise.setGratuity(Double.parseDouble(sca.nextLine()));

                the_list.add(fileCruise);

            hideKeyboard();
            et.toString();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    " added to the list",
                    Snackbar.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "File does not exist",
                    Snackbar.LENGTH_SHORT).show();

        }

    } // end addItem

    private void hideKeyboard(){
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

} // end AddItemActivity
