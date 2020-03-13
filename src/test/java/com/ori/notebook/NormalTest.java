package com.ori.notebook;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NormalTest {
    public static void main(String[] args) {
        Set<Integer> s = new HashSet<>();
        s.add(1);
        s.add(2);
        s.add(3);
        Iterator<Integer> it = s.iterator();
        System.out.println(it.next());
        System.out.println(it.next());
    }
}
