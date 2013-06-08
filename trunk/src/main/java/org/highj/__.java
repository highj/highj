package org.highj;

/**
 * Type-lifting interface for type constructors with two arguments. Extends <code>_</code> in order to allow
 * "curried" applications (e.g. writing a monad instance for <code>Either</code>).
 */

public interface __<µ, A, B> extends _<__.µ<µ, A>, B> {
    public static class µ<µ, T> {}
    
}
