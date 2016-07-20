package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.function.F1;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

/**
 * A {@link PIso} defines an isomorphism between types S, A and B, T:
 *
 * <pre>
 *              get                           reverse.get
 *     --------------------&gt;             --------------------&gt;
 *   S                       A         T                       B
 *     &lt;--------------------             &lt;--------------------
 *       reverse.reverseGet                   reverseGet
 * </pre>
 *
 * In addition, if f and g forms an isomorphism between `A` and `B`, i.e. if `f . g = id` and `g . f = id`, then a {@link PIso}
 * defines an isomorphism between `S` and `T`:
 *
 * <pre>
 *     S           T                                   S           T
 *     |           ↑                                   ↑           |
 *     |           |                                   |           |
 * get |           | reverseGet     reverse.reverseGet |           | reverse.get
 *     |           |                                   |           |
 *     ↓     f     |                                   |     g     ↓
 *     A --------&gt; B                                   A &lt;-------- B
 * </pre>
 *
 * A {@link PIso} is also a valid {@link Getter}, {@link Fold}, {@link PLens}, {@link PPrism}, {@link POptional},
 * {@link PTraversal} and {@link PSetter}
 *
 * @param <S> the source of a {@link PIso}
 * @param <T> the modified source of a {@link PIso}
 * @param <A> the target of a {@link PIso}
 * @param <B> the modified target of a {@link PIso}
 */
public abstract class PIso<S, T, A, B> {

    PIso() {
        super();
    }

    /** Get the target of a {@link PIso}
     * @param s the source value
     * @return the target value
     */
    public abstract A get(S s);

    /** Get the modified source of a {@link PIso}
     * @param b the modified target
     * @return the modified source
     */
    public abstract T reverseGet(B b);

    /** Reverse a {@link PIso}: the source becomes the target and the target becomes the source
     * @return the reversed {@link PIso}
     */
    public abstract PIso<B, A, T, S> reverse();

    /**
     * Modify polymorphically the target of a {@link PIso} with an Applicative function
     * @param applicative the applicative functor
     * @param f target modification function
     * @param <X> applicative type
     * @return result function
     */
    public final <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
        return s -> applicative.map(this::reverseGet, f.apply(get(s)));
    }

    /** Modify polymorphically the target of a {@link PIso} with a function
     * @param f target modification function
     * @return source modifying function
     */
    public final F1<S, T> modify(final Function<A, B> f) {
        return s -> reverseGet(f.apply(get(s)));
    }

    /** Set polymorphically the target of a {@link PIso} with a value
     * @param b modified target value
     * @return source modifying function
     */
    public final F1<S, T> set(final B b) {
        return __ -> reverseGet(b);
    }

    /** Pair two disjoint {@link PIso}
     * @param other the second {@link PIso}
     * @param <S1> the source type of the second {@link PIso}
     * @param <T1> the modified source type of the second {@link PIso}
     * @param <A1> the target type of the second {@link PIso}
     * @param <B1> the modified target type of the second {@link PIso}
     * @return the combined {@link PIso}
     */
    public <S1, T1, A1, B1> PIso<T2<S, S1>, T2<T, T1>, T2<A, A1>, T2<B, B1>> product(final PIso<S1, T1, A1, B1> other) {
        return pIso(
                ss1 -> T2.of(get(ss1._1()), other.get(ss1._2())),
                bb1 -> T2.of(reverseGet(bb1._1()), other.reverseGet(bb1._2())));
    }

    public <C> PIso<T2<S, C>, T2<T, C>, T2<A, C>, T2<B, C>> first() {
        return pIso(
                sc -> T2.of(get(sc._1()), sc._2()),
                bc -> T2.of(reverseGet(bc._1()), bc._2()));
    }

    public <C> PIso<T2<C, S>, T2<C, T>, T2<C, A>, T2<C, B>> second() {
        return pIso(
                cs -> T2.of(cs._1(), get(cs._2())),
                cb -> T2.of(cb._1(), reverseGet(cb._2())));
    }

    /* *************************************************************/
    /* * Compose methods between a {@link PIso} and another Optics */
    /* *************************************************************/

    /** Compose a {@link PIso} with a {@link Fold}
     * @param other the {@link Fold}
     * @param <C> the target type of the {@link Fold}
     * @return the composed {@link Fold}
     */
    public final <C> Fold<S, C> composeFold(final Fold<A, C> other) {
        return asFold().composeFold(other);
    }

    /** Compose a {@link PIso} with a {@link Getter}
    * @param other the {@link Getter}
    * @param <C> the target type of the {@link Getter}
    * @return the composed {@link Getter}
    */
    public final <C> Getter<S, C> composeGetter(final Getter<A, C> other) {
        return asGetter().composeGetter(other);
    }

    /** Compose a {@link PIso} with a {@link PSetter}
     * @param other the {@link PSetter}
     * @param <C> the target type of the {@link PSetter}
     * @param <D> the modified target type of the {@link PSetter}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composeSetter(final PSetter<A, B, C, D> other) {
        return asSetter().composeSetter(other);
    }

    /** Compose a {@link PIso} with a {@link PTraversal}
     * @param other the {@link PTraversal}
     * @param <C> the target type of the {@link PTraversal}
     * @param <D> the modified target type of the {@link PTraversal}
     * @return the composed {@link PTraversal}
     */
    public final <C, D> PTraversal<S, T, C, D> composeTraversal(final PTraversal<A, B, C, D> other) {
        return asTraversal().composeTraversal(other);
    }

    /** Compose a {@link PIso} with a {@link POptional}
     * @param other the {@link POptional}
     * @param <C> the target type of the {@link POptional}
     * @param <D> the modified target type of the {@link POptional}
     * @return the composed {@link POptional}
     */
    public final <C, D> POptional<S, T, C, D> composeOptional(final POptional<A, B, C, D> other) {
        return asOptional().composeOptional(other);
    }

    /** Compose a {@link PIso} with a {@link PPrism}
     * @param other the {@link PPrism}
     * @param <C> the target type of the {@link PPrism}
     * @param <D> the modified target type of the {@link PPrism}
     * @return the composed {@link PPrism}
     */
    public final <C, D> PPrism<S, T, C, D> composePrism(final PPrism<A, B, C, D> other) {
        return asPrism().composePrism(other);
    }

    /** Compose a {@link PIso} with a {@link PLens}
     * @param other the {@link PLens}
     * @param <C> the target type of the {@link PLens}
     * @param <D> the modified target type of the {@link PLens}
     * @return the composed {@link PLens}
     */
    public final <C, D> PLens<S, T, C, D> composeLens(final PLens<A, B, C, D> other) {
        return asLens().composeLens(other);
    }

    /** compose a {@link PIso} with a {@link PIso}
     * @param other the second {@link PIso}
     * @param <C> the target type of the second {@link PIso}
     * @param <D> the modified target type of the second {@link PIso}
     * @return the composed {@link PIso}
     */
    public final <C, D> PIso<S, T, C, D> composeIso(final PIso<A, B, C, D> other) {
        final PIso<S, T, A, B> self = this;
        return new PIso<S, T, C, D>() {
            @Override
            public C get(final S s) {
                return other.get(self.get(s));
            }

            @Override
            public T reverseGet(final D d) {
                return self.reverseGet(other.reverseGet(d));
            }

            @Override
            public PIso<D, C, T, S> reverse() {
                final PIso<S, T, C, D> composeSelf = this;
                return new PIso<D, C, T, S>() {
                    @Override
                    public T get(final D d) {
                        return self.reverseGet(other.reverseGet(d));
                    }

                    @Override
                    public C reverseGet(final S s) {
                        return other.get(self.get(s));
                    }

                    @Override
                    public PIso<S, T, C, D> reverse() {
                        return composeSelf;
                    }
                };
            }
        };
    }

    /* *******************************************************************/
    /* * Transformation methods to view a {@link PIso} as another Optics */
    /* *******************************************************************/

    /** View a {@link PIso} as a {@link Fold}
     * @return the {@link Fold}
     */
    public final Fold<S, A> asFold() {
        return new Fold<S, A>() {
            @Override
            public <M> F1<S, M> foldMap(final Monoid<M> m, final Function<A, M> f) {
                return s -> f.apply(PIso.this.get(s));
            }
        };
    }

    /** View a {@link PIso} as a {@link Getter}
     * @return the {@link Getter}
     */
    public final Getter<S, A> asGetter() {
        return new Getter<S, A>() {
            @Override
            public A get(final S s) {
                return PIso.this.get(s);
            }
        };
    }

    /** View a {@link PIso} as a {@link Setter}
     * @return the {@link Setter}
     */
    public PSetter<S, T, A, B> asSetter() {
        return new PSetter<S, T, A, B>() {
            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return PIso.this.modify(f);
            }

            @Override
            public F1<S, T> set(final B b) {
                return PIso.this.set(b);
            }
        };
    }

    /** View a {@link PIso} as a {@link PTraversal}
     * @return the {@link PTraversal}
     */
    public PTraversal<S, T, A, B> asTraversal() {
        return new PTraversal<S, T, A, B>() {

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return PIso.this.modifyF(applicative, f);
            }

        };
    }

    /** View a {@link PIso} as a {@link POptional}
     * @return the {@link POptional}
     */
    public POptional<S, T, A, B> asOptional() {
        final PIso<S, T, A, B> self = this;
        return new POptional<S, T, A, B>() {

            @Override
            public Either<T, A> getOrModify(final S s) {
                return Either.Right(self.get(s));
            }

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return self.modifyF(applicative, f);
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
            public F1<S, T> modify(final Function<A, B> f) {
                return self.modify(f);
            }
        };
    }

    /** View a {@link PIso} as a {@link PPrism}
     * @return the {@link PPrism}
     */
    public PPrism<S, T, A, B> asPrism() {
        final PIso<S, T, A, B> self = this;
        return new PPrism<S, T, A, B>() {
            @Override
            public Either<T, A> getOrModify(final S s) {
                return Either.Right(self.get(s));
            }

            @Override
            public T reverseGet(final B b) {
                return self.reverseGet(b);
            }

            @Override
            public Maybe<A> getMaybe(final S s) {
                return Maybe.Just(self.get(s));
            }
        };
    }

    /** View a {@link PIso} as a {@link PLens}
     * @return the {@link PLens}
     */
    public PLens<S, T, A, B> asLens() {
        final PIso<S, T, A, B> self = this;
        return new PLens<S, T, A, B>() {
            @Override
            public A get(final S s) {
                return self.get(s);
            }

            @Override
            public F1<S, T> set(final B b) {
                return self.set(b);
            }

            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return self.modify(f);
            }

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return self.modifyF(applicative, f);
            }
        };
    }

    /** Create a {@link PIso} using a pair of functions: one to get the target and one to get the source.
     * @param get the get function
     * @param reverseGet the reverse get function
     * @param <S> the source type
     * @param <T> the modified source type
     * @param <A> the target type
     * @param <B> the modified target type
     * @return the  {@link PIso}
     */
    public static final <S, T, A, B> PIso<S, T, A, B> pIso(final Function<S, A> get, final Function<B, T> reverseGet) {
        return new PIso<S, T, A, B>() {

            @Override
            public A get(final S s) {
                return get.apply(s);
            }

            @Override
            public T reverseGet(final B b) {
                return reverseGet.apply(b);
            }

            @Override
            public PIso<B, A, T, S> reverse() {
                final PIso<S, T, A, B> self = this;
                return new PIso<B, A, T, S>() {
                    @Override
                    public T get(final B b) {
                        return reverseGet.apply(b);
                    }

                    @Override
                    public A reverseGet(final S s) {
                        return get.apply(s);
                    }

                    @Override
                    public PIso<S, T, A, B> reverse() {
                        return self;
                    }
                };
            }

        };
    }

    /**
     * create a {@link PIso} between any type and itself. id is the zero element of optics composition, for all optics o of type
     * O (e.g. Lens, Iso, Prism, ...):
     *
     * <pre>
     *  o composeIso Iso.id == o
     *  Iso.id composeO o == o
     * </pre>
     *
     * (replace composeO by composeLens, composeIso, composePrism, ...)
     *
     * @param <S> the source and target type
     * @param <T> the modified source and target type
     * @return the identity {@link PIso}
     */
    public static <S, T> PIso<S, T, S, T> pId() {
        return new PIso<S, T, S, T>() {

            @Override
            public S get(final S s) {
                return s;
            }

            @Override
            public T reverseGet(final T t) {
                return t;
            }

            @Override
            public PIso<T, S, T, S> reverse() {
                final PIso<S, T, S, T> self = this;
                return new PIso<T, S, T, S>() {
                    @Override
                    public T get(final T t) {
                        return t;
                    }

                    @Override
                    public S reverseGet(final S s) {
                        return s;
                    }

                    @Override
                    public PIso<S, T, S, T> reverse() {
                        return self;
                    }
                };
            }
        };
    }

}
