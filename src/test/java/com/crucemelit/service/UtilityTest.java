package com.crucemelit.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.crucemelit.util.Utility;

public class UtilityTest {

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
    public void getUniqueListWithNullPassed() {
        List<Integer> list1 = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });
        assertEquals(list1, Utility.getUniqueList(list1, null));
    }

    @Test
    public void getUniqueListWithNothingPassed() {
        assertEquals(Utility.EMPTY_LIST, Utility.getUniqueList());
    }

}
