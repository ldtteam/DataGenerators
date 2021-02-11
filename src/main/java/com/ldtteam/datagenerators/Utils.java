package com.ldtteam.datagenerators;

import java.util.Map;
import java.util.TreeMap;

/**
 * Utility methods
 */
public class Utils
{
    private Utils()
    {
    }

    public static <K, V> Map<K, V> ensureTreeMap(final Map<K, V> map)
    {
        return map instanceof TreeMap ? map : new TreeMap<>(map);
    }
}
