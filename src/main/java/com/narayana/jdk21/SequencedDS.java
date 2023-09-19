package com.narayana.jdk21;

import java.util.*;

public class SequencedDS {
    public static void main(String[] args) {
        var set = new LinkedHashSet<>();
        set.add("one");
        set.add("two");
        set.addFirst("zero");
        System.out.println("set1 " + set);
        SequencedSet<Object> reversed = set.reversed();
        System.out.println("set2 " + reversed);

        var map = new LinkedHashMap<>();
        map.put("2", "two");
        map.put("5", "five");
        map.putFirst("1", "one");
        map.putLast("4", "four");
        map.put("3", "three");
        SequencedSet<Map.Entry<Object, Object>> entries = map.sequencedEntrySet();
        System.out.println(" entries " + entries);
        var list = new ArrayList<>();
        list.add("1");
        list.add("3");
        list.add("2");
        System.out.println("list: " + list);
        SequencedCollection<Object> seqList = Collections.unmodifiableSequencedCollection(list);
        System.out.println("seqList: " + seqList);


    }
}
