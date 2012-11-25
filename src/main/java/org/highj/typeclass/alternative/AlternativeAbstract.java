package org.highj.typeclass.alternative;

import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.function.F2;
import org.highj.typeclass.group.Monoid;
import org.highj.typeclass.monad.ApplicativeAbstract;

public abstract class AlternativeAbstract<mu> extends ApplicativeAbstract<mu> implements Alternative<mu> {

    private final Plus plus;

    public AlternativeAbstract(Plus<mu> plus) {
        this.plus = plus;
    }

    @Override
    public <A> _<mu, A> mzero(){
        return plus.mzero();
    }

    @Override
    public <A> _<mu, A> mplus(_<mu, A> first, _<mu, A> second) {
        return plus.mplus(first, second);
    }

    public <A> F2<_<mu, A>,_<mu, A>,_<mu, A>> mplus() {
        return plus.mplus();
    }

    @Override
    public <A> _<mu, Maybe<A>> optional(_<mu, A> nestedA) {
        return mplus(map(Maybe.<A>just(), nestedA), pure(Maybe.<A>Nothing()));
    }

    @Override
    public <A> Monoid<_<mu, A>> asMonoid() {
        return plus.asMonoid();
    }

}

