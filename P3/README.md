#P3
This Directory contains the work I had done to complete P3 for CEG 3900 Mobile and Cloud Computing

##Task 1 - Collaborative Sudoku
I had to use my Pruned Sudoku to create a Sudoku game that allowed multiple people to work on the same puzzle at the same time.  The folder called collabSudoku is my source and collabSudoku.apk is the app that I created from that source.  I went through three different database structures in order to get the app to work, and FireBaseDataInit3.json is the file you can import to set up your Firebase database to work with the source code.

##Task 2 - FireBase Auth
I had to build the Auth app from the Firebase Quickstart pack again, and get it to work with the same project as my Collaborative Sudoku app.  The collabSudokuAuth.apk is the app that can be used to create Users for Collaborative Sudoku.

##Task 3 - Quiz on FireBase
This task was changed to writing pre- post-conditions, and asserts for [Friendly Pix](https://github.com/firebase/friendlypix).  The FeedsActivity_old.java is a copy of the FeedsActivity class from the source, and FeedsActivity.java is the same class but with my asserts and conditions.  The diff.txt is what calling "diff FeedsActivity_old.java FeedsActivity.java" from the Terminal produced as Output.

##Task 4 & 5 - Improve Pruned Sudoku
These two tasks I didn't get to finish.  The improvedSudoku folder is work I had done for the two tasks.  I'm sure it was enough to be 5% smaller in size compared to the original Pruned Sudoku.  However, we also needed to improve how the code was written ( Java 6 to Java 8 ), and make it 5% faster.