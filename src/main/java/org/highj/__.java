package org.highj;

import org.highj.data.structural.Dual;

/**
 * Type-lifting interface for type constructors with two arguments. Extends <code>_</code> in order to allow
 * "curried" applications (e.g. writing a monad instance for <code>Either</code>).
 */

public interface __<X, A, B> extends _<_<X, A>, B> {

    default Dual<X, B, A> dual() {
        return new Dual<>(this);
    }

}
