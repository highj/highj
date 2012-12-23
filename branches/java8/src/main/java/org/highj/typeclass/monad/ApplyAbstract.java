package org.highj.typeclass.monad;

import org.highj._;
import org.highj.function.F1;
import org.highj.function.F2;
import org.highj.function.F3;

public abstract class ApplyAbstract<mu> extends FunctorAbstract<mu> implements Apply<mu>{

    // <*> (Control.Applicative), ap (Control.Monad)
    public abstract <A, B> _<mu, B> ap(_<mu, F1<A, B>> fn, _<mu, A> nestedA);

    //curried version of ap
    public <A, B> F1<_<mu, A>, _<mu, B>> ap(final _<mu, F1<A, B>> nestedFn) {
        return new F1<_<mu, A>, _<mu, B>>() {
            @Override
            public _<mu, B> $(_<mu, A> a) {
                return ap(nestedFn, a);
            }
        };
    }

    // <* (Control.Applicative)
    public <A, B> _<mu, A> leftSeq(_<mu, A> nestedA, _<mu, B> nestedB) {
        return lift2(F2.<A,B>constant()).$(nestedA, nestedB);
    }

    // *> (Control.Applicative)
    public <A, B> _<mu, B> rightSeq(_<mu, A> nestedA, _<mu, B> nestedB) {
        return leftSeq(nestedB, nestedA);
    }

    //liftA2 (Control.Applicative), liftM2 (Control.Monad)
    public <A, B, C> F2<_<mu, A>, _<mu, B>, _<mu, C>> lift2(final F1<A, F1<B, C>> fn) {
        return new F2<_<mu, A>, _<mu, B>, _<mu, C>>() {
            @Override
            public _<mu, C> $(_<mu, A> a, _<mu, B> b) {
                return ap(map(fn, a), b);
            }
        };
    }

    //liftA3 (Control.Applicative), liftM3 (Control.Monad)
    public <A, B, C, D> F3<_<mu, A>, _<mu, B>, _<mu, C>, _<mu, D>> lift3(final F1<A, F1<B, F1<C, D>>> fn) {
        return new F3<_<mu, A>, _<mu, B>, _<mu, C>, _<mu, D>>() {
            @Override
            public _<mu, D> $(_<mu, A> a, _<mu, B> b, _<mu, C> c) {
                return ap(lift2(fn).$(a).$(b),c);
            }
        };
    }

}
