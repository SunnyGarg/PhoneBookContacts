package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class OrganizationData implements Serializable {

	private static final long serialVersionUID = 1L;

	String orgnizationName, orgnizationType, orgnizationOfficeLocation,
			orgnizationJobDescription, orgnizationDept;

	public String getOrgnizationName() {
		return orgnizationName;
	}

	public void setOrgnizationName(String orgnizationName) {
		this.orgnizationName = orgnizationName;
	}

	public String getOrgnizationType() {
		return orgnizationType;
	}

	public void setOrgnizationType(String orgnizationType) {
		this.orgnizationType = orgnizationType;
	}

	public String getOrgnizationOfficeLocation() {
		return orgnizationOfficeLocation;
	}

	public void setOrgnizationOfficeLocation(String orgnizationOfficeLocation) {
		this.orgnizationOfficeLocation = orgnizationOfficeLocation;
	}

	public String getOrgnizationJobDescription() {
		return orgnizationJobDescription;
	}

	public void setOrgnizationJobDescription(String orgnizationJobDescription) {
		this.orgnizationJobDescription = orgnizationJobDescription;
	}

	public String getOrgnizationDept() {
		return orgnizationDept;
	}

	public void setOrgnizationDept(String orgnizationDept) {
		this.orgnizationDept = orgnizationDept;
	}

}
