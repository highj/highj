package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.bool.Booleans;
import org.highj.function.F1;
import org.highj.function.Functions;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.typeclass2.arrow.ArrowChoice;

/**
 * A {@link Fold} can be seen as a {@link Getter} with many targets or a weaker {@link PTraversal} which cannot modify its
 * target.
 *
 * {@link Fold} is on the top of the Optic hierarchy which means that {@link Getter}, {@link PTraversal}, {@link POptional},
 * {@link PLens}, {@link PPrism} and {@link PIso} are valid {@link Fold}
 *
 * @param <S> the source of a {@link Fold}
 * @param <A> the target of a {@link Fold}
 */
public abstract class Fold<S, A> implements __2<Fold.µ, S, A> {

    public static final class µ {
    }

    /**
     * Map each target to a {@link Monoid} and combine the results underlying representation of {@link Fold}, all {@link Fold}
     * methods are defined in terms of foldMap.
     *
     * @param m the {@link Monoid}
     * @param f mapping function
     * @param <M> element type of the monoid
     * @return the resulting {@link F1}
     */
    public abstract <M> F1<S, M> foldMap(Monoid<M> m, Function<A, M> f);

    /** Combine all targets using a target's {@link Monoid}.
     * @param m the {@link Monoid}
     * @return the resulting {@link F1}
     */
    public final F1<S, A> fold(final Monoid<A> m) {
        return foldMap(m, F1.id());
    }

    /**
     * Get all the targets of a {@link Fold} TODO: Shall it return a Stream as there might be an infinite number of targets?
     *
     * @param s the source value
     * @return the list
     */
    public final List<A> getAll(final S s) {
        return foldMap(List.monoid(), List.monadPlus::pure).apply(s);
    }

    /** Find the first target of a {@link Fold} matching the predicate
     *
     * @param p the predicate
     * @return the function returning the first occurrence, if there is any
     */
    public final F1<S, Maybe<A>> find(final F1<A, Boolean> p) {
        return foldMap(Maybe.firstMonoid(), a -> Maybe.JustWhenTrue(p.apply(a), () -> a));
    }

    /** Get the first target of a {@link Fold}
     *
     * @param s the source value
     * @return the first target, if there is any
     */
    public final Maybe<A> headOption(final S s) {
        return find(__ -> true).apply(s);
    }

    /** Check if at least one target satisfies the predicate
     * @param p the predicate
     * @return the result function
     */
    public final F1<S, Boolean> exist(final Function<A, Boolean> p) {
        return foldMap(Booleans.orMonoid, p);
    }

    /** Check if all targets satisfy the predicate
     * @param p the predicate
     * @return the result function
     */
    public final F1<S, Boolean> all(final Function<A, Boolean> p) {
        return foldMap(Booleans.andMonoid, p);
    }

    /** Join two {@link Fold} with the same target
     *
     * @param other the second {@link Fold}
     * @param <S1> the source type of the second {@link Fold}
     * @return the combined {@link Fold}
     */
    public final <S1> Fold<Either<S, S1>, A> sum(final Fold<S1, A> other) {
        return new Fold<Either<S, S1>, A>() {
            @Override
            public <B> F1<Either<S, S1>, B> foldMap(final Monoid<B> m, final Function<A, B> f) {
                return s -> s.either(Fold.this.foldMap(m, f), other.foldMap(m, f));
            }
        };
    }

    public final <B> Fold<T2<S, B>, T2<A, B>> first() {
        return new Fold<T2<S, B>, T2<A, B>>() {
            @Override
            public <M> F1<T2<S, B>, M> foldMap(final Monoid<M> m, final Function<T2<A, B>, M> f) {
                return t2 -> Fold.this.foldMap(m, a -> f.apply(T2.of(a, t2._2()))).apply(t2._1());
            }
        };
    }

    public final <B> Fold<T2<B, S>, T2<B, A>> second() {
        return new Fold<T2<B, S>, T2<B, A>>() {
            @Override
            public <M> F1<T2<B, S>, M> foldMap(final Monoid<M> m, final Function<T2<B, A>, M> f) {
                return t2 -> Fold.this.foldMap(m, b -> f.apply(T2.of(t2._1(), b))).apply(t2._2());
            }
        };
    }

    public final <B> Fold<Either<S, B>, Either<A, B>> left() {
        return new Fold<Either<S, B>, Either<A, B>>() {
            @Override
            public <M> F1<Either<S, B>, M> foldMap(final Monoid<M> m, final Function<Either<A, B>, M> f) {
                return e -> e.either(
                        s -> Fold.this.foldMap(m, a -> f.apply(Either.Left(a))).apply(s),
                        Functions.constant(m.identity())
                        );

            }
        };
    }

    public final <B> Fold<Either<B, S>, Either<B, A>> right() {
        return new Fold<Either<B, S>, Either<B, A>>() {
            @Override
            public <M> F1<Either<B, S>, M> foldMap(final Monoid<M> m, final Function<Either<B, A>, M> f) {
                return e -> e.either(
                        Functions.constant(m.identity()),
                        s -> Fold.this.foldMap(m, b -> f.apply(Either.Right(b))).apply(s)
                        );
            }
        };
    }

    /* *************************************************************/
    /* * Compose methods between a {@link Fold} and another Optics */
    /* *************************************************************/

    /** compose a {@link Fold} with a {@link Fold}
     * @param other the second {@link Fold}
     * @param <B> the target type of the second {@link Fold}
     * @return the composed {@link Fold}
     */
    public final <B> Fold<S, B> composeFold(final Fold<A, B> other) {
        return new Fold<S, B>() {
            @Override
            public <C> F1<S, C> foldMap(final Monoid<C> m, final Function<B, C> f) {
                return Fold.this.<C> foldMap(m, other.<C> foldMap(m, f));
            }
        };
    }

    /** compose a {@link Fold} with a {@link Getter}
     * @param other the  {@link Getter}
     * @param <C> the target type of the {@link Getter}
     * @return the composed {@link Fold}
     */
    public final <C> Fold<S, C> composeGetter(final Getter<A, C> other) {
        return composeFold(other.asFold());
    }

    /** compose a {@link Fold} with a {@link POptional}
     *  @param other the {@link POptional}
     *  @param <B> the modified source type of the {@link POptional}
     *  @param <C> the target type of the {@link POptional}
     *  @param <D> the modified target type of the {@link POptional}
     *  @return the composed {@link Fold}
     */
    public final <B, C, D> Fold<S, C> composeOptional(final POptional<A, B, C, D> other) {
        return composeFold(other.asFold());
    }

    /** compose a {@link Fold} with a {@link PPrism}
     *  @param other the {@link PPrism}
     *  @param <B> the modified source type of the{@link PPrism}
     *  @param <C> the target type of the{@link PPrism}
     *  @param <D> the modified target type of the {@link PPrism}
     *  @return the composed {@link Fold}
     */
    public final <B, C, D> Fold<S, C> composePrism(final PPrism<A, B, C, D> other) {
        return composeFold(other.asFold());
    }

    /** compose a {@link Fold} with a {@link PLens}
     *  @param other the {@link PLens}
     *  @param <B> the modified source type of the{@link PLens}
     *  @param <C> the target type of the{@link PLens}
     *  @param <D> the modified target type of the {@link PLens}
     *  @return the composed {@link Fold}
     */
    public final <B, C, D> Fold<S, C> composeLens(final PLens<A, B, C, D> other) {
        return composeFold(other.asFold());
    }

    /** compose a {@link Fold} with a {@link PIso}
     *  @param other the {@link PLens}
     *  @param <B> the modified source type of the{@link PIso}
     *  @param <C> the target type of the{@link PIso}
     *  @param <D> the modified target type of the {@link PIso}
     *  @return the composed {@link Fold}
     */
    public final <B, C, D> Fold<S, C> composeIso(final PIso<A, B, C, D> other) {
        return composeFold(other.asFold());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Fold<S, A> narrow(final __<__<µ, S>, A> value) {
        return (Fold) value;
    }

    public static <A> Fold<A, A> id() {
        return PIso.<A, A> pId().asFold();
    }

    public static final <A> Fold<Either<A, A>, A> codiagonal() {
        return new Fold<Either<A, A>, A>() {
            @Override
            public <B> F1<Either<A, A>, B> foldMap(final Monoid<B> m, final Function<A, B> f) {
                return e -> e.either(f, f);
            }
        };
    }

    public static <F, A> Fold<__<F, A>, A> fromFoldable(final Foldable<F> foldable) {
        return new Fold<__<F, A>, A>() {
            @Override
            public <M> F1<__<F, A>, M> foldMap(final Monoid<M> m, final Function<A, M> f) {
                return fa -> foldable.foldMap(m, f, fa);
            }
        };
    }

    public static final ArrowChoice<Fold.µ> foldChoice = new ArrowChoice<Fold.µ>() {

        @Override
        public <B, C, D> __2<µ, Either<B, C>, D> fanin(final __2<µ, B, D> f,
                final __2<µ, C, D> g) {
            return narrow(f).sum(narrow(g));
        }

        @Override
        public <B, C, D> __2<µ, B, D> dot(final __2<µ, C, D> cd, final __2<µ, B, C> bc) {
            return narrow(bc).composeFold(narrow(cd));
        }

        @Override
        public <B> __2<µ, B, B> identity() {
            return Fold.id();
        }

        @Override
        public <B, C, D> __2<µ, T2<B, D>, T2<C, D>> first(final __2<µ, B, C> arrow) {
            return narrow(arrow).first();
        }

        @Override
        public <B, C, D> __2<µ, T2<D, B>, T2<D, C>> second(final __2<µ, B, C> arrow) {
            return narrow(arrow).second();
        }

        @Override
        public <B, C> __2<µ, B, C> arr(final Function<B, C> fn) {
            return Getter.getter(fn).asFold();
        }

        @Override
        public <B, C, D> __2<µ, Either<B, D>, Either<C, D>> left(final __2<µ, B, C> arrow) {
            return narrow(arrow).left();
        }

        @Override
        public <B, C, D> __2<µ, Either<D, B>, Either<D, C>> right(final __2<µ, B, C> arrow) {
            return narrow(arrow).right();
        }
    };

}
