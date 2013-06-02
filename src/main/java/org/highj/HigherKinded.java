package org.highj;

public class HigherKinded {

    public static <µ, A, B> __<µ, A, B> uncurry2(_<__.µ<µ, A>, B> curried) {
        return (__<µ, A, B>) curried;
    }

    public static <µ, A, B, C> ___<µ, A, B, C> uncurry3(_<__.µ<___.µ<µ, A>, B>, C> curried) {
        return (___<µ, A, B, C>) curried;
    }

    public static <µ, A, B, C, D> ____<µ, A, B, C, D> uncurry4(_<__.µ<___.µ<____.µ<µ, A>, B>, C>, D> curried) {
        return (____<µ, A, B, C, D>) curried;
    }
}
