package com.company;

public class Main {

    public static void main(String[] args) {

        // string values for inputFile ( list of URLs ), and number of top words ( n )
        // output file will be handled through CloudWebScannerClient
        String inputFile = "/home/user/testFile.txt";
        int topWords = 10;

        // switch case to check the args
        switch ( args.length ){
            case 0: break;
            case 2: topWords = Integer.parseInt( args[1] );
            case 1: inputFile = args[0]; break;
            default: System.out.println( "Usage: At most one file name and one integer expected." );
                     System.exit( 0 );
        }


    }
}
