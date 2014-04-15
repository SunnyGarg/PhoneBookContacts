package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class EmailData implements Serializable {

	private static final long serialVersionUID = 1L;

	String emailAddress, emailType;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
}
