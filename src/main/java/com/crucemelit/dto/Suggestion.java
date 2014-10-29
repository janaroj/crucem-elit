package com.crucemelit.dto;

import lombok.Data;

import com.crucemelit.domain.SuggestionType;

public @Data class Suggestion {
	
	private final long id;
	
	private final String text;
	
	private final SuggestionType type;
	
	public String getType() {
		return type.getUri();
	}
	
}
