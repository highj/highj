package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.function.F1;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.monad.Applicative;

/** {@link POptional} restricted to monomorphic update */
public final class Optional<S, A> extends POptional<S, S, A, A> implements __2<Optional.µ, S, A> {

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
    public <X> F1<S, __<X, S>> modifyF(final Applicative<X> applicative, final Function<A, __<X, A>> f) {
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

    /** Join two {@link Optional} with the same target
     * @param other the second {@link Optional}
     * @param <S1> the source type of the second {@link Optional}
     * @return the combined {@link Optional}
     */
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

    /* *****************************************************************/
    /* * Compose methods between a {@link Optional} and another Optics */
    /* *****************************************************************/

    /** Compose a {@link Optional} with a {@link Setter}
     * @param other the {@link Setter}
     * @param <C> the target type of the {@link Setter}
     * @return the composed {@link Setter}
     */
    public final <C> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pOptional.composeSetter(other.pSetter));
    }

    /** Compose a {@link Optional} with a {@link Traversal}
     * @param other the {@link Traversal}
     * @param <C> the target type of the {@link Traversal}
     * @return the composed {@link Traversal}
     */
    public final <C> Traversal<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Traversal<>(pOptional.composeTraversal(other.pTraversal));
    }

    /** Compose a {@link Optional} with a {@link Optional}
     * @param other the second {@link Optional}
     * @param <C> the target type of the second {@link Optional}
     * @return the composed {@link Optional}
     */
    public final <C> Optional<S, C> composeOptional(final Optional<A, C> other) {
        return new Optional<>(pOptional.composeOptional(other.pOptional));
    }

    /** Compose a {@link Optional} with a {@link Prism}
     * @param other the {@link Prism}
     * @param <C> the target type of the {@link Prism}
     * @return the composed {@link Optional}
     */
    public final <C> Optional<S, C> composePrism(final Prism<A, C> other) {
        return new Optional<>(pOptional.composePrism(other.pPrism));
    }

    /** Compose a {@link Optional} with a {@link Lens}
     * @param other the {@link Lens}
     * @param <C> the target type of the {@link Lens}
     * @return the composed {@link Optional}
     */
    public final <C> Optional<S, C> composeLens(final Lens<A, C> other) {
        return new Optional<>(pOptional.composeLens(other.pLens));
    }

    /** Compose a {@link Optional} with an {@link Iso}
     * @param other the {@link Iso}
     * @param <C> the target type of the {@link Iso}
     * @return the composed {@link Optional}
     */
    public final <C> Optional<S, C> composeIso(final Iso<A, C> other) {
        return new Optional<>(pOptional.composeIso(other.pIso));
    }

    /* ***********************************************************************/
    /* * Transformation methods to view a {@link Optional} as another Optics */
    /* ***********************************************************************/

    /** View a {@link Optional} as a {@link Setter}
     * @return the {@link Setter}
     */
    @Override
    public final Setter<S, A> asSetter() {
        return new Setter<>(pOptional.asSetter());
    }

    /** View a {@link Optional} as a {@link Traversal}
     * @return the {@link Traversal}
     */
    @Override
    public final Traversal<S, A> asTraversal() {
        return new Traversal<>(pOptional.asTraversal());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Optional<S, A> narrow(final __<__<µ, S>, A> value) {
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
                return getMaybe.apply(s).cata(Either.<S, A>Left(s), Either::<S, A>Right);
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
            public <X> F1<S, __<X, S>> modifyF(final Applicative<X> applicative, final Function<A, __<X, A>> f) {
                return s -> getOrModify(s).<__<X, S>> either(
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
