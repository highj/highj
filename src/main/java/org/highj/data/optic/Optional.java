package org.highj.data.optic;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.data.collection.Maybe;
import org.highj.data.functions.F1;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Applicative;

/** {@link POptional} restricted to monomorphic update */
public final class Optional<S, A> extends POptional<S, S, A, A> implements __<Optional.µ, S, A> {

    public static final class µ {
    }

    final POptional<S, S, A, A> pOptional;

    public Optional(final POptional<S, S, A, A> pOptional) {
        this.pOptional = pOptional;
    }

    @Override
    public F1<S, S> set(final A a) {
        return pOptional.set(a);
    }

    @Override
    public <X> F1<S, _<X, S>> modifyF(final Applicative<X> applicative, final Function<A, _<X, A>> f) {
        return pOptional.modifyF(applicative, f);
    }

    @Override
    public F1<S, S> modify(final Function<A, A> f) {
        return pOptional.modify(f);
    }

    @Override
    public Either<S, A> getOrModify(final S s) {
        return pOptional.getOrModify(s);
    }

    @Override
    public Maybe<A> getMaybe(final S s) {
        return pOptional.getMaybe(s);
    }

    /** join two {@link Optional} with the same target */
    public final <S1> Optional<Either<S, S1>, A> sum(final Optional<S1, A> other) {
        return new Optional<>(pOptional.sum(other.pOptional));
    }

    @Override
    public final <C> Optional<T2<S, C>, T2<A, C>> first() {
        return new Optional<>(pOptional.first());
    }

    @Override
    public final <C> Optional<T2<C, S>, T2<C, A>> second() {
        return new Optional<>(pOptional.second());
    }

    /**************************************************************/
    /** Compose methods between a {@link Optional} and another Optics */
    /**************************************************************/

    /** compose a {@link Optional} with a {@link Setter} */
    public final <C> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pOptional.composeSetter(other.pSetter));
    }

    /** compose a {@link Optional} with a {@link Traversal} */
    public final <C> Traversal<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Traversal<>(pOptional.composeTraversal(other.pTraversal));
    }

    /** compose a {@link Optional} with a {@link Optional} */
    public final <C> Optional<S, C> composeOptional(final Optional<A, C> other) {
        return new Optional<>(pOptional.composeOptional(other.pOptional));
    }

    /** compose a {@link Optional} with a {@link Prism} */
    public final <C> Optional<S, C> composePrism(final Prism<A, C> other) {
        return new Optional<>(pOptional.composePrism(other.pPrism));
    }

    /** compose a {@link Optional} with a {@link Lens} */
    public final <C> Optional<S, C> composeLens(final Lens<A, C> other) {
        return new Optional<>(pOptional.composeLens(other.pLens));
    }

    /** compose a {@link Optional} with an {@link Iso} */
    public final <C> Optional<S, C> composeIso(final Iso<A, C> other) {
        return new Optional<>(pOptional.composeIso(other.pIso));
    }

    /********************************************************************/
    /** Transformation methods to view a {@link Optional} as another Optics */
    /********************************************************************/

    /** view a {@link Optional} as a {@link Setter} */
    @Override
    public final Setter<S, A> asSetter() {
        return new Setter<>(pOptional.asSetter());
    }

    /** view a {@link Optional} as a {@link Traversal} */
    @Override
    public final Traversal<S, A> asTraversal() {
        return new Traversal<>(pOptional.asTraversal());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Optional<S, A> narrow(final _<_<Optional.µ, S>, A> value) {
        return (Optional) value;
    }

    public static <S> Optional<S, S> id() {
        return new Optional<>(POptional.pId());
    }

    public static final <S, A> Optional<S, A> optional(final Function<S, Maybe<A>> getMaybe,
            final Function<A, F1<S, S>> set) {
        return new Optional<>(new POptional<S, S, A, A>() {

            @Override
            public Either<S, A> getOrModify(final S s) {
                return getMaybe.apply(s).cata(Either.<S, A>newLeft(s), Either::<S, A>newRight);
            }

            @Override
            public F1<S, S> set(final A a) {
                return set.apply(a);
            }

            @Override
            public Maybe<A> getMaybe(final S s) {
                return getMaybe.apply(s);
            }

            @Override
            public <X> F1<S, _<X, S>> modifyF(final Applicative<X> applicative, final Function<A, _<X, A>> f) {
                return s -> getOrModify(s).<_<X, S>> either(
                        applicative::pure,
                        a -> applicative.map(b -> set.apply(b).apply(s), f.apply(a))
                        );
            }

            @Override
            public F1<S, S> modify(final Function<A, A> f) {
                return s -> getOrModify(s).either(F1.id(), a -> set.apply(f.apply(a)).apply(s));
            }
        });
    }

}
