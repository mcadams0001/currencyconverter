package org.adam.currency.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection Helper
 */
public class CollectionHelper {

    /**
     * Creates defensive copy of list if
     * @param list source list to be copied.
     * @param <T> type of list.
     * @return defensive copy of non-empty list or empty List.
     */
    public static <T> List<T> defensiveCopy(List<T> list) {
        return list != null && list.size() > 0 ? new ArrayList<>(list) : new ArrayList<>();
    }
}
