package org.highj;

import static org.highj.util.Contracts.require;

public class ____<µ, A, B, C, D> extends ___<____.µ<µ, A>, B, C, D> {
    public static class µ<µ, T> {
        private µ() {
        }
    }

    protected ____(µ hidden) {
        super(new ____.µ<µ, A>());
        require(hidden != null, "µ value can't be null");
    }

    public static <µ, A, B, C, D> ____<µ, A, B, C, D> uncurry4(_<__.µ<___.µ<____.µ<µ, A>, B>, C>, D> curried) {
        return (____<µ, A, B, C, D>) curried;
    }
}