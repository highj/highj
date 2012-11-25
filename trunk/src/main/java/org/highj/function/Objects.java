package org.highj.function;

public enum Objects {
    ;

    public static <T> F1<T, String> toStringFn() {
        return new F1<T, String>() {

            @Override
            public String $(T o) {
                return o == null ? "" : o.toString();
            }
        };
    }

    public static <T> F1<T, Integer> hashCodeFn() {
        return new F1<T, Integer>() {

            @Override
            public Integer $(T o) {
                return o == null ? 0 : o.hashCode();
            }
        };
    }

    public static <T> F2<T, T, Boolean> equalsFn() {
        return new F2<T, T, Boolean>() {
            @Override
            public Boolean $(T one, T two) {
                return one == null ? two == null : one.equals(two);
            }
        };
    }

    public static boolean notNull(Object... os) {
        for (Object o : os) {
            if (o == null) return false;
        }
        return true;
    }
}
