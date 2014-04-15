PhoneBookContacts
=================

In Andorid, Accessing the contacts informations from PhoneBook is much complex process. As the Android contact database has multiple join tables therefore it is very complex to get all the fields (multiple phone numbers, multiple email ids etc) for a particular contact. 

We have simplified this complex process.

Now a developer can get all contacts information in just 3 easy steps.


START

Step 1 -> 

    Add below permissions in manifest.xml
    
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.WRITE_CONTACTS" />
    
Step 2 -> 

    Get the instance of the class AllPhoneBookContactUtils.java as below explained.
    
    AllPhoneBookContactUtils.getInstance() -> return an instanceof the class AllPhoneBookContactUtils.java


Step 3 -> 

    Call the below method to get list of all PhoneBook contacts.
    
    AllPhoneBookContactUtils.getInstance().getAllPhoneBookContacts(
				context); -> Returns the ArrayList of objects of the class ContactData.java 
				
END




Note: ContactData.java implements Serializable and Comparable interfaces.

public class ContactData implements Serializable, Comparable<ContactData> {

}
