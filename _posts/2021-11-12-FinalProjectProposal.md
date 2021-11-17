---
layout: default
title: final project proposal
---

I considered many possibilities and finally decided to add the following extensions to my todo list app:

# 1. Implement user interface design
   Currently the app is not decorated and many parts of it need to be improved.\
   Basically I just put a bunch of function together and that's what I've got.\
   I can also update the silent update method, which is not silent enough because it just keep flashing every time I asked it to update.\
   I'll do this by unbind the switch in MainActivity with direct update, but save the data we need in this activity, call the update method in onPause() method\
   I'll also add a confirm fragment before user delete their note, to make it user friendly.
   
# 2. Drag and reorder 
   I'm going to add a function that user can drag and reorder the notes he wrote.\
   I'll need to add an order number in the database to save changes, and I also need method to interact with the recycler view adapter.
   
# 3. Cloud backup - Firebase Realtime Database
   It's important that user could get his notes everywhere if he has multiple devices.\
   I'll create a cloud backup for the app database and make it accessible through account system.\
   If possible, I want the notice poped on any device with that account.
   
# 4. User account and OAuth
   As mentioned before, I'll build a user account system, users could login and get the notes they wrote.\
   But this make no sense without cloud database, why would some app need an account system if it has nothing to do with internet?\
   So I put them together.\
   For OAuth, which, in my opinion, is a powerful aspect with an app, which made the app a lot more easy to use. Wish I could add this, too.
