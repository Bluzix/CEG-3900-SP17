package com.wright.ceg3900.logscanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class InputSelect extends AppCompatActivity {

    // pick file request code
    private static final int PICKFILE_REQUEST_CODE = 3;

    // Radio Button Ids
    private int radioButtonId = 4000;

    /**
     * This method is first called when the App starts.  It's job is to set
     * the screen to the proper layout, and add the default File to the
     * RadioGroup that holds all the entered Files that can be selected to
     * be scanned.
     *
     * @param savedInstanceState a Bundle that can contain a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_select);

        // create  RadioButton for the default Input file value
        //RadioButton defaultFile = new RadioButton( getApplicationContext() );
        //defaultFile.setText( R.string.default_file );
        //defaultFile.setId( radioButtonId++ );

        // add it to the RadioGroup
        //RadioGroup group = ( RadioGroup ) findViewById( R.id.rbgInputList );
        //group.addView( defaultFile );
        //group.check( radioButtonId - 1 );
    }

    /**
     * This method is used to create an AlertDialog that asks the User to
     * manually enter the File Path for the Input File to add to the
     * RadioGroup.
     */
    private void typeInFile(){

        // set up Alert Dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( R.string.enter_input_file );

        // set up input on the Alert Dialog
        final EditText input = new EditText(this);
        input.setInputType( InputType.TYPE_CLASS_TEXT );
        builder.setView( input );

        // set up buttons
        builder.setPositiveButton( R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String path = input.getText().toString();
                if ( path.trim().isEmpty() ){
                    showError();
                }
                else {
                    File inputFile = new File( path );

                    // if the file exists and we can read it, use it
                    if ( inputFile.exists() && inputFile.canRead() ){
                        // add the entered Path to the RadioGroup and get it selected
                        RadioButton addButton = new RadioButton( getApplicationContext() );
                        addButton.setText( path );
                        addButton.setId( radioButtonId++ );

                        RadioGroup group = ( RadioGroup ) findViewById( R.id.rbgInputList );
                        group.addView( addButton );
                        group.check( radioButtonId - 1 );
                    }

                    // if we can't read the existing file, tell the User
                    else if ( inputFile.exists() && !inputFile.canRead() ){
                        showError( path, true );
                    }

                    // else we have to tell the user that their file path is wrong
                    else{
                        showError( path, false );
                    }
                }

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        // show the Alert Dialog
        builder.show();
    }

    /**
     * This method displays another Alert Dialog, after the one from typeInFile(), to say that the
     * User did not enter a correct File Path.  This message is displayed if they just entered
     * nothing or a bunch of whitespace characters in the original Alert Dialog box.
     */
    private void showError(){

        // set up Alert Dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( R.string.error );
        builder.setMessage( R.string.empty_path );

        builder.setPositiveButton( R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialogInterface, int i ) {
                typeInFile();
            }
        });

        builder.show();
    }

    /**
     * This method displays another Alert Dialog, after the one from typeInFile(), to say that the
     * User did not enter a correct File Path.  However, the User actually tried to enter something
     * this time.  This message will either state that the File Path does not exist, or that the
     * File Path exists but cannot be read.
     *
     * @param path this is the File Path that the User tried to enter as a legitimate use able
     *             File Path.
     * @param exists if false, the path is not a legitimate File Path on the Android device; else,
     *               the path points to a File that cannot be read by the Android device.
     */
    private void showError( String path, boolean exists ){

        // set up Alert Dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( R.string.error );

        // if the file path exists, state we cannot read it
        if ( exists ){
            builder.setMessage( path + " " + getString( R.string.cant_read ) );
        }

        // else the file path does not exist
        else {
            builder.setMessage( path + " " + getString( R.string.bad_path ) );
        }

        builder.setPositiveButton( R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialogInterface, int i ) {
                typeInFile();
            }
        });

        builder.show();
    }

    /**
     * This method is the action taken by the btnAddInputFile button.
     * Its job is to broadcast an Intent to a receiver that can select
     * files that exist on the Android device.
     *
     * @param view this should be from the btnAddInputFile button
     */
    protected void selectInputFile( View view ){

        Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
        intent.setType( "file/*" );

        // a try-catch block, in case there is no File Manager is found
        try{
            startActivityForResult( intent, PICKFILE_REQUEST_CODE );
        }
        catch ( android.content.ActivityNotFoundException e ){
            typeInFile();
        }

    }

    /**
     * This method displays an Alert Dialog when the User tries to click the Start Scan button
     * without first entering and selecting an Input File to read from the list.
     */
    private void showErrorSelection(){

        // set up Alert Dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( R.string.error );
        builder.setMessage( R.string.select_file );

        builder.setPositiveButton( R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialogInterface, int i ) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    /**
     * This method is the action taken by the btnStartScan button.
     * It's job is to check for a selected Input File path and do the work of scanning that file
     * for IP Addresses from the lines containing "Invalid user"; assuming the input is some sort
     * of /var/log/auth.log file from a Linux PC or stored on the Android Device.  If no Input File
     * path is entered or selected, this method will display an Alert Dialog to inform the User
     * that they need to enter and/or select one from the list.
     *
     * @param view this should be from the btnStartScan button
     */
    protected void startScan( View view ){

        // check RadioGroup for the Id of Selected RadioButton in the group
        RadioGroup group = ( RadioGroup ) findViewById( R.id.rbgInputList );
        int selectedId = group.getCheckedRadioButtonId();

        // if selection is empty, the group returns -1 as the Id; alert the use that nothing is
        // selected.
        if ( selectedId == -1 ){
            showErrorSelection();
        }

        // else we have a selected RadioButton and can use the File Path from it
        else {

            // read the path of the selected RadioButton
            RadioButton selectedBtn = ( RadioButton ) findViewById( selectedId );
            String path = selectedBtn.getText().toString();

            // read the contents, File Path is already checked to exist from onActivityResult
            File inputFile = new File( path );
            // TODO: get a Stream<String> of lines from the file
            // start added after due date
//            try{
//                BufferedReader reader = new BufferedReader( new FileReader( inputFile ) );
//                Stream<String> lines = reader.lines();
//
//                ArrayList<String> invalidIP = new ArrayList<>();
//
//                lines.filter( l -> l.contains( "Invalid user" ) )
//                        .forEach( l -> {
//                            String[] tokens = l.split(" " );
//                            invalidIP.add( invalidIP.size() + 1 + " " + tokens[ tokens.length - 1 ] );
//                        });
//
//                try{
//                    reader.close();
//                }
//                catch ( IOException e ){
//                    Log.d( "startScan", e.toString() );
//                }
//            }
//            catch ( FileNotFoundException e ){
//                showError( path, false );
//            }
            // end added after due date
        }
    }

    /**
     * This method is used to process the result of an external Activities that were called up
     * through Intents made by this Activity.  This method has been Override to process
     * PICKFILE_REQUEST_CODEs for File Managers that were used to pick an Input File for our Input
     * File List.
     * @param requestCode the code returned from doing a startActivityForResult request. This
     *                    Override method only looks for PICKFILE_REQUEST_CODE.
     * @param resultCode this will be a RESULT_OK if the Activity was completed by the User, or
     *                   RESULT_CANCELED if the User backed out of the external Activity ( the
     *                   File Manager for the PICKFILE_REQUEST_CODE case ).
     * @param data This will be the Intent returned from the external Activity, the
     *             data.getDataString() will be used as a File Path for an Input File if the
     *             requestCode is equal to PICKFILE_REQUEST_CODE and the resultCode is equal to
     *             RESULT_OK.
     */
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ){

        // if requestCode is PICKFILE_REQUEST_CODE, act upon the File Manager's response
        if ( requestCode == PICKFILE_REQUEST_CODE ){

            // if User picked a File from the File Manager then use the
            // Path that was returned to create a RadioButton in the Radio Group
            if ( resultCode == RESULT_OK ){

                RadioButton addButton = new RadioButton( getApplicationContext() );
                addButton.setText( data.getDataString() );
                addButton.setId( radioButtonId++ );

                RadioGroup group = ( RadioGroup ) findViewById( R.id.rbgInputList );
                group.addView( addButton );
                group.check( radioButtonId - 1 );
            }
            else if ( resultCode == RESULT_CANCELED ){
                typeInFile();
            }
        }

        super.onActivityResult( requestCode, resultCode, data );
    }
}
