package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class SipCall implements Serializable {

	private static final long serialVersionUID = 1L;

	String sip_address, sip_address_type;

	public String getSip_address() {
		return sip_address;
	}

	public void setSip_address(String sip_address) {
		this.sip_address = sip_address;
	}

	public String getSip_address_type() {
		return sip_address_type;
	}

	public void setSip_address_type(String sip_address_type) {
		this.sip_address_type = sip_address_type;
	}

}
