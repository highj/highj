package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.function.F1;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

/**
 * A {@link PLens} can be seen as a pair of functions: - `get: S =&gt; A` i.e. from an `S`, we can extract an `A` - `set: (B, S) =&gt;
 * T` i.e. if we replace an `A` by a `B` in an `S`, we obtain a `T`
 *
 * A {@link PLens} could also be defined as a weaker {@link PIso} where set requires an additional parameter than reverseGet.
 *
 * {@link PLens} stands for Polymorphic Lens as it set and modify methods change a type `A` to `B` and `S` to `T`. {@link Lens}
 * is a {@link PLens} restricted to monomoprhic updates.
 *
 * A {@link PLens} is also a valid {@link Getter}, {@link Fold}, {@link POptional}, {@link PTraversal} and {@link PSetter}
 *
 * Typically a {@link PLens} or {@link Lens} can be defined between a Product (e.g. case class, tuple, HList) and one of it is
 * components.
 *
 * @param <S> the source of a {@link PLens}
 * @param <T> the modified source of a {@link PLens}
 * @param <A> the target of a {@link PLens}
 * @param <B> the modified target of a {@link PLens}
 */
public abstract class PLens<S, T, A, B> {

    PLens() {
        super();
    }

    /** Get the target of a {@link PLens}
     * @param s the source value
     * @return the target value
     */
    public abstract A get(S s);

    /** Set polymorphically the target of a {@link PLens} using a function
     * @param b the modified target value
     * @return a function to modify the source value
     */
    public abstract F1<S, T> set(B b);

    /**
     * Modify polymorphically the target of a {@link PLens} with an Applicative function
     * @param applicative the applicative functor
     * @param f target modification function
     * @param <X> applicative type
     * @return result function
     */
    public abstract <X> F1<S, __<X, T>> modifyF(Applicative<X> applicative, Function<A, __<X, B>> f);

    /** Modify polymorphically the target of a {@link PLens} using a function
     * @param f target modification function
     * @return source modifying function
     */
    public abstract F1<S, T> modify(final Function<A, B> f);

    /** Join two {@link PLens} with the same target
     * @param other the second {@link PLens}
     * @param <S1> the source type of the second {@link PLens}
     * @param <T1> the modified source type of the second {@link PLens}
     * @return the combined {@link PLens}
     */
    public final <S1, T1> PLens<Either<S, S1>, Either<T, T1>, A, B> sum(final PLens<S1, T1, A, B> other) {
        return pLens(
                e -> e.either(this::get, other::get),
                b -> e -> e.bimap(PLens.this.set(b), other.set(b)));
    }

    /* **************************************************************/
    /* * Compose methods between a {@link PLens} and another Optics */
    /* **************************************************************/

    /** Compose a {@link PLens} with a {@link Fold}
     * @param other the {@link Fold}
     * @param <C> the target type of the {@link Fold}
     * @return the composed {@link Fold}
     */
    public final <C> Fold<S, C> composeFold(final Fold<A, C> other) {
        return asFold().composeFold(other);
    }

    /** Compose a {@link PLens} with a {@link Getter}
     * @param other the {@link Getter}
     * @param <C> the target type of the {@link Getter}
     * @return the composed {@link Getter}
     */
    public final <C> Getter<S, C> composeGetter(final Getter<A, C> other) {
        return asGetter().composeGetter(other);
    }

    /** Compose a {@link PLens} with a {@link PSetter}
     * @param other the  {@link PSetter}
     * @param <C> the target type of the {@link PSetter}
     * @param <D> the modified target type of the {@link PSetter}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composeSetter(final PSetter<A, B, C, D> other) {
        return asSetter().composeSetter(other);
    }

    /**
     * compose a {@link PLens} with a {@link PTraversal}
     * @param other the  {@link PTraversal}
     * @param <C> the target type of the {@link PTraversal}
     * @param <D> the modified target type of the {@link PTraversal}
     * @return the composed {@link PTraversal}
     */
    public final <C, D> PTraversal<S, T, C, D> composeTraversal(final PTraversal<A, B, C, D> other) {
        return asTraversal().composeTraversal(other);
    }

    /** Compose a {@link PLens} with an {@link POptional}
     * @param other the  {@link POptional}
     * @param <C> the target type of the {@link POptional}
     * @param <D> the modified target type of the {@link POptional}
     * @return the composed {@link POptional}
     */
    public final <C, D> POptional<S, T, C, D> composeOptional(final POptional<A, B, C, D> other) {
        return asOptional().composeOptional(other);
    }

    /** Compose a {@link PLens} with a {@link PPrism}
     * @param other the  {@link PPrism}
     * @param <C> the target type of the {@link PPrism}
     * @param <D> the modified target type of the {@link PPrism}
     * @return the composed {@link PPrism}
     */
    public final <C, D> POptional<S, T, C, D> composePrism(final PPrism<A, B, C, D> other) {
        return asOptional().composeOptional(other.asOptional());
    }

    /** compose a {@link PLens} with a {@link PLens}
     * @param other the second {@link PLens}
     * @param <C> the target type of the second {@link PLens}
     * @param <D> the modified target type of the second {@link PLens}
     * @return the composed {@link PLens}
     */
    public final <C, D> PLens<S, T, C, D> composeLens(final PLens<A, B, C, D> other) {
        final PLens<S, T, A, B> self = this;
        return new PLens<S, T, C, D>() {
            @Override
            public C get(final S s) {
                return other.get(self.get(s));
            }

            @Override
            public F1<S, T> set(final D d) {
                return self.modify(other.set(d));
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

    /** Compose a {@link PLens} with an {@link PIso}
     * @param other the  {@link PIso}
     * @param <C> the target type of the {@link PIso}
     * @param <D> the modified target type of the {@link PIso}
     * @return the composed {@link PLens}
     */
    public final <C, D> PLens<S, T, C, D> composeIso(final PIso<A, B, C, D> other) {
        return composeLens(other.asLens());
    }

    /* ********************************************************************/
    /* * Transformation methods to view a {@link PLens} as another Optics */
    /* ********************************************************************/

    /** View a {@link PLens} as a {@link Fold}
     * @return the {@link Fold}
     */
    public final Fold<S, A> asFold() {
        return new Fold<S, A>() {
            @Override
            public <M> F1<S, M> foldMap(final Monoid<M> m, final Function<A, M> f) {
                return s -> f.apply(get(s));
            }
        };
    }

    /** View a {@link PLens} as a {@link Getter}
     * @return the {@link Getter}
     */
    public final Getter<S, A> asGetter() {
        return new Getter<S, A>() {
            @Override
            public A get(final S s) {
                return PLens.this.get(s);
            }
        };
    }

    /** view a {@link PLens} as a {@link PSetter}
     * @return the {@link PSetter}
     */
    public PSetter<S, T, A, B> asSetter() {
        return new PSetter<S, T, A, B>() {
            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return PLens.this.modify(f);
            }

            @Override
            public F1<S, T> set(final B b) {
                return PLens.this.set(b);
            }
        };
    }

    /** View a {@link PLens} as a {@link PTraversal}
     * @return the {@link PTraversal}
     */
    public PTraversal<S, T, A, B> asTraversal() {
        return new PTraversal<S, T, A, B>() {

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return PLens.this.modifyF(applicative, f);
            }

        };
    }

    /** View a {@link PLens} as an {@link POptional}
     * @return the {@link POptional}
     */
    public POptional<S, T, A, B> asOptional() {
        final PLens<S, T, A, B> self = this;
        return new POptional<S, T, A, B>() {
            @Override
            public Either<T, A> getOrModify(final S s) {
                return Either.Right(self.get(s));
            }

            @Override
            public F1<S, T> set(final B b) {
                return self.set(b);
            }

            @Override
            public Maybe<A> getMaybe(final S s) {
                return Maybe.Just(self.get(s));
            }

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return self.modifyF(applicative, f);
            }

            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return self.modify(f);
            }
        };
    }

    public static final <S, T> PLens<S, T, S, T> pId() {
        return PIso.<S, T> pId().asLens();
    }

    /** Create a {@link PLens} using a pair of functions: one to get the target, one to set the target.
     * @param get the get function
     * @param set the set function
     * @param <S> the source type
     * @param <T> the modified source type
     * @param <A> the target type
     * @param <B> the modified target type
     * @return the {@link PLens}
     */
    public static <S, T, A, B> PLens<S, T, A, B> pLens(final Function<S, A> get, final Function<B, F1<S, T>> set) {
        return new PLens<S, T, A, B>() {

            @Override
            public A get(final S s) {
                return get.apply(s);
            }

            @Override
            public F1<S, T> set(final B b) {
                return set.apply(b);
            }

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return s -> applicative.map(b -> set.apply(b).apply(s), f.apply(get.apply(s)));
            }

            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return s -> set(f.apply(get.apply(s))).apply(s);
            }
        };
    }
}