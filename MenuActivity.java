package com.example.android.npuzzle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


public class MenuActivity extends ActionBarActivity {

    public static int dimension = 3;
    public static int pictureID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Text Field listeners
        final EditText editTextDim = (EditText) findViewById(R.id.editTextDim);
        final EditText editTextSrc = (EditText) findViewById(R.id.editTextSrc);
        editTextDim.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    // switch to picture source setting
                    dimension = Integer.valueOf(editTextDim.getText().toString());
                    // set dimension //TODO:BUGS
                    editTextDim.clearFocus();
                    editTextSrc.requestFocus();
                    handled = true;
                }
                return handled;
            }
        });
        editTextSrc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    // switch to next screen
                    startGame();
                    handled = true;
                }
                return handled;
            }
        });


    }

    private void startGame() {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("EXTRA_DIMENSION", dimension);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
