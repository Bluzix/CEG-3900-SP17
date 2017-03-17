# authLogScan
This is a Java Command Line app, which will read auth.log files ( found at /var/log/auth.log ) as a text document.  The app will then output a list of IPs that were listed in the lines containing "Invalid user" as a text document.  Each entry in the list is numbered as well, so you can get a total count of those lines by scrolling to the end of the text file and look at the first number of the last entry.

## How to use it
If you use javac to compile to authLogScan, here's how to use it: `authLogScan [inputFilePath] [outputFilePath]`
* inputFilePath can be blank, the app will scan /var/log/auth.log file
* outputFilePath can be blank, the app will write and overwrite /tmp/invalidUsers.txt
* if you give one argument, that argument is used as inputFilePath
* if you give more than two arguments, the app will stop and tell you that it should have a maximum of two arguments
* don't add [ and ], those are only there to copy the format of a man page entry
