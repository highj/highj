package org.highj;

public class HigherKinded {

    public static <X, A, B> __<X, A, B> uncurry2(_<_<X, A>, B> curried) {
        return (__<X, A, B>) curried;
    }

    public static <X, A, B, C> ___<X, A, B, C> uncurry3(_<_<_<X, A>, B>, C> curried) {
        return (___<X, A, B, C>) curried;
    }

    public static <X, A, B, C, D> ____<X, A, B, C, D> uncurry4(_<_<_<_<X, A>, B>, C>, D> curried) {
        return (____<X, A, B, C, D>) curried;
    }
}
