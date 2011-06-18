/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj._;

/**
 *
 * @author DGronau
 */
public abstract class ApplicativeAbstract<Ctor> extends ApplyAbstract<Ctor> implements Applicative<Ctor> {

    @Override
    public abstract <A> _<Ctor, A> pure(A a);

    @Override
    public <A> F<A,_<Ctor, A>> pure() {
        return new F<A,_<Ctor, A>>(){
            @Override
            public _<Ctor, A> f(A a) {
                return pure(a);
            }
        };
    }
    
    @Override
    public <A, B> _<Ctor, B> fmap(F<A, B> fn, _<Ctor, A> nestedA) {
        return ap(pure(fn), nestedA);
    }

}
