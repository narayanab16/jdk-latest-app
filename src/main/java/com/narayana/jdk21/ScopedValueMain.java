package com.narayana.jdk21;

public class ScopedValueMain {
    private static final ScopedValue<String> X = ScopedValue.newInstance();

    public static void main(String[] args) {
        ScopedValueMain ref = new ScopedValueMain();
        ref.foo();
    }
    void foo() {
        ScopedValue.where(X, "hello").run(() -> bar());
    }

    void bar() {
        System.out.println(X.get()); // prints hello
        ScopedValue.where(X, "goodbye").run(() -> baz());
        System.out.println(X.get()); // prints hello
    }

    void baz() {
        System.out.println(X.get()); // prints goodbye
    }
}
