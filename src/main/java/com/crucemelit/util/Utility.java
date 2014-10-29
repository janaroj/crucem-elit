package com.crucemelit.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.StringUtils;

import com.crucemelit.domain.SuggestionType;
import com.crucemelit.dto.Suggestion;
import com.crucemelit.model.Suggestable;

public final class Utility {

    public static final List<?> EMPTY_LIST = new ArrayList<>();

    @SafeVarargs
    public static final <T> List<T> getUniqueList(List<T>... lists) {
        Set<T> set = new HashSet<>();
        for (List<T> list : lists) {
            if (list == null) {
                continue;
            }
            set.addAll(list);
        }
        return new ArrayList<T>(set);
    }

    public static final byte[] getBytesFromStream(InputStream stream) throws IOException {
        return IOUtils.toByteArray(stream);
    }

    public static final String getImgSourceFromBytes(byte[] bytes) {
        return new String(Base64.encode(bytes));
    }

    public static final List<Suggestion> getSuggestions(List<? extends Suggestable> suggestables, SuggestionType type) {
        List<Suggestion> suggestions = new ArrayList<>();
        for (Suggestable suggestable : suggestables) {
            suggestions.add(new Suggestion(suggestable.getId(), suggestable.getName(), type));
        }
        return suggestions;
    }

    public static final String formatStrings(String... strings) {
        List<String> stringList = new ArrayList<>(Arrays.asList(strings));
        stringList.removeAll(Collections.singleton(null));
        return StringUtils.collectionToDelimitedString(stringList, " ");
    }
}
