package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class ContactNote implements Serializable {
	private static final long serialVersionUID = 1L;

	String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
