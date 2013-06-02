package org.highj.data.functions;

import java.util.function.Function;

public enum Objects {
    ;

    public static <T> Function<T, String> toStringFn() {
        return o -> o == null ? "" : o.toString();
    }

    public static <T> Function<T, Integer> hashCodeFn() {
        return o -> o == null ? 0 : o.hashCode();
    }

    public static <T> Function<T, Function<T, Boolean>> equalsFn() {
        return one -> two -> one == null ? two == null : one.equals(two);
    }

    //for better type inference
    public static <T> Function<T,Boolean> equalsFn(T t) {
        return Objects.<T>equalsFn().apply(t);
    }

    public static boolean notNull(Object... os) {
        for (Object o : os) {
            if (o == null) return false;
        }
        return true;
    }
}
