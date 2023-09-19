package com.narayana.jdk21;

import java.text.MessageFormat;

public class TemplateProcessor {
    public static void main(String[] args) {
        String x = "Narayana";
        int y= 2;
        MessageFormat mf = new MessageFormat("{0} plus {1} equals {2}");
        String s = mf.format(x, y, x + y);
        System.out.println(s);
        String name = " Narayana Basetty";
        String info = STR."My name is \{name}";
        System.out.println(" info : " + info);
        // Embedded expressions can be postfix increment expressions
        int index = 0;
        String data = STR."\{index++}, \{index++}, \{index++}, \{index++}";
        System.out.println(" data : " + data);
    }
}
