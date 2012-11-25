package org.highj;

import static org.highj.util.Contracts.require;

public class ___<µ, A, B, C> extends __<___.µ<µ, A>, B, C> {
    public static class µ<µ, T> {
        private µ() {
        }
    }
    protected ___(µ hidden) {
        super(new ___.µ<µ, A>());
        require(hidden != null, "µ value can't be null");
    }

    public static <µ, A, B, C> ___<µ, A, B, C> uncurry3(_<__.µ<___.µ<µ, A>, B>, C> curried) {
        return (___<µ, A, B, C>) curried;
    }
}