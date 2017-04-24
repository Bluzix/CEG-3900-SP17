# PasswordHelp
This app is used to check to see if your given password is a good one.

## How to use the app.
When the app starts up, enter a password that you think is good into the Password box and then hit the "Check Password" button on the bottom.  Currently, the app will tell you if your password passes our check, is bad, or will tell you that the password you entered was blank.

## What is our check?
When you hit the "Check Password" button, your password is checked to see if it is on the list of top 100000 most used password list.  This app ignores upper or lower case letters when checking the list.  The app will skip this check if you try to enter a blank password.

## Can the developer see the given password?
No, the check is done internally, and your given password is not sent over the internet.  Your password never leaves your device, nor is it saved on your device during the check.

## Changes from P6
I managed to fix the User Interface, so everything should show up correctly.  I also tried to add the functionality of GoSimple LLC's [Nbvcxz](https://github.com/GoSimpleLLC/nbvcxz) to this app.

## Bugs
The TextEdit View, where the password is entered, likes to disappear when the on screen keyboard shows up.  Also, when I added Nbvcxz's packages to this app, the app stopped compiling.  Some classes in those packages uses Java8 or plain Java libraries, and those need to be swapped out for more Android Compatible Java classes.