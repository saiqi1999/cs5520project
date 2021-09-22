---
layout: default
title: Assignment 2
date: 09-21-2021
---
2-1: https://github.com/saiqi1999/cs5520project/tree/main/lesson2_1
2-2: https://github.com/saiqi1999/cs5520project/tree/main/lesson2_2
***homework problems***
Question 1
What changes are made when you add a second Activity to your app by choosing File > New > Activity and an Activity template? Choose one:

The second Activity is added as a Java class, the XML layout file is created, and the AndroidManifest.xml file is changed to declare a second Activity.

Question 2
What happens if you remove the android:parentActivityName and the <meta-data> elements from the second Activity declaration in the AndroidManifest.xml file? Choose one:
  
The Up button in the app bar no longer appears in the second Activity to send the user back to the parent Activity.

Question 3
Which constructor method do you use to create a new explicit Intent? Choose one:
  
new Intent(Context context, Class<?> class)

Question 4
In the HelloToast app homework, how do you add the current value of the count to the Intent? Choose one:

As an Intent extra

Question 5
In the HelloToast app homework, how do you display the current count in the second "Hello" Activity? Choose one:

All of the above.

Question 1
If you run the homework app before implementing onSaveInstanceState(), what happens if you rotate the device? Choose one:

The counter is reset to 0, and the EditText no longer contains the text you entered.

Question 2
What Activity lifecycle methods are called when a device-configuration change (such as rotation) occurs? Choose one:

Android shuts down your Activity by calling onPause(), onStop(), and onDestroy(), and then starts it over again, calling onCreate(), onStart(), and onResume().

Question 3
When in the Activity lifecycle is onSaveInstanceState() called? Choose one:

onSaveInstanceState() is called before the onStop() method.

Question 4
Which Activity lifecycle methods are best to use for saving data before the Activity is finished or destroyed? Choose one:

onPause() or onStop()
