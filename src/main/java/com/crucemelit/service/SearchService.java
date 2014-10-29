package com.crucemelit.service;

import java.util.List;

import com.crucemelit.dto.Suggestion;

public interface SearchService {
	
	List<Suggestion> search(String term);
	
}
