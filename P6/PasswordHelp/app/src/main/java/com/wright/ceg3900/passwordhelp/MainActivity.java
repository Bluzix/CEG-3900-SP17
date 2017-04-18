package com.wright.ceg3900.passwordhelp;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is used to check the given Password to see if it is crackable
     * according to our standards: not in the 10_million_password_list_top_100000.txt.
     *
     * @param view The View that is returned by a button's onClick Event.
     */
    public void checkPassword(View view){

        // prepare to read lines from 10_million_password_list_top_100000.txt
        Resources res = getResources();
        InputStreamReader inputStreamReader = new InputStreamReader(
                res.openRawResource( R.raw.word_list) );
        BufferedReader reader = new BufferedReader( inputStreamReader );

        // prepare to check the given Password
        EditText edit = (EditText)findViewById( R.id.passwordEntry );
        String password = edit.getText().toString();
        boolean pass = true;

        // grab the status and hint views
        TextView status = (TextView)findViewById( R.id.txtStatus );
        TextView hints = (TextView)findViewById( R.id.txtHints );

        // if the given password is blank, skip check
        if ( password.isEmpty() ){

            status.setText( R.string.blank_password );
            status.setTextColor( Color.GRAY );
            hints.setText( R.string.blank_hint );
            return;

        }

        try{
            String word = reader.readLine();

            while ( word != null ) {

                if (password.equalsIgnoreCase(word)) {
                    pass = false;
                    break;
                }

                word = reader.readLine();
            }

            reader.close();

            // does it pass the test?
            if ( pass ){

                status.setText( R.string.pass_password );
                status.setTextColor( Color.GREEN );
                hints.setText( R.string.pass_hint );

            }
            else {

                status.setText( R.string.bad_password );
                status.setTextColor( Color.RED );
                hints.setText( R.string.match_hint );
            }
        }
        catch (IOException e){

            hints.setText( e.toString() );

        }

    }
}
