package com.crucemelit.domain;

public enum SuggestionType {

	USER("users"), GYM("gyms");
	
	private String uriPart;
	
	private SuggestionType(String uriPart) {
		this.uriPart = uriPart;
	}
	
	public String getUri() {
		return this.uriPart;
	}
}
