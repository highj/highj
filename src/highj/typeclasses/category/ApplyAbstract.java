/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import fj.F2;
import fj.F3;
import fj.Function;
import highj._;

/**
 *
 * @author DGronau
 */
public abstract class ApplyAbstract<Ctor> extends FunctorAbstract<Ctor>
        implements Apply<Ctor> {
    
    // <*> (Control.Applicative), ap (Control.Monad)
    @Override
    public abstract <A, B> _<Ctor, B> ap(_<Ctor, F<A, B>> fn, _<Ctor, A> nestedA);

    // ap curried
    @Override
    public <A, B> F<_<Ctor, A>, _<Ctor, B>> ap(final _<Ctor, F<A, B>> fn) {
        return new F<_<Ctor, A>, _<Ctor, B>>() {
            @Override
            public _<Ctor, B> f(_<Ctor, A> a) {
                return ap(fn, a);
            }
        };
    }

    // <* (Control.Applicative)
    @Override
    public <A, B> _<Ctor, A> leftSeq(_<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return lift2(Function.<B, A>constant()).f(nestedA, nestedB);
    }

    // *> (Control.Applicative)
    @Override
    public <A, B> _<Ctor, B> rightSeq(_<Ctor, A> nestedA, _<Ctor, B> nestedB) {
        return lift2(Function.<A, F<B, B>>constant().f(Function.<B>identity())).f(nestedA, nestedB);
    }

    //liftA2 (Control.Applicative), liftM2 (Control.Monad)
    @Override
    public <A, B, C> F2<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>> lift2(final F<A, F<B, C>> fn) {
        return new F2<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>>() {
            @Override
            public _<Ctor, C> f(_<Ctor, A> a, _<Ctor, B> b) {
                return ap(fmap(fn, a), b);
            }
        };
    }

    //flat version of lift2
    @Override
    public <A, B, C> F2<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>> lift2Flat(F2<A, B, C> fn) {
        return lift2(fn.curry());
    }

    //liftA3 (Control.Applicative), liftM3 (Control.Monad)
    @Override
    public <A, B, C, D> F3<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>, _<Ctor, D>> lift3(final F<A, F<B, F<C, D>>> fn) {
        return new F3<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>, _<Ctor, D>>() {
            @Override
            public _<Ctor, D> f(_<Ctor, A> a, _<Ctor, B> b, _<Ctor, C> c) {
                return ap(lift2(fn).f(a, b), c);
            }
        };
    }

    //flat version of lift3
    @Override
    public <A, B, C, D> F3<_<Ctor, A>, _<Ctor, B>, _<Ctor, C>, _<Ctor, D>> lift3Flat(F3<A, B, C, D> fn) {
        return lift3(Function.curry(fn));
    }

}
