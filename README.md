PhoneBookContacts
=================

As the Android contact database has multiple join tables therefore it is very complex to get all the fields (multiple phone numbers, multiple email ids etc) for a particular contact. This SDK will facilitate the access of all contacts (along with all fields) )which are present in user's phonebook.


Steps to integrate this SDK.

Step 1 -> 

    Add below permissions in manifest.xml
    
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.WRITE_CONTACTS" />
