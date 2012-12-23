package org.highj.function.repo;

import org.highj.data.compare.Eq;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.typeclass.group.Group;
import org.highj.typeclass.group.GroupAbstract;
import org.highj.util.ReadOnlyIterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.highj.util.Contracts.require;

public enum Strings {
    ;

    public static final F1<String, Integer> length = new F1<String, Integer>() {
        @Override
        public Integer $(String s) {
            return s.length();
        }
    };

    public static final F1<String, String> toUpperCase = new F1<String, String>() {
        @Override
        public String $(String s) {
            return s.toUpperCase();
        }
    };

    public static final F1<String, String> toLowerCase = new F1<String, String>() {
        @Override
        public String $(String s) {
            return s.toLowerCase();
        }
    };

    public static final F1<String, String> reverse = new F1<String, String>() {
        @Override
        public String $(String s) {
            return reverse(s);
        }
    };

    public static final String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static final F2<String, String, String> append = new F2<String, String, String>() {
        @Override
        public String $(String s1, String s2) {
            return s1 + s2;
        }
    };

    public static final F2<String, String, String> prepend = new F2<String, String, String>() {
        @Override
        public String $(String s1, String s2) {
            return s2 + s1;
        }
    };

    public static final F2<String, Integer, String> repeat = new F2<String, Integer, String>() {
        @Override
        public String $(String s, Integer n) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(s);
            }
            return sb.toString();
        }
    };

    public static final <A> F1<A, String> format(final String formatString) {
        return new F1<A, String>() {

            @Override
            public String $(A a) {
                return String.format(formatString, a);
            }
        };
    }

    public static final Eq<String> eq = new Eq.JavaEq<String>();

    public static final Eq<String> eqIgnoreCase = new Eq<String>() {

        @Override
        public boolean eq(String one, String two) {
            return one == null ? two == null : one.equalsIgnoreCase(two);
        }
    };

    public static final Group<String> group = new GroupAbstract<String>(append, "", reverse);

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
        return new Iterable<Character>() {

            @Override
            public Iterator<Character> iterator() {
                return new ReadOnlyIterator<Character>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return index < string.length();
                    }

                    @Override
                    public Character next() {
                        require(hasNext(), NoSuchElementException.class, "next() on mzero iterator");
                        return string.charAt(index++);
                    }
                };
            }
        };
    }
}
