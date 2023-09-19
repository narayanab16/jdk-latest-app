package com.narayana.jdk21;

public class RecordMain {
    record Point(int x, int y) { }
    enum Color { RED, GREEN, BLUE }
    record ColoredPoint(Point p, Color c) { }
    record Pair(Object x, Object y) {}
    public static void main(String[] args) {
        Pair p = new Pair(42, 42);
        if (p instanceof Pair(String s, String t)) {
            System.out.println(s + ", " + t);
        } else {
            System.out.println("Not a pair of strings");
        }
        var obj = new ColoredPoint(new Point(1,2), Color.RED);
        System.out.println(" ColoredPoint : " + obj);
        testStringEnhanced("yes");
    }
    static void testStringEnhanced(String response) {
        switch (response) {
            case null -> { }
            case "y", "Y" -> {
                System.out.println("You got it");
            }
            case "n", "N" -> {
                System.out.println("Shame");
            }
            case String s
                    when s.equalsIgnoreCase("YES") -> {
                System.out.println("You got it");
            }
            case String s
                    when s.equalsIgnoreCase("NO") -> {
                System.out.println("Shame");
            }
            case String s -> {
                System.out.println("Sorry?");
            }
        }
    }
}
