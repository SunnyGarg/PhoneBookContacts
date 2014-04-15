package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class RelationData implements Serializable {

	private static final long serialVersionUID = 1L;

	String relationName, relationType;

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

}
