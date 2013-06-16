package org.highj;

public class HigherKinded {

    public static <X, A, B> __<X, A, B> uncurry2(_<__.µ<X, A>, B> curried) {
        return (__<X, A, B>) curried;
    }

    public static <X, A, B, C> ___<X, A, B, C> uncurry3(_<__.µ<___.µ<X, A>, B>, C> curried) {
        return (___<X, A, B, C>) curried;
    }

    public static <X, A, B, C, D> ____<X, A, B, C, D> uncurry4(_<__.µ<___.µ<____.µ<X, A>, B>, C>, D> curried) {
        return (____<X, A, B, C, D>) curried;
    }
}
