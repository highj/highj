package org.highj.function;

import org.highj.data.eq.Eq;
import org.highj.data.ord.Ord;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.highj.util.Contracts.require;

public final class Strings {

    private Strings() {
        //do not instantiate
    }

    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static final BiFunction<String, Integer, String> repeat = (s, n) -> {
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

    public static final Eq<String> eqIgnoreCase = (one, two) -> one == null ? two == null : one.equalsIgnoreCase(two);

    public static final Ord<String> ordIgnoreCase = Ord.fromComparator(String.CASE_INSENSITIVE_ORDER);

    public static final Monoid<String> monoid = Monoid.create("", Strings::concat);

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

    public static String mkEnclosed(String start, String sep, String end, Object... values) {
        return start + mkString(sep, values) + end;
    }

    public static String mkEnclosed(String start, String sep, String end, Iterable<?> values) {
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
