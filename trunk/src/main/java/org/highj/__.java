package org.highj;

import static org.highj.util.Contracts.require;

public class __<µ, A, B> extends _<__.µ<µ, A>, B> {
    public static class µ<µ, T> {
        private µ() {
        }
    }
    
    protected __(µ hidden) {
        super(new __.µ<µ, A>());
        require(hidden != null, "µ value can't be null");
    }

    public static <µ, A, B> __<µ, A, B> uncurry2(_<__.µ<µ, A>, B> curried) {
        return (__<µ, A, B>) curried;
    }
}
