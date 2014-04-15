package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class WebSiteData implements Serializable {

	private static final long serialVersionUID = 1L;

	String websiteName, websiteType;

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getWebsiteType() {
		return websiteType;
	}

	public void setWebsiteType(String websiteType) {
		this.websiteType = websiteType;
	}

}
