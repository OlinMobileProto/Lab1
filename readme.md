## Lab 1 Expectations

### Assignment

You should create a grocery list application. A user should be able to type out an entry and add this item to a list that is displayed on the screen.

A user should also be able to delete items from this list and edit items once they are in the list. The list does not have to be persisted after the application stops, however, that is a stretch goal. For deleting and editing we recommend using an AlertDialog to show the options to the user.

### Rubric

- Functionality: 75%
	- Display list of notes: 10% (Of total)
	- Ability to add notes: 25%
	- Ability to delete notes: 20%
	- Ability to edit notes: 20%


- Code Quality: 25%
	- Following good MVC practices 12.5%
	- Following Android practices 12.5%

### Details

You should have the following class at a minimum (potentially more):

- MainActivity, subclass of Activity

For information about implementing an AlertDialog, see here: http://www.tutorialspoint.com/android/android_alert_dialoges.htm. Specifically, see the section of modified code of MainActivity.java. The contents of the function named *open* will be most useful.

For more information about implementing an ArrayAdapter, see here: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView#using-a-basic-arrayadapter. Only the "Using a Basic ArrayAdapter" section is relevant. 

Please, **please** email us if you have questions at any time. 

### Stretch goal

A grocery list app doesn't have any real use if items are erased when you close the application. Figure out a way to persist notes on your device. Likely this will mean using a database.
Android offers functionality to get access to a local SQLite database on the particular phone. More information on that can be found here http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html

### Stretch goal #2

Wouldn't a grocery list be more useful if the user could check items off? Figure out a way to mark notes as done or undone and display this information to the user.

**Note:** for this strech goal, you will have to subclass ArrayAdapter to do anything more than displaying text.

For more information on custom ArrayAdapters, see here: https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView#using-a-custom-arrayadapter. Read the section called "Using a Custom ArrayAdapter" up to but not including "Populating Data into ListView". Using a class to represent a grocery item is not required (but recommended, and probably necessary if you do stretch goal #2). Therefore, if you don't use a custom data class, it should read *extends ArrayAdapter*, but otherwise is very similar.
