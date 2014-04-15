package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactData implements Serializable, Comparable<ContactData> {

	private static final long serialVersionUID = 1L;

	String contact_id;
	String contact_image;
	String nickName;
	String contact_name;

	ArrayList<OrganizationData> organizations;
	ArrayList<WebSiteData> websites;
	ArrayList<RelationData> relations;
	ArrayList<EmailData> emailAddresses;
	ArrayList<PhoneNumberData> phoneNumbers;
	ArrayList<ContactNote> notes;
	ArrayList<SipCall> sipAddresses;
	ArrayList<StructeredName> structeredNames;
	ArrayList<StructeredPostal> structeredpostals;
	ArrayList<ContactIMData> contact_ims;

	public ArrayList<ContactIMData> getContact_ims() {
		return contact_ims;
	}

	public void setContact_ims(ArrayList<ContactIMData> contact_ims) {
		this.contact_ims = contact_ims;
	}

	public ArrayList<StructeredPostal> getStructeredpostals() {
		return structeredpostals;
	}

	public void setStructeredpostals(
			ArrayList<StructeredPostal> structeredpostals) {
		this.structeredpostals = structeredpostals;
	}

	public ArrayList<EventData> getContactEvents() {
		return contactEvents;
	}

	public ArrayList<StructeredName> getStructeredNames() {
		return structeredNames;
	}

	public void setStructeredNames(ArrayList<StructeredName> structeredNames) {
		this.structeredNames = structeredNames;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public void setContactEvents(ArrayList<EventData> contactEvents) {
		this.contactEvents = contactEvents;
	}

	ArrayList<EventData> contactEvents;

	public ArrayList<SipCall> getSipAddresses() {
		return sipAddresses;
	}

	public void setSipAddresses(ArrayList<SipCall> sipAddresses) {
		this.sipAddresses = sipAddresses;
	}

	public ArrayList<ContactNote> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<ContactNote> notes) {
		this.notes = notes;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public ArrayList<RelationData> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<RelationData> relations) {
		this.relations = relations;
	}

	public ArrayList<WebSiteData> getWebsites() {
		return websites;
	}

	public ArrayList<OrganizationData> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(ArrayList<OrganizationData> organizations) {
		this.organizations = organizations;
	}

	public void setWebsites(ArrayList<WebSiteData> websites) {
		this.websites = websites;
	}

	public ArrayList<EmailData> getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(ArrayList<EmailData> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public ArrayList<PhoneNumberData> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(ArrayList<PhoneNumberData> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public String getContact_image() {
		return contact_image;
	}

	public void setContact_image(String contact_image) {
		this.contact_image = contact_image;
	}

	public String getContact_id() {
		return contact_id;
	}

	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}

	public int compareTo(ContactData another) {
		return contact_name.compareTo(another.contact_name);
	}

}
