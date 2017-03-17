package com.company;

import java.io.IOException;
//import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main( String[] args ) {

	    // Given from the instructions
        String inputFileName = "/var/log/auth.log", outputFileName = "/tmp/invalidUsers.txt";
        switch ( args.length ) {
            case 0: break;
            case 2: outputFileName = args[1]; // fall through
            case 1: inputFileName = args[0]; break;
            default:
                System.out.println
                        ( "Usage: At most two file names expected" );
                System.exit( 0 );
        }

        // Code by Sam Lincoln
        System.out.print("Reading " + inputFileName + " for Invalid user logins..." );

        String[] dummy = { "test", "contents" };
        Stream<String> lines = Arrays.stream( dummy );

        try{
            lines = Files.lines( Paths.get( inputFileName ) );
        }
        catch ( IOException e ){
            System.out.println( "Error" );
            System.out.println( e );
            System.exit( 0 );
        }

        //List<String> invalidAttempts = lines.filter( l -> l.contains( "Invalid user" ) )
        //        .collect( Collectors.toList() );

        List<String> invalidIP = new ArrayList<>();

        lines.filter( l -> l.contains( "Invalid user" ) )
                .forEach( l -> {
                    String[] tokens = l.split(" " );
                    invalidIP.add( invalidIP.size() + 1 + " " + tokens[ tokens.length - 1 ] );
                });

        System.out.println( "Done" );

        // Delete the output File if it exists
        if ( Files.exists( Paths.get( outputFileName ) ) ) {

            System.out.print( "Deleting " + outputFileName + "..." );

            try{
                Files.delete( Paths.get( outputFileName ) );
            }
            catch ( IOException e ){
                System.out.println( "Error" );
                System.out.println( e );
                System.exit( 0 );
            }

            System.out.println( "Done" );
        }

        System.out.print( "Writing list of IP Addresses to " + outputFileName + "..." );

        try{
            Files.createFile( Paths.get( outputFileName ) );
            Files.write( Paths.get( outputFileName ), invalidIP, Charset.defaultCharset() );
        }
        catch ( IOException e ){
            System.out.println( "Error" );
            System.out.println( e );
            System.exit( 0 );
        }

        System.out.println( "Done" );
    }
}
