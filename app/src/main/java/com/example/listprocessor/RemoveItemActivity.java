package com.example.listprocessor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RemoveItemActivity extends AppCompatActivity {

    @Inject
    ItemList the_list;   // reference to singleton string list object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remove_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void removeItem(View view){
        EditText et;
        int position;

        et = (EditText) findViewById(R.id.remove_item);

        //get the position of the element you want to remove

        Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                "Cruise removed successfully",
                Snackbar.LENGTH_SHORT).show();
        try{
            position = Integer.parseInt(et.getText().toString());
            the_list.remove(position);

        }catch(IndexOutOfBoundsException e)
        {
            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "Failed to remove cruise from list",
                    Snackbar.LENGTH_SHORT).show();

        }catch(NumberFormatException e){
            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "Enter a valid number",
                    Snackbar.LENGTH_SHORT).show();
        }

    } //End of removeItem

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
}