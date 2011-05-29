/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import highj.__;

/**
 *
 * @author DGronau
 */
public abstract class SemigroupoidAbstract<A> implements Semigroupoid<A> {

    // (>>>) (Control.Category, Control.Arrow)
    @Override
    public <B, C, D> __<A, B, D> then(__<A, B, C> bc, __<A, C, D> cd) {
        return o(cd, bc);
    }

    // 2x (>>>)
    @Override
    public <B, C, D, E> __<A, B, E> then(__<A, B, C> bc, __<A, C, D> cd, __<A, D, E> de) {
        return then(bc, then(cd, de));
    }

    // 3x (>>>)
    @Override
    public <B, C, D, E, F> __<A, B, F> then(__<A, B, C> bc, __<A, C, D> cd, __<A, D, E> de, __<A, E, F> ef) {
        return then(bc, then(cd, then(de, ef)));
    }
    
}
