package org.highj.function;

import org.highj.data.eq.Eq;
import org.highj.typeclass0.group.Group;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.highj.util.Contracts.require;

public enum Strings {
    ;

    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static final Function<String, Function<Integer, String>> repeat =  s -> n -> {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    };

    public static <A> Function<A, String> format(final String formatString) {
        return a -> String.format(formatString, a);
    }

    public static String concat(String one, String two) {
        return one + two;
    }

    public static final Eq<String> eq = new Eq.JavaEq<>();

    public static final Eq<String> eqIgnoreCase = (one, two) -> one == null ? two == null : one.equalsIgnoreCase(two);

    public static final Group<String> group = Group.create("", Strings::concat, Strings::reverse);

    public static String mkString(String sep, Object... values) {
        return mkString(sep, Arrays.asList(values));
    }

    public static String mkString(String sep, Iterable<?> values) {
        StringBuilder sb = new StringBuilder();
        for (Object s : values) {
            if (sb.length() != 0) {
                sb.append(sep);
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public static String mkString(String start, String sep, String end, Object... values) {
        return start + mkString(sep, values) + end;
    }

    public static String mkString(String start, String sep, String end, Iterable<?> values) {
        return start + mkString(sep, values) + end;
    }

    public static Iterable<Character> iterable(final String string) {
        return () -> new Iterator<Character>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < string.length();
            }

            @Override
            public Character next() {
                require(hasNext(), NoSuchElementException.class, "next() on empty iterator");
                return string.charAt(index++);
            }
        };
    }
}
