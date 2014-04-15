package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class PhoneNumberData implements Serializable {

	private static final long serialVersionUID = 1L;

	String phoneNumber, phoneNumberType;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumberType() {
		return phoneNumberType;
	}

	public void setPhoneNumberType(String phoneNumberType) {
		this.phoneNumberType = phoneNumberType;
	}

}
