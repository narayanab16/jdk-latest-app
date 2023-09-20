package com.narayana.java9to20;

import java.util.ArrayList;

public class NewTypeMain {
    //TODO Java 14
    record Point(int x, int y) { } // case class in scala

    public static void main(String[] args) {
        //TODO instanceof with type
        Object data = 5;
        if(data instanceof Integer i) {
            System.out.println(" data is integer : " + i);
        }
        //TODO Java 18,19,20 Switch pattern matching
        var result = switch (data) {
            case Integer i -> Integer.valueOf(i);
            case String str -> str;
            default -> null;
        };
        System.out.println(" result : " + result);
        //TODO Java 18,19,20 switch expressions
        Object o = 1;
        switch (o) {
            case Integer i -> System.out.println("i " + i);
            case String s -> System.out.println(" s " + s);
            default -> System.out.println("default");
        }

        //TODO Java 10 var, text block
        var textBlock = """
                This is Narayana
                I am currently living in Forney,TX,USA
                """.strip();
        System.out.println("textBlock : " + textBlock);
        var point = new Point(1, 2);
        var s = "String";
        var i = 1;
        var bool = false;
        var al = new ArrayList<String>();
        al.add("1");
        al.add("2");
        System.out.println(" al -> " + al.getClass() + ", al data : " + al +
                ", string : " + s + ", int : " + i + ", bool : " + bool);
        int x = point.x(); // returns 1
        int y = point.y(); // returns 2
        System.out.println(" x: " + x + ", y : " + y);
        myPoint(point);

    }

    private static void myPoint(Point p) {
        System.out.println("calc " + p.x() * p.y());
    }
}
