package com.crucemelit.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.crucemelit.domain.SuggestionType;
import com.crucemelit.dto.Suggestion;
import com.crucemelit.model.Suggestable;
import com.crucemelit.model.User;
import com.crucemelit.util.Utility;

public class UtilityTest {
	
	private final int ID = 11;
	private final String NAME = "test";
	private final SuggestionType type = SuggestionType.USER;
	
    @Test
    public void getUniqueListTest() {
        List<Integer> list1 = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });
        List<Integer> list2 = Arrays.asList(new Integer[] { 3, 4, 5, 6, 7 });
        List<Integer> list3 = Arrays.asList(new Integer[] { 6, 7, 8, 9, 10 });

        List<Integer> uniqueList = Utility.getUniqueList(list1, list2, list3);
        Set<Integer> set = new HashSet<>(uniqueList);

        assertEquals(set.size(), uniqueList.size());
    }

    @Test
    public void getUniqueListWithNullPassedTest() {
        List<Integer> list1 = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });
        assertEquals(list1, Utility.getUniqueList(list1, null));
        assertEquals(Utility.EMPTY_LIST, Utility.getUniqueList(null, null));
    }

    @Test
    public void getUniqueListWithNothingPassedTest() {
        assertEquals(Utility.EMPTY_LIST, Utility.getUniqueList());
    }
    
    @Test
    public void getSuggestionsWithNullPassedTest() {
    	assertEquals(Utility.EMPTY_LIST, Utility.getSuggestions(null, null));
    }
    
    @Test
    public void getSuggestionsWithEmptyPassedTest() {
    	assertEquals(Utility.EMPTY_LIST, Utility.getSuggestions(new ArrayList<Suggestable>(), null));
    }
    
    @Test
    public void getSuggestionsTest() {
    	Suggestion suggestion = Utility.getSuggestions(createSuggestable(), type).get(0);
    	assertEquals(NAME, suggestion.getText());
    	assertEquals(ID, suggestion.getId());
    	assertEquals(type.getUri(), suggestion.getType());
    	
    }

	private List<Suggestable> createSuggestable() {
		List<Suggestable> suggestables = new ArrayList<>();
    	suggestables.add(createUser());
		return suggestables;
	}

	private User createUser() {
		User user = new User();
    	user.setId(ID);
    	user.setFirstName(NAME);
		return user;
	}

	@Test
	public void formatStringsMultipleStringsTest() {
		assertEquals(NAME + " " + NAME, Utility.formatStrings(null,NAME, "", null, NAME));
	}
	
	@Test
	public void formatStringsOneStringTest() {
		assertEquals(NAME, Utility.formatStrings(NAME));
	}
	
	@Test
	public void formatStringsEmptyAndNullTest() {
		assertEquals("", Utility.formatStrings("", null));
	}
	
}
