# LogScanner
This is the source code for the Log Scanner app.  I built this app to be used to read auth.log files ( like the ones found in /var/log/auth.log ), and then output the list of IP Addresses from the lines containing "Invalid user".  This was supposed to be like the Java Command Line app authLogScan in the parent GitHub directory, only in APK form and usable in Android.  My idea was to allow the user to select input files using their own File Manager app, or manually enter an input file path if they didn't have one installed or backed out of their default File Manager app.  The input file paths would appear in a list, and the User could pick the path from that list to use.  The list uses a RadioGroup and RadioButtons now, but I was going to turn that into a list of check boxes for Task 5 ( use multiple Threads to read multiple logs ).  Once the input File is selected, the user could then start the scan and the list would display on another activity.  This output results display activity would then show the list and give a button to save the list to an output file path.  Sadly, I was only able to get the add input file path and the RadioGroup working, but I ran into a problem trying to read the file.  I tried to add some code after the due date, to test a line of code I was given over Wright State's Pilot Discussion board, but I think that only broke the app: now when I try to add an input file path, the app just crashes ( which it didn't do before ).

## Minimum Android Level
This app only runs on minimumSDK 24, or Android Nougat (7).

## Permissions
* External Write
    * to read log files given from input file path
    * to write the resulting list to a given output file path
