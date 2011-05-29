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
interface Semigroupoid<A> {

    // o (Data.Semigroupoid), (.) (Control.Category)
    <B, C, D> __<A, B, D> o(__<A, C, D> cd, __<A, B, C> bc);

    // (>>>) (Control.Category, Control.Arrow)
    <B, C, D> __<A, B, D> then(__<A, B, C> bc, __<A, C, D> cd);

    // 2 x (>>>) 
    <B, C, D, E> __<A, B, E> then(__<A, B, C> bc, __<A, C, D> cd, __<A, D, E> de);

    // 3 x (>>>)
    <B, C, D, E, F> __<A, B, F> then(__<A, B, C> bc, __<A, C, D> cd, __<A, D, E> de, __<A, E, F> ef);
    
}
