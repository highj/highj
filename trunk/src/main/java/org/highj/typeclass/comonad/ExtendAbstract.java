package org.highj.typeclass.comonad;

import org.highj._;
import org.highj.function.F1;
import org.highj.typeclass.monad.FunctorAbstract;

//Minimal implementation: duplicate(_<mu, A>) OR extend(F1<_<mu, A>, B>)
public abstract class ExtendAbstract<mu> extends FunctorAbstract<mu> implements Extend<mu> {

    @Override
    public <A> _<mu, _<mu, A>> duplicate(_<mu, A> nestedA) {
        // duplicate = extend id
        return extend(F1.<_<mu, A>>id()).$(nestedA);
    }

    @Override
    public <A> F1<_<mu, A>,_<mu, _<mu, A>>> duplicate() {
        return new F1<_<mu, A>,_<mu, _<mu, A>>>() {
            @Override
            public _<mu, _<mu, A>> $(_<mu, A> a) {
                return duplicate(a);
            }
        };
    }

    @Override
    public <A, B> F1<_<mu, A>, _<mu, B>> extend(final F1<_<mu, A>, B> fn) {
        //extend f = fmap f . duplicate
        F1<_<mu, A>,_<mu, _<mu, A>>> dupFn = duplicate();
        return F1.compose(lift(fn), dupFn);
    }

    @Override
    public <A, B, C> F1<_<mu, A>, C> cokleisli(F1<_<mu, A>, B> f, F1<_<mu, B>, C> g) {
        //f =>= g = g . extend f
        return F1.compose(g, extend(f));
    }

}
