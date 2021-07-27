package com.example.shoppingcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    public static String USERNAME = "";
    public static Map<Integer, Integer> ORDER = new HashMap<>();

    public static<K> void incrementValue(Map<K, Integer> map, K key)
    {
        Integer count = map.get(key);

        if (count == null) {
            map.put(key, 1);
        }
        else {
            map.put(key, count + 1);
        }
    }
}
