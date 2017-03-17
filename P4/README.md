# P4 - Java8 Streams and Map+Reduce
This lab was used to help us get into Cloud Computing with AWS some more, as well as introduce us to new features of Java8 and Map+Reduce.

## Task 1 - Java8 Streams in Linux
We had to make use Java8 Streams in a Linux environmnet.  The only thing that we had to do was use an IDE like IntelliJ ( which I used ) to create a Command Line Java application.  The goal was to build an app that would take in two arguments: a path to a log file ( like the one in /var/log/auth.log ) and a path to save the results.  Then the app would scan the log file for lines containing "Invalid user", take the IP Address from those lines to build a numbered list, and then write that list to an output file.  The source code is in the authLogScan directory.  My test input file is auth.log.txt, and the output from that file is outputTask1.txt.

## Task 2 - Java8 Streams in an Android APK
The goal for this Task was to take the Command Line Java application in Task 1, and put it in a usable Android app.  My design choice was to have two activities: one for inputting input file paths for auth.log files, and another to display and save the numbered list of "Invalid user" IP Addresses.  I managed to finish inputting the files, but then I ran out of time to complete this app.  Plus, I couldn't use the Java8 Files class and I wouldn't find out about BufferedReader's lines() method until after the due date.  I tried to test BufferedReader, but only ended up breaking the input file paths button.

## Task 3 - Map+Reduce in Linux
The goal for this task was to build a Command Line Java app that could take in a file path containing a list of URLs to read, and a number (x) for top x words use in the pages as arguments.  The app was then supposed to read the text file containing the URLs to read and then number x, and give it to a remote cloud computer ( AWS for this Task ) and have that remote computer do the heavy lifting.  Then the client would wait for the resulting top number x words from the host.  I didn't really know how to set this up, so I thought I would have to make two apps: CloudWebScannerClient for the client to run, and CloudWebScannerHost for the remote host to use as a REST service.  I don't know if I was right or not, and I didn't get really far on this one.  You can view the source directories, but there's nothing much there.

## Task 4 - Map+Reduce in Android
The goal for this Task was to take the code in Task 3 ( mainly the client ), and get it to run as an Android app.  I didn't even finish Task 3, so I didn't even start this Task.

## Task 5 - Java8 Cocurrency
The goal for this Task was to make another version of Task 1 that made use of Concurrent Threads.  I thought a little bit ahead in Task 2 and designed my input file paths to be displayed in a list.  In this Task, I was going to change that list to a check list and the checked input file paths would be read on their own Thread.  However, besides the work I did in Task 2, I didn't get started on this own either.

## Optional Bonus Task 6 - Apache Spark/Java in Linux
The goal for this task was to rewrite the Command Line Java app I wrote in Task 3 to use Apache Spark + Java.  I didn't get Task 3 done, so I didn't even attempt this one.