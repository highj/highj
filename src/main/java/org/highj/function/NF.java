package org.highj.function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Maybe;
import org.highj.data.eq.Eq;
import org.highj.data.eq.Eq1;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ord1;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.unfoldable.Unfoldable;
import org.highj.typeclass2.profunctor.Profunctor;

import java.util.function.Function;

/**
 * Natural Transformation
 *
 * @param <F> Source Context
 * @param <G> Target Context
 */
public interface NF<F, G> extends __2<NF.µ, F, G> {
    interface µ {
    }

    <A> __<G, A> apply(__<F, A> a);

    default <H> NF<F,H> andThen(NF<G,H> gh) {
        return compose(gh, this);
    }

    static <F_> NF<F_, F_> identity() {
        return new NF<F_, F_>() {
            @Override
            public <A> __<F_, A> apply(__<F_, A> a) {
                return a;
            }
        };
    }

    static <F,G,H> NF<F,H> compose(NF<G,H> gh, NF<F,G> fg) {
        return new NF<F, H>() {
            @Override
            public <A> __<H, A> apply(__<F, A> a) {
                return gh.apply(fg.apply(a));
            }
        };
    }

    default Eq1<F> eq1(Eq1<G> eq1G) {
        return new Eq1<F>() {
            @Override
            public <T> Eq<__<F, T>> eq1(Eq<? super T> eq) {
                return (oneF, twoF) -> {
                    Eq<__<G, T>> eqG = eq1G.eq1(eq);
                    return eqG.eq(apply(oneF), apply(twoF));
                };
            }
        };
    }

    default Ord1<F> ord1(Ord1<G> ord1G) {
        return new Ord1<F>() {
            @Override
            public <T> Ord<__<F, T>> cmp(Ord<? super T> ord) {
                return (oneF, twoF) -> {
                    Ord<__<G, T>> ordG = ord1G.cmp(ord);
                    return ordG.cmp(apply(oneF), apply(twoF));
                };
            }
        };
    }

    default Foldable<F> foldable(Foldable<G> foldableG) {
        return new Foldable<F>() {
            @Override
            public <A, B> B foldMap(Monoid<B> mb, Function<A, B> fn, __<F, A> nestedA) {
                return foldableG.foldMap(mb, fn, apply(nestedA));
            }
        };
    }

    default Unfoldable<G> unfoldable(Unfoldable<F> unfoldableF) {
        return new Unfoldable<G>() {
            @Override
            public <A, B> __<G, A> unfoldr(Function<B, Maybe<T2<A, B>>> fn, B b) {
                return apply(unfoldableF.unfoldr(fn,b));
            }
        };
    }

}
