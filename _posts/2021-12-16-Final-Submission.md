---
layout: default
title: Final project submission, Application Manual & Release
---

Source code on [https://github.com/saiqi1999/cs5520project/tree/main/Final%20Submission](https://github.com/saiqi1999/cs5520project/tree/main/Final%20Submission)

Here's a powerpoint as a Doc
[Saiqi_FinalProjectPresentation_cs5520_fall2021.pptx](https://github.com/saiqi1999/cs5520project/files/7725912/SaiqiHe_FinalProjectPresentation_cs5520_fall2021.pptx)

Download the apk here 
[app-release.apk.zip](https://github.com/saiqi1999/cs5520project/files/7726045/app-release.apk.zip)

# 1. Implement user interface design
   I made the UI better, and add a switch pages for alarm clocks and timers\
   I have 5 float button now, they are : Google OAuth, Sign up, Delete all(red), Sign in, Add new\
   Set the background color so text will not "hide" in background\
   Sign in Activity added
   
# 2. Sort 
   The notes are now sorted according to create time, which is a little bit conflict with reorder method, so I implemented the sorting method only
   
# 3. Cloud backup - Firebase Firestore Database
   It's important that user could get his notes everywhere if he has multiple devices\
   I created a cloud backup for the app database and make it accessible through account system\
   It works as iCloud for "photos" app, automatically upload & download notes needed, linked with you account mentioned below
   
# 4. User account and OAuth
   As mentioned before, I built a user account system, users could login and get the notes they wrote\
   This worked as iCloud account for "photos" app, and a achieved it by firebase fireauth\
   I also implemented OAuth method, but it's not activated. It will be activated once I put it on Google playstore and get the SHA-1 fingerprint generated
   
 
