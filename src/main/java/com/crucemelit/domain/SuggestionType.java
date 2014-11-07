package com.crucemelit.domain;

public enum SuggestionType {

	USER("contacts"), GYM("gyms");
	
	private String uriPart;
	
	private SuggestionType(String uriPart) {
		this.uriPart = uriPart;
	}
	
	public String getUri() {
		return this.uriPart;
	}
}
