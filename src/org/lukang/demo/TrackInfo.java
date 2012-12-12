package org.lukang.demo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TrackInfo {
	
	private String time;
	
	private String context;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	
}
