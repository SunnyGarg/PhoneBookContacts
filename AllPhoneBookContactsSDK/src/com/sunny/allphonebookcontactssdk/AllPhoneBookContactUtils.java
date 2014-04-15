package com.sunny.allphonebookcontactssdk;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.provider.ContactsContract.CommonDataKinds.SipAddress;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.util.Log;

public class AllPhoneBookContactUtils {

	private static AllPhoneBookContactUtils mAllPhonebookContactutils;

	private AllPhoneBookContactUtils() {

	}

	public final static AllPhoneBookContactUtils getInstance() {
		if (mAllPhonebookContactutils == null)
			mAllPhonebookContactutils = new AllPhoneBookContactUtils();

		return mAllPhonebookContactutils;
	}

	public final ArrayList<ContactData> getAllPhoneBookContacts(Context context) {

		ArrayList<ContactData> contactDataArray = new ArrayList<ContactData>();
		try {
			final Cursor phones = context.getContentResolver().query(
					ContactsContract.RawContacts.CONTENT_URI, null, null, null,
					null);
			phones.moveToFirst();
			contactDataArray.ensureCapacity(phones.getCount());
			while (phones.moveToNext()) {

				String contact_id = phones
						.getString(phones
								.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));

				int integer_contact_id = Integer.parseInt(contact_id);

				ContactData contactData = new ContactData();
				contactData.setContact_id(contact_id);
				contactData = getNameAndPhotoFromContact_Id(context,
						integer_contact_id, contactData);
				if (contactData != null) {
					contactData = getAllEmailAddressesFromContact_Id(context,
							integer_contact_id, contactData);
					contactData = getAllPhoneNumbersForContact(context,
							integer_contact_id, contactData);
					contactData = getContactNickName(context,
							integer_contact_id, contactData);
					contactData = getContactOrganization(context,
							integer_contact_id, contactData);
					contactData = getContactRelation(context,
							integer_contact_id, contactData);
					contactData = getContactWebsite(context,
							integer_contact_id, contactData);
					contactData = getContactNotes(context, integer_contact_id,
							contactData);
					contactData = getContactSipAddresses(context,
							integer_contact_id, contactData);
					contactData = getContactEvents(context, integer_contact_id,
							contactData);
					contactData = getContactstructeredName(context,
							integer_contact_id, contactData);
					contactData = getContactstructeredAddress(context,
							integer_contact_id, contactData);
					contactDataArray.add(contactData);
				}
			}
			phones.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(contactDataArray);
		return contactDataArray;

	}

	private final ContactData getAllPhoneNumbersForContact(Context context,
			int contactId, ContactData contactData) {
		try {
			final String[] projection = new String[] { Phone.NUMBER, Phone.TYPE };

			final Cursor phone = context.getContentResolver().query(
					Phone.CONTENT_URI, projection, Data.CONTACT_ID + "=?",
					new String[] { String.valueOf(contactId) }, null);

			if (phone.moveToFirst()) {
				final int contactNumberColumnIndex = phone
						.getColumnIndex(Phone.NUMBER);
				final int contactTypeColumnIndex = phone
						.getColumnIndex(Phone.TYPE);

				ArrayList<PhoneNumberData> phoneMnumberDataarray = new ArrayList<PhoneNumberData>();

				while (!phone.isAfterLast()) {
					final String number = phone
							.getString(contactNumberColumnIndex);
					final int type = phone.getInt(contactTypeColumnIndex);

					System.out
							.println("mmmmmContacts :getAllPhoneNumbersForContact "
									+ contactId + " ; " + number + " " + type);

					PhoneNumberData numberData = new PhoneNumberData();
					numberData.setPhoneNumber(number);
					numberData.setPhoneNumberType(context.getString(Phone
							.getTypeLabelResource(type)));

					phoneMnumberDataarray.add(numberData);

					contactData.setPhoneNumbers(phoneMnumberDataarray);
					phone.moveToNext();
				}

			}
			phone.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	private final ContactData getContactWebsite(Context context, int contactId,
			ContactData contactData) {
		try {
			final String[] projection = new String[] {
					ContactsContract.CommonDataKinds.Website.URL,
					ContactsContract.CommonDataKinds.Website.TYPE };

			final Cursor things = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							Website.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE },
							null);

			if (things.moveToFirst()) {
				final int contactWebsiteColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL);
				final int contactWebsiteTypeColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE);

				ArrayList<WebSiteData> websites = new ArrayList<WebSiteData>(
						things.getCount());
				while (!things.isAfterLast()) {
					final String website = things
							.getString(contactWebsiteColumnIndex);
					final String website_type = things
							.getString(contactWebsiteTypeColumnIndex);

					WebSiteData data = new WebSiteData();
					data.setWebsiteName(website);
					data.setWebsiteType(website_type);

					websites.add(data);

					System.out.println("mmmmmContacts :geteXTRA website  "
							+ contactId + " ; " + website + " ; "
							+ website_type);
					things.moveToNext();
				}

				contactData.setWebsites(websites);

			}
			things.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (contactData.getWebsites() != null
				&& contactData.getWebsites().size() > 0)
			System.out.println("mmmmmContacts :geteXTRA website  ad "
					+ contactData.getWebsites().get(0).getWebsiteName());
		return contactData;
	}

	private final ContactData getContactSipAddresses(Context context,
			int contactId, ContactData contactData) {
		try {
			final String[] projection = new String[] { SipAddress.SIP_ADDRESS,
					SipAddress.TYPE };

			final Cursor sipaddressCursor = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							SipAddress.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.SipAddress.CONTENT_ITEM_TYPE },
							null);

			if (sipaddressCursor.moveToFirst()) {
				final int contactSipAddressrColumnIndex = sipaddressCursor
						.getColumnIndex(SipAddress.SIP_ADDRESS);
				final int contactSipAddressTypeColumnIndex = sipaddressCursor
						.getColumnIndex(SipAddress.TYPE);

				ArrayList<SipCall> sipCallDataarray = new ArrayList<SipCall>();

				while (!sipaddressCursor.isAfterLast()) {
					final String sip_address = sipaddressCursor
							.getString(contactSipAddressrColumnIndex);
					final int type = sipaddressCursor
							.getInt(contactSipAddressTypeColumnIndex);

					System.out.println("mmmmmContacts :getContactSipAddresses "
							+ contactId + " ; " + sip_address + " " + type);

					SipCall sipCallData = new SipCall();
					sipCallData.setSip_address(sip_address);
					sipCallData.setSip_address_type(context
							.getString(SipAddress.getTypeLabelResource(type)));

					sipCallDataarray.add(sipCallData);

					contactData.setSipAddresses(sipCallDataarray);
					sipaddressCursor.moveToNext();
				}

			}
			sipaddressCursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	private final ContactData getContactRelation(Context context,
			int contactId, ContactData contactData) {
		try {
			final String[] projection = new String[] {
					ContactsContract.CommonDataKinds.Relation.NAME,
					ContactsContract.CommonDataKinds.Relation.TYPE };

			final Cursor things = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							Relation.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.Relation.CONTENT_ITEM_TYPE },
							null);

			if (things.moveToFirst()) {
				final int contactRelationColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Relation.NAME);
				final int contactRelationTypeColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Relation.TYPE);
				ArrayList<RelationData> relations = new ArrayList<RelationData>(
						things.getCount());
				while (!things.isAfterLast()) {
					final String relation = things
							.getString(contactRelationColumnIndex);
					final int relationType = things
							.getInt(contactRelationTypeColumnIndex);
					RelationData data = new RelationData();
					data.setRelationName(relation);
					data.setRelationType(context.getString(Relation
							.getTypeLabelResource(relationType)));

					relations.add(data);

					System.out.println("mmmmmContacts :geteXTRA relation "
							+ contactId + " ; " + relation + " ; "
							+ relationType);
					things.moveToNext();
				}

				contactData.setRelations(relations);
			}
			things.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	private final ContactData getContactOrganization(Context context,
			int contactId, ContactData contactData) {
		try {
			final String[] projection = new String[] {
					ContactsContract.CommonDataKinds.Organization.COMPANY,
					ContactsContract.CommonDataKinds.Organization.TITLE,
					ContactsContract.CommonDataKinds.Organization.DEPARTMENT,
					ContactsContract.CommonDataKinds.Organization.JOB_DESCRIPTION,
					ContactsContract.CommonDataKinds.Organization.OFFICE_LOCATION };

			final Cursor things = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							Organization.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE },
							null);

			if (things.moveToFirst()) {
				final int contactOrgNameColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY);
				final int contactOrgTypeTypeColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE);
				final int contactOrgDeptTypeColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT);
				final int contactOrgJobDescTypeColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Organization.JOB_DESCRIPTION);
				final int contactOrgOfficeLocationTypeColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Organization.OFFICE_LOCATION);

				while (!things.isAfterLast()) {
					final String orgnization = things
							.getString(contactOrgNameColumnIndex);
					final int orgnizationType = things
							.getInt(contactOrgTypeTypeColumnIndex);
					final String orgnizationDept = things
							.getString(contactOrgDeptTypeColumnIndex);
					final String orgnizationJobDesc = things
							.getString(contactOrgJobDescTypeColumnIndex);
					final String orgnizationOfficeLocation = things
							.getString(contactOrgOfficeLocationTypeColumnIndex);

					OrganizationData data = new OrganizationData();
					data.setOrgnizationDept(orgnizationDept);
					data.setOrgnizationJobDescription(orgnizationJobDesc);
					data.setOrgnizationName(orgnization);
					data.setOrgnizationOfficeLocation(orgnizationOfficeLocation);
					data.setOrgnizationType(context.getString(Organization
							.getTypeLabelResource(orgnizationType)));
					contactData.setOrganization(data);

					System.out.println("mmmmmContacts :geteXTRA relation "
							+ contactId + " ; " + orgnization + " ; "
							+ orgnizationType + " ; " + orgnizationDept + " ; "
							+ orgnizationJobDesc + " ; "
							+ orgnizationOfficeLocation);
					things.moveToNext();
				}

			}
			things.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	private final ContactData getContactstructeredName(Context context,
			int contactId, ContactData contactData) {
		try {
			final String[] projection = new String[] {
					ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
					ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
					ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
					ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
					ContactsContract.CommonDataKinds.StructuredName.PHONETIC_FAMILY_NAME,
					ContactsContract.CommonDataKinds.StructuredName.PHONETIC_GIVEN_NAME,
					ContactsContract.CommonDataKinds.StructuredName.PHONETIC_MIDDLE_NAME,
					ContactsContract.CommonDataKinds.StructuredName.PREFIX,
					ContactsContract.CommonDataKinds.StructuredName.SUFFIX };

			final Cursor structCursor = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							StructuredName.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE },
							null);

			if (structCursor.moveToFirst()) {
				final int display_nameColumnIndex = structCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME);
				final int family_nameColumnIndex = structCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);
				final int given_nameColumnIndex = structCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
				final int middle_nameColumnIndex = structCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME);
				final int phonetic_family_nameColumnIndex = structCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_FAMILY_NAME);
				final int phonetic_given_nameColumnIndex = structCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_GIVEN_NAME);
				final int phonetic_middle_nameColumnIndex = structCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHONETIC_MIDDLE_NAME);
				final int prefix_nameColumnIndex = structCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PREFIX);
				final int suffic_nameColumnIndex = structCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.SUFFIX);

				ArrayList<StructeredName> s_names_array = new ArrayList<StructeredName>(
						structCursor.getCount());

				while (!structCursor.isAfterLast()) {

					StructeredName s_name = new StructeredName();
					s_name.setDisplay_name(structCursor
							.getString(display_nameColumnIndex));
					s_name.setFamily_name(structCursor
							.getString(family_nameColumnIndex));
					s_name.setGiven_name(structCursor
							.getString(given_nameColumnIndex));
					s_name.setMiddle_name(structCursor
							.getString(middle_nameColumnIndex));
					s_name.setPhonetic_family_name(structCursor
							.getString(phonetic_family_nameColumnIndex));
					s_name.setPhonetic_given_name(structCursor
							.getString(phonetic_given_nameColumnIndex));
					s_name.setPhonetic_middle_name(structCursor
							.getString(phonetic_middle_nameColumnIndex));
					s_name.setPrefix(structCursor
							.getString(prefix_nameColumnIndex));
					s_name.setSuffix(structCursor
							.getString(suffic_nameColumnIndex));

					s_names_array.add(s_name);

					structCursor.moveToNext();
				}
				contactData.setStructeredNames(s_names_array);
			}
			structCursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	private final ContactData getContactstructeredAddress(Context context,
			int contactId, ContactData contactData) {
		try {
			final String[] projection = new String[] {
					ContactsContract.CommonDataKinds.StructuredPostal.CITY,
					ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY,
					ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
					ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD,
					ContactsContract.CommonDataKinds.StructuredPostal.POBOX,
					ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE,
					ContactsContract.CommonDataKinds.StructuredPostal.REGION,
					ContactsContract.CommonDataKinds.StructuredPostal.STREET,
					ContactsContract.CommonDataKinds.StructuredPostal.TYPE };

			final Cursor structPostalCursor = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							StructuredPostal.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE },
							null);

			if (structPostalCursor.moveToFirst()) {
				final int cityColumnIndex = structPostalCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY);
				final int countryColumnIndex = structPostalCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY);
				final int formatted_addressColumnIndex = structPostalCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS);
				final int neighborhoodColumnIndex = structPostalCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD);
				final int postboxColumnIndex = structPostalCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX);
				final int postcodeColumnIndex = structPostalCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE);
				final int regionColumnIndex = structPostalCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION);
				final int streetColumnIndex = structPostalCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET);
				final int typeColumnIndex = structPostalCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE);

				ArrayList<StructeredPostal> s_address_array = new ArrayList<StructeredPostal>(
						structPostalCursor.getCount());

				while (!structPostalCursor.isAfterLast()) {

					StructeredPostal s_address = new StructeredPostal();
					s_address.setCity(structPostalCursor
							.getString(cityColumnIndex));
					s_address.setFormatted_address(structPostalCursor
							.getString(formatted_addressColumnIndex));
					s_address.setCountry(structPostalCursor
							.getString(countryColumnIndex));
					s_address.setNeighborhood(structPostalCursor
							.getString(neighborhoodColumnIndex));
					s_address.setPostbox(structPostalCursor
							.getString(postboxColumnIndex));
					s_address.setPostcode(structPostalCursor
							.getString(postcodeColumnIndex));
					s_address.setRegion(structPostalCursor
							.getString(regionColumnIndex));
					s_address.setStreet(structPostalCursor
							.getString(streetColumnIndex));
					s_address.setType(context.getString(structPostalCursor
							.getInt(typeColumnIndex)));

					s_address_array.add(s_address);

					structPostalCursor.moveToNext();
				}
				contactData.setStructeredpostals(s_address_array);
			}
			structPostalCursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	private final ContactData getContactNickName(Context context,
			int contactId, ContactData contactData) {
		try {
			final String[] projection = new String[] {
					ContactsContract.CommonDataKinds.Nickname.NAME,
					ContactsContract.CommonDataKinds.Nickname.TYPE };

			final Cursor things = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							Nickname.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE },
							null);

			if (things.moveToFirst()) {
				final int contactNickNameColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME);
				while (!things.isAfterLast()) {
					final String nick_name = things
							.getString(contactNickNameColumnIndex);

					contactData.setNickName(nick_name);

					System.out.println("mmmmmContacts :geteXTRA nickname "
							+ contactId + " ; " + nick_name + " ; ");
					things.moveToNext();
				}

			}
			things.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	private final ContactData getContactNotes(Context context, int contactId,
			ContactData contactData) {
		try {
			final String[] projection = new String[] { ContactsContract.CommonDataKinds.Note.NOTE };

			final Cursor things = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							Note.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE },
							null);
			ArrayList<ContactNote> contactNotes = new ArrayList<ContactNote>(
					things.getCount());
			if (things.moveToFirst()) {
				final int contactNoteColumnIndex = things
						.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE);
				while (!things.isAfterLast()) {
					final String contact_note = things
							.getString(contactNoteColumnIndex);

					ContactNote note = new ContactNote();
					note.setNote(contact_note);

					contactNotes.add(note);
					System.out.println("mmmmmContacts :geteXTRA note "
							+ contactId + " ; " + contact_note);

					things.moveToNext();
				}

			}
			contactData.setNotes(contactNotes);
			things.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	private final ContactData getAllEmailAddressesFromContact_Id(
			Context context, int contactId, ContactData contactData) {
		try {
			final String[] projection = new String[] { Email.DATA, Email.TYPE };

			final Cursor email = context.getContentResolver().query(
					Email.CONTENT_URI, projection, Data.CONTACT_ID + "=?",
					new String[] { String.valueOf(contactId) }, null);

			if (email.moveToFirst()) {
				final int contactEmailColumnIndex = email
						.getColumnIndex(Email.DATA);
				final int contactTypeColumnIndex = email
						.getColumnIndex(Email.TYPE);

				ArrayList<EmailData> emailDatAarray = new ArrayList<EmailData>();

				while (!email.isAfterLast()) {
					final String address = email
							.getString(contactEmailColumnIndex);
					final int type = email.getInt(contactTypeColumnIndex);

					EmailData emailData = new EmailData();
					emailData.setEmailAddress(address);
					emailData.setEmailType(context.getString(Email
							.getTypeLabelResource(type)));
					emailDatAarray.add(emailData);

					contactData.setEmailAddresses(emailDatAarray);
					email.moveToNext();
				}

			}
			email.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	private final ContactData getNameAndPhotoFromContact_Id(Context context,
			int contactId, ContactData contactData) {
		try {
			final String[] projection = new String[] { Contacts.DISPLAY_NAME,
					Contacts.PHOTO_ID };
			final Cursor contact = context.getContentResolver().query(
					Contacts.CONTENT_URI, projection, Contacts._ID + "=?",
					new String[] { String.valueOf(contactId) }, null);
			if (contact.moveToFirst()) {
				final String name = contact.getString(contact
						.getColumnIndex(Contacts.DISPLAY_NAME));
				final String photoId = contact.getString(contact
						.getColumnIndex(Contacts.PHOTO_ID));
				if (name != null && name.length() > 0
						&& !name.equalsIgnoreCase("null")) {
					System.out.println("mmmmmContacts : " + contactId + " ; "
							+ name + " " + photoId);
					contactData.setContact_name(name);
					contactData.setContact_image(photoId);
				} else
					return null;
				contact.close();
			}
			contact.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

	public Bitmap queryContactBitmap(Context context, String photoId) {
		try {
			if (photoId != null && photoId.length() > 0) {
				final Cursor photo = context.getContentResolver().query(
						Data.CONTENT_URI, new String[] { Photo.PHOTO },
						Data._ID + "=?", new String[] { photoId }, null);

				final Bitmap photoBitmap;
				if (photo.moveToFirst()) {
					byte[] photoBlob = photo.getBlob(photo
							.getColumnIndex(Photo.PHOTO));
					photoBitmap = BitmapFactory.decodeByteArray(photoBlob, 0,
							photoBlob.length);
				} else {
					photoBitmap = null;
				}
				photo.close();
				return photoBitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String generateJson(Context context) {
		ArrayList<ContactData> contactsList = getAllPhoneBookContacts(context);
		if (contactsList != null && contactsList.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (ContactData data : contactsList) {
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("contact_id", data.getContact_id());
					jsonObject.put("contact_name", data.getContact_name());
					jsonObject.put("contact_nick_name", data.getNickName());
					// /////////////
					JSONObject organizationObject = new JSONObject();
					if (data.getOrganization() != null) {
						organizationObject.put("organization_type", data
								.getOrganization().getOrgnizationType());
						organizationObject.put("organization_office_location",
								data.getOrganization()
										.getOrgnizationOfficeLocation());
						organizationObject.put("organization_name", data
								.getOrganization().getOrgnizationName());
						organizationObject.put("organization_job_description",
								data.getOrganization()
										.getOrgnizationJobDescription());
						organizationObject.put("organization_department", data
								.getOrganization().getOrgnizationDept());
						jsonObject.put("organization", organizationObject);
					} else
						jsonObject.put("organization", "");
					// /////////////

					// /////////////////////////

					JSONArray websitesArray = new JSONArray();
					ArrayList<WebSiteData> websites = data.getWebsites();
					System.out.println("mmmmwebsites : ");
					if (websites != null && websites.size() > 0) {
						System.out.println("mmmmwebsites : " + websites.size());
						for (WebSiteData websitedata : websites) {
							JSONObject websiteJsonObject = new JSONObject();

							websiteJsonObject.put("website_name",
									websitedata.getWebsiteName());
							websiteJsonObject.put("website_type",
									websitedata.getWebsiteType());

							websitesArray.put(websiteJsonObject);
						}

						jsonObject.put("website", websitesArray);

					} else
						jsonObject.put("website", "");

					// /////////////////////////

					// /////////////////////////

					JSONArray s_names_array = new JSONArray();
					ArrayList<StructeredName> struct_names = data
							.getStructeredNames();
					System.out.println("mmmms_names : ");
					if (struct_names != null && struct_names.size() > 0) {
						System.out.println("mmmmwebsites : "
								+ struct_names.size());
						for (StructeredName struct_name_data : struct_names) {
							JSONObject struct_name_JsonObject = new JSONObject();

							struct_name_JsonObject.put("given_name",
									struct_name_data.getGiven_name());

							struct_name_JsonObject.put("middle_name",
									struct_name_data.getMiddle_name());

							struct_name_JsonObject.put("display_name",
									struct_name_data.getDisplay_name());

							struct_name_JsonObject.put("family_name",
									struct_name_data.getFamily_name());

							struct_name_JsonObject.put("phonetic_family_name",
									struct_name_data.getPhonetic_family_name());

							struct_name_JsonObject.put("phonetic_middle_name",
									struct_name_data.getPhonetic_middle_name());

							struct_name_JsonObject.put("phonetic_given_name",
									struct_name_data.getPhonetic_given_name());

							struct_name_JsonObject.put("prefix",
									struct_name_data.getPrefix());

							struct_name_JsonObject.put("suffix",
									struct_name_data.getSuffix());

							s_names_array.put(struct_name_JsonObject);
						}

						jsonObject.put("structered_name", s_names_array);

					} else
						jsonObject.put("structered_name", "");

					// /////////////////////////

					// /////////////////////////

					JSONArray s_addresses_array = new JSONArray();
					ArrayList<StructeredPostal> struct_addresses = data
							.getStructeredpostals();
					System.out.println("mmmms_names : ");
					if (struct_addresses != null && struct_addresses.size() > 0) {
						System.out.println("mmmmwebsites : "
								+ struct_addresses.size());
						for (StructeredPostal struct_postal_data : struct_addresses) {
							JSONObject struct_address_JsonObject = new JSONObject();

							struct_address_JsonObject.put("given_name",
									struct_postal_data.getCity());

							struct_address_JsonObject.put("middle_name",
									struct_postal_data.getCountry());

							struct_address_JsonObject.put("display_name",
									struct_postal_data.getFormatted_address());

							struct_address_JsonObject.put("family_name",
									struct_postal_data.getNeighborhood());

							struct_address_JsonObject.put(
									"phonetic_family_name",
									struct_postal_data.getPostbox());

							struct_address_JsonObject.put(
									"phonetic_middle_name",
									struct_postal_data.getPostcode());

							struct_address_JsonObject.put(
									"phonetic_given_name",
									struct_postal_data.getRegion());

							struct_address_JsonObject.put("prefix",
									struct_postal_data.getStreet());

							struct_address_JsonObject.put("suffix",
									struct_postal_data.getType());

							s_addresses_array.put(struct_address_JsonObject);
						}

						jsonObject.put("structered_address", s_addresses_array);

					} else
						jsonObject.put("structered_address", "");

					// /////////////////////////

					// /////////////////////////

					JSONArray relationsArray = new JSONArray();
					ArrayList<RelationData> relations = data.getRelations();
					System.out.println("mmmmwebsites : ");
					if (relations != null && relations.size() > 0) {
						System.out
								.println("mmmmwebsites : " + relations.size());
						for (RelationData relationdata : relations) {
							JSONObject relationJsonObject = new JSONObject();

							relationJsonObject.put("relation_name",
									relationdata.getRelationName());
							relationJsonObject.put("relation_type",
									relationdata.getRelationType());

							relationsArray.put(relationJsonObject);
						}

						jsonObject.put("relation", relationsArray);

					} else
						jsonObject.put("relation", "");

					// /////////////////////////

					// /////////////////////////

					JSONArray eventsArray = new JSONArray();
					ArrayList<EventData> events = data.getContactEvents();
					System.out.println("mmmmwebsites : ");
					if (events != null && events.size() > 0) {
						System.out.println("mmmmwebsites : " + events.size());
						for (EventData eventData : events) {
							JSONObject eventJsonObject = new JSONObject();

							eventJsonObject.put("event_start_date",
									eventData.getEvent_start_date());
							eventJsonObject.put("event_type",
									eventData.getEvent_type());

							eventsArray.put(eventJsonObject);
						}

						jsonObject.put("events", eventsArray);

					} else
						jsonObject.put("events", "");

					// /////////////////////////

					// /////////////////////////

					JSONArray emailsArray = new JSONArray();
					ArrayList<EmailData> emails = data.getEmailAddresses();
					System.out.println("mmmmwebsites : ");
					if (emails != null && emails.size() > 0) {
						System.out.println("mmmmwebsites : " + emails.size());
						for (EmailData emaildata : emails) {
							JSONObject emailJsonObject = new JSONObject();

							emailJsonObject.put("email_name",
									emaildata.getEmailAddress());
							emailJsonObject.put("email_type",
									emaildata.getEmailType());

							emailsArray.put(emailJsonObject);
						}

						jsonObject.put("email", emailsArray);

					} else
						jsonObject.put("email", "");

					// /////////////////////////

					// /////////////////////////

					JSONArray phoneNumbersArray = new JSONArray();
					ArrayList<PhoneNumberData> phoneNumbers = data
							.getPhoneNumbers();
					System.out.println("mmmmwebsites : ");
					if (phoneNumbers != null && phoneNumbers.size() > 0) {
						System.out.println("mmmmwebsites : "
								+ phoneNumbers.size());
						for (PhoneNumberData phoneNumberdata : phoneNumbers) {
							JSONObject phoneNumberJsonObject = new JSONObject();

							phoneNumberJsonObject.put("phone_number",
									phoneNumberdata.getPhoneNumber());
							phoneNumberJsonObject.put("phone_number_type",
									phoneNumberdata.getPhoneNumberType());

							phoneNumbersArray.put(phoneNumberJsonObject);
						}

						jsonObject.put("phone numbers", phoneNumbersArray);

					} else
						jsonObject.put("phone numbers", "");

					// /////////////////////////

					// /////////////////////////

					JSONArray sipAddresesArray = new JSONArray();
					ArrayList<SipCall> sipAddresses = data.getSipAddresses();
					System.out.println("mmmmsipaddresses : ");
					if (sipAddresses != null && sipAddresses.size() > 0) {
						System.out.println("mmmmwebsites : "
								+ sipAddresses.size());
						for (SipCall sipAddressdata : sipAddresses) {
							JSONObject sipCallJsonObject = new JSONObject();

							sipCallJsonObject.put("internet_call_address",
									sipAddressdata.getSip_address());
							sipCallJsonObject.put("internet_call_address_type",
									sipAddressdata.getSip_address_type());

							sipAddresesArray.put(sipCallJsonObject);
						}

						jsonObject.put("internet calls", sipAddresesArray);

					} else
						jsonObject.put("internet calls", "");

					// /////////////////////////

					// /////////////////////////

					JSONArray notesArray = new JSONArray();
					ArrayList<ContactNote> notes = data.getNotes();
					if (notes != null && notes.size() > 0) {
						for (ContactNote notedata : notes) {
							JSONObject noteJsonObject = new JSONObject();
							noteJsonObject.put("note", notedata.getNote());
							notesArray.put(noteJsonObject);
						}

						jsonObject.put("notes", notesArray);

					} else
						jsonObject.put("notes", "");

					// /////////////////////////

					jsonArray.put(jsonObject);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			JSONObject contactsObj = new JSONObject();
			try {
				contactsObj.put("contacts", jsonArray);
				Log.d("abcd", contactsObj.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return contactsObj.toString();
		}
		return null;
	}

	public void getContactIM(Context context, int contactId) {
		System.out.println("mmmmmContacts :getContactIM " + contactId
				+ " start");

		try {
			final String[] projection = new String[] {
					ContactsContract.CommonDataKinds.Im.PROTOCOL, Im.TYPE,
					Im.LABEL };

			final Cursor imCursor = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							Im.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE },
							null);

			if (imCursor.moveToFirst()) {
				final int contactIMProtocolColumnIndex = imCursor
						.getColumnIndex(Im.PROTOCOL);
				final int contactIMTypeColumnIndex = imCursor
						.getColumnIndex(Im.TYPE);
				final int contactIMCustomProtocolTypeColumnIndex = imCursor
						.getColumnIndex(Im.LABEL);
				// ArrayList<SipCall> sipCallDataarray = new
				// ArrayList<SipCall>();

				while (!imCursor.isAfterLast()) {
					final int protocol = imCursor
							.getInt(contactIMProtocolColumnIndex);
					final int type = imCursor.getInt(contactIMTypeColumnIndex);
					final String custom_protocol = imCursor
							.getString(contactIMCustomProtocolTypeColumnIndex);
					System.out.println("mmmmmContacts :getContactIM "
							+ contactId
							+ " ; "
							+ context.getString(Im
									.getProtocolLabelResource(protocol))
							+ "  ; "
							+ context.getString(Im.getTypeLabelResource(type))
							+ " ; " + custom_protocol);

					// SipCall sipCallData = new SipCall();
					// sipCallData.setSip_address(sip_address);
					// sipCallData.setSip_address_type(context
					// .getString(SipAddress.getTypeLabelResource(type)));
					//
					// sipCallDataarray.add(sipCallData);

					// contactData.setSipAddresses(sipCallDataarray);
					imCursor.moveToNext();
				}

			}
			imCursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return contactData;
	}

	private final ContactData getContactEvents(Context context, int contactId,
			ContactData contactData) {
		try {
			final String[] projection = new String[] {
					ContactsContract.CommonDataKinds.Event.START_DATE,
					ContactsContract.CommonDataKinds.Event.TYPE };

			final Cursor eventsCursor = context
					.getContentResolver()
					.query(Data.CONTENT_URI,
							projection,
							Event.CONTACT_ID + "=? AND "
									+ ContactsContract.Data.MIMETYPE + " = ?",
							new String[] {
									String.valueOf(contactId),
									ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE },
							null);
			ArrayList<EventData> contactEvents = new ArrayList<EventData>(
					eventsCursor.getCount());
			if (eventsCursor.moveToFirst()) {
				final int contactEventDateColumnIndex = eventsCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
				final int contactEventTypeColumnIndex = eventsCursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE);
				while (!eventsCursor.isAfterLast()) {
					final String event_date = eventsCursor
							.getString(contactEventDateColumnIndex);
					final int event_type = eventsCursor
							.getInt(contactEventTypeColumnIndex);

					EventData event_data = new EventData();
					event_data.setEvent_type(context.getString(Event
							.getTypeResource(event_type)));
					event_data.setEvent_start_date(event_date);

					contactEvents.add(event_data);
					System.out.println("mmmmmContacts :geteXTRA note "
							+ contactId + " ; " + event_date + " ; "
							+ event_type);

					eventsCursor.moveToNext();
				}

			}
			contactData.setContactEvents(contactEvents);
			eventsCursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactData;
	}

}
