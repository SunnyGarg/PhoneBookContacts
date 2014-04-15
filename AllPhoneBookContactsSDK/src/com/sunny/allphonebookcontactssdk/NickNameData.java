package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class NickNameData implements Serializable {

	private static final long serialVersionUID = 1L;

	String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
