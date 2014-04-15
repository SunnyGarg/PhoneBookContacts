package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class StructeredName implements Serializable {
	private static final long serialVersionUID = 1L;

	String family_name, given_name, display_name, middle_name;
	String phonetic_family_name, phonetic_middle_name, phonetic_given_name;
	String suffix, prefix;

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getPhonetic_family_name() {
		return phonetic_family_name;
	}

	public void setPhonetic_family_name(String phonetic_family_name) {
		this.phonetic_family_name = phonetic_family_name;
	}

	public String getPhonetic_middle_name() {
		return phonetic_middle_name;
	}

	public void setPhonetic_middle_name(String phonetic_middle_name) {
		this.phonetic_middle_name = phonetic_middle_name;
	}

	public String getPhonetic_given_name() {
		return phonetic_given_name;
	}

	public void setPhonetic_given_name(String phonetic_given_name) {
		this.phonetic_given_name = phonetic_given_name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
