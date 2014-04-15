package com.sunny.allphonebookcontactssdk;

import java.io.Serializable;

public class EventData implements Serializable {

	private static final long serialVersionUID = 1L;

	String event_type, event_start_date;

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public String getEvent_start_date() {
		return event_start_date;
	}

	public void setEvent_start_date(String event_start_date) {
		this.event_start_date = event_start_date;
	}

}
