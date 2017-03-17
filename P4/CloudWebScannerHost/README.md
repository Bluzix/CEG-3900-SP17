# CloudWebScannerHost
This was going to be the Java Command Line app that would have ran on a cloud or remote server.  I was thinking that I would need a Client and Host Java program to complete Task 3 and 4 of P4.  This app would have been ran by the Client app, through some sort of connection or service on AWS.  The job of this app would then be to read in the list of URLs given by the client, and then run the map-reduce process to find the n most used words in the text of those websites.  Sadly, I was only able to complete the switch cases to test the number of arguments before I ran out of time.

## How to use
If you javac compiled Main.java to CloudWebScannerHost, then use it like this: `CloudWebScannerHost [inputFilePath] [int n]`
* if inputFilePath is blank, the app will use /home/user/testFile.txt
* if int n is blank, the app will use 10; it will find the top ten most used words
* if only one argument is given, it will use it for inputFilePath
* don't add [ or ], I'm just styling the use of CloudWebScannerHost like a man page