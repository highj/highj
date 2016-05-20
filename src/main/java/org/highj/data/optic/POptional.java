package org.highj.data.optic;

import java.util.Optional;
import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.function.F1;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

/**
 * A {@link POptional} can be seen as a pair of functions: - `getOrModify: S => T \/ A` - `set : (B, S) => T`
 *
 * A {@link POptional} could also be defined as a weaker {@link PLens} and weaker {@link PPrism}
 *
 * {@link POptional} stands for Polymorphic Optional as it set and modify methods change a type `A` to `B` and `S` to `T`.
 * {@link Optional} is a {@link POptional} restricted to monomoprhic updates: {{{ type Optional[S, A] = POptional[S, S, A, A]
 * }}}
 *
 * @param <S> the source of a {@link POptional}
 * @param <T> the modified source of a {@link POptional}
 * @param <A> the target of a {@link POptional}
 * @param <B> the modified target of a {@link POptional}
 */
public abstract class POptional<S, T, A, B> {

    POptional() {
        super();
    }

    /** get the target of a {@link POptional} or modify the source in case there is no target */
    public abstract Either<T, A> getOrModify(S s);

    /** get the modified source of a {@link POptional} */
    public abstract F1<S, T> set(final B b);

    /** get the target of a {@link POptional} or nothing if there is no target */
    public abstract Maybe<A> getMaybe(final S s);

    /**
     * modify polymorphically the target of a {@link POptional} with an Applicative function
     */
    public abstract <X> F1<S, __<X, T>> modifyF(Applicative<X> applicative, Function<A, __<X, B>> f);

    /** modify polymorphically the target of a {@link POptional} with a function */
    public abstract F1<S, T> modify(final Function<A, B> f);

    /**
     * modify polymorphically the target of a {@link POptional} with a function. return empty if the {@link POptional} is not
     * matching
     */
    public final F1<S, Maybe<T>> modifyMaybe(final F1<A, B> f) {
        return s -> getMaybe(s).map(__ -> modify(f).apply(s));
    }

    /**
     * set polymorphically the target of a {@link POptional} with a value. return empty if the {@link POptional} is not matching
     */
    public final F1<S, Maybe<T>> setMaybe(final B b) {
        return modifyMaybe(__ -> b);
    }

    /** check if a {@link POptional} has a target */
    public final boolean isMatching(final S s) {
        return getMaybe(s).isJust();

    }

    /** join two {@link POptional} with the same target */
    public final <S1, T1> POptional<Either<S, S1>, Either<T, T1>, A, B> sum(final POptional<S1, T1, A, B> other) {
        return pOptional(
                e -> e.either(s -> getOrModify(s).leftMap(Either::Left),
                        s1 -> other.getOrModify(s1).leftMap(Either::Right)),
                b -> e -> e.bimap(set(b), other.set(b)));
    }

    public <C> POptional<T2<S, C>, T2<T, C>, T2<A, C>, T2<B, C>> first() {
        return pOptional(
                sc -> getOrModify(sc._1()).bimap(t -> T2.of(t, sc._2()), a -> T2.of(a, sc._2())),
                bc -> s_ -> T2.of(set(bc._1()).apply(s_._1()), bc._2()));
    }

    public <C> POptional<T2<C, S>, T2<C, T>, T2<C, A>, T2<C, B>> second() {
        return pOptional(
                cs -> getOrModify(cs._2()).bimap(t -> T2.of(cs._1(), t), a -> T2.of(cs._1(), a)),
                cb -> _s -> T2.of(cb._1(), set(cb._2()).apply(_s._2())));
    }

    /***************************************************************/
    /** Compose methods between a {@link POptional} and another Optics */
    /***************************************************************/

    /** compose a {@link POptional} with a {@link Fold} */
    public final <C> Fold<S, C> composeFold(final Fold<A, C> other) {
        return asFold().composeFold(other);
    }

    /** compose a {@link POptional} with a {@link Getter} */
    public final <C> Fold<S, C> composeGetter(final Getter<A, C> other) {
        return asFold().composeGetter(other);
    }

    /** compose a {@link POptional} with a {@link PSetter} */
    public final <C, D> PSetter<S, T, C, D> composeSetter(final PSetter<A, B, C, D> other) {
        return asSetter().composeSetter(other);
    }

    /** compose a {@link POptional} with a {@link PTraversal} */
    public final <C, D> PTraversal<S, T, C, D> composeTraversal(final PTraversal<A, B, C, D> other) {
        return asTraversal().composeTraversal(other);
    }

    /** compose a {@link POptional} with a {@link POptional} */
    public final <C, D> POptional<S, T, C, D> composeOptional(final POptional<A, B, C, D> other) {
        final POptional<S, T, A, B> self = this;
        return new POptional<S, T, C, D>() {

            @Override
            public Either<T, C> getOrModify(final S s) {
                return Either.<T> monad().bind(self.getOrModify(s),
                        a -> other.getOrModify(a).bimap(b -> POptional.this.set(b).apply(s), F1.id()));
            }

            @Override
            public F1<S, T> set(final D d) {
                return self.modify(other.set(d));
            }

            @Override
            public Maybe<C> getMaybe(final S s) {
                return self.getMaybe(s).bind(other::getMaybe);
            }

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<C, __<X, D>> f) {
                return self.modifyF(applicative, other.modifyF(applicative, f));
            }

            @Override
            public F1<S, T> modify(final Function<C, D> f) {
                return self.modify(other.modify(f));
            }

        };
    }

    /** compose a {@link POptional} with a {@link PPrism} */
    public final <C, D> POptional<S, T, C, D> composePrism(final PPrism<A, B, C, D> other) {
        return composeOptional(other.asOptional());
    }

    /** compose a {@link POptional} with a {@link PLens} */
    public final <C, D> POptional<S, T, C, D> composeLens(final PLens<A, B, C, D> other) {
        return composeOptional(other.asOptional());
    }

    /** compose a {@link POptional} with a {@link PIso} */
    public final <C, D> POptional<S, T, C, D> composeIso(final PIso<A, B, C, D> other) {
        return composeOptional(other.asOptional());
    }

    /*********************************************************************/
    /** Transformation methods to view a {@link POptional} as another Optics */
    /*********************************************************************/

    /** view a {@link POptional} as a {@link Fold} */
    public final Fold<S, A> asFold() {
        return new Fold<S, A>() {
            @Override
            public <M> F1<S, M> foldMap(final Monoid<M> m, final Function<A, M> f) {
                return s -> POptional.this.getMaybe(s).map(f).getOrElse(m.identity());
            }
        };
    }

    /** view a {@link POptional} as a {@link PSetter} */
    public PSetter<S, T, A, B> asSetter() {
        return new PSetter<S, T, A, B>() {
            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return POptional.this.modify(f);
            }

            @Override
            public F1<S, T> set(final B b) {
                return POptional.this.set(b);
            }
        };
    }

    /** view a {@link POptional} as a {@link PTraversal} */
    public PTraversal<S, T, A, B> asTraversal() {
        return new PTraversal<S, T, A, B>() {

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return POptional.this.modifyF(applicative, f);
            }

        };
    }

    public static <S, T> POptional<S, T, S, T> pId() {
        return PIso.<S, T> pId().asOptional();
    }

    /** create a {@link POptional} using the canonical functions: getOrModify and set */
    public static final <S, T, A, B> POptional<S, T, A, B> pOptional(final Function<S, Either<T, A>> getOrModify,
            final Function<B, F1<S, T>> set) {
        return new POptional<S, T, A, B>() {
            @Override
            public Either<T, A> getOrModify(final S s) {
                return getOrModify.apply(s);
            }

            @Override
            public F1<S, T> set(final B b) {
                return set.apply(b);
            }

            @Override
            public Maybe<A> getMaybe(final S s) {
                return getOrModify.apply(s).maybeRight();
            }

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return s -> getOrModify(s).<__<X, T>> either(
                        applicative::pure,
                        a -> applicative.map(b -> set.apply(b).apply(s), f.apply(a))
                        );
            }

            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return s -> getOrModify.apply(s).either(F1.id(), a -> set.apply(f.apply(a)).apply(s));
            }
        };
    }

}