package com.crucemelit.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Utility {

    @SafeVarargs
    public static final <T> List<T> getUniqueList(List<T>... lists) {
        Set<T> set = new HashSet<>();
        for (List<T> list : lists) {
            set.addAll(list);
        }
        return new ArrayList<T>(set);
    }

}
