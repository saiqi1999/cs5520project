---
layout: default
title: Assignment5 - Basic timer worker added
Date: 2021-10-31
---
Brief introduction: \
this is an Android app that allows you to create, edit and delete note in it\
the notes are stored in room database and you can get it the next time you opend this app\
add button is a green button in right-down corner, delete button is a red button in right-up corner, and click note title to edit specific note\
click note detail to make the note blue

Patch note: \
added basic timer function to this app, it can also create a notification now\
you can select time using "pick time" button when you are creating or updating notes\
while picking time the picked time will be shown in button\
notify could be replaced by update, the old one won't create notifications in this case

Update Nov 10:\
I updated this project so there is a "TodoList Version 2.3" in this folder, it has new feature below:\
added a switch to each note, which is used to pin certain notes\
the switch will be shown in main activity(right to the title), you can change the note switch here, it will make a "slient" update\ 
alarm clock info is now saved in database, so you'll find them the next time open the app\

link to Code: \
[https://github.com/saiqi1999/cs5520project/tree/main/Assignment5](https://github.com/saiqi1999/cs5520project/tree/main/Assignment5)\

Overview:\
<img src = "https://raw.githubusercontent.com/saiqi1999/cs5520project/gh-pages/images/HW5/helpGif.gif" width="360"/>\

Components:\
MainActivity/InsertActivity\
RecyclerView and adapter\
RoomDatabase\
WorkScheduler/TimerWorker\
\
[Home](https://saiqi1999.github.io/cs5520project/)
