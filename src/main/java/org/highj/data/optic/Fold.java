package org.highj.data.optic;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.data.collection.List;
import org.highj.data.collection.Maybe;
import org.highj.data.functions.Booleans;
import org.highj.data.functions.F1;
import org.highj.data.functions.Functions;
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
public abstract class Fold<S, A> implements __<Fold.µ, S, A> {

    public static final class µ {
    }

    /**
     * map each target to a {@link Monoid} and combine the results underlying representation of {@link Fold}, all {@link Fold}
     * methods are defined in terms of foldMap
     */
    public abstract <M> F1<S, M> foldMap(Monoid<M> m, Function<A, M> f);

    /** combine all targets using a target's {@link Monoid} */
    public final F1<S, A> fold(final Monoid<A> m) {
        return foldMap(m, F1.id());
    }

    /**
     * get all the targets of a {@link Fold} TODO: Shall it return a Stream as there might be an infinite number of targets?
     */
    public final List<A> getAll(final S s) {
        return foldMap(List.monoid(), List.monadPlus::pure).apply(s);
    }

    /** find the first target of a {@link Fold} matching the predicate */
    public final F1<S, Maybe<A>> find(final F1<A, Boolean> p) {
        return foldMap(Maybe.firstMonoid(), a -> Maybe.justWhenTrue(p.apply(a), () -> a));
    }

    /** get the first target of a {@link Fold} */
    public final Maybe<A> headOption(final S s) {
        return find(__ -> true).apply(s);
    }

    /** check if at least one target satisfies the predicate */
    public final F1<S, Boolean> exist(final Function<A, Boolean> p) {
        return foldMap(Booleans.orGroup, p);
    }

    /** check if all targets satisfy the predicate */
    public final F1<S, Boolean> all(final Function<A, Boolean> p) {
        return foldMap(Booleans.andGroup, p);
    }

    /** join two {@link Fold} with the same target */
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
                        s -> Fold.this.foldMap(m, a -> f.apply(Either.newLeft(a))).apply(s),
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
                        s -> Fold.this.foldMap(m, b -> f.apply(Either.newRight(b))).apply(s)
                        );
            }
        };
    }

    /**********************************************************/
    /** Compose methods between a {@link Fold} and another Optics */
    /**********************************************************/

    /** compose a {@link Fold} with a {@link Fold} */
    public final <B> Fold<S, B> composeFold(final Fold<A, B> other) {
        return new Fold<S, B>() {
            @Override
            public <C> F1<S, C> foldMap(final Monoid<C> m, final Function<B, C> f) {
                return Fold.this.<C> foldMap(m, other.<C> foldMap(m, f));
            }
        };
    }

    /** compose a {@link Fold} with a {@link Getter} */
    public final <C> Fold<S, C> composeGetter(final Getter<A, C> other) {
        return composeFold(other.asFold());
    }

    /** compose a {@link Fold} with a {@link POptional} */
    public final <B, C, D> Fold<S, C> composeOptional(final POptional<A, B, C, D> other) {
        return composeFold(other.asFold());
    }

    /** compose a {@link Fold} with a {@link PPrism} */
    public final <B, C, D> Fold<S, C> composePrism(final PPrism<A, B, C, D> other) {
        return composeFold(other.asFold());
    }

    /** compose a {@link Fold} with a {@link PLens} */
    public final <B, C, D> Fold<S, C> composeLens(final PLens<A, B, C, D> other) {
        return composeFold(other.asFold());
    }

    /** compose a {@link Fold} with a {@link PIso} */
    public final <B, C, D> Fold<S, C> composeIso(final PIso<A, B, C, D> other) {
        return composeFold(other.asFold());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Fold<S, A> narrow(final _<_<Fold.µ, S>, A> value) {
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

    public static <F, A> Fold<_<F, A>, A> fromFoldable(final Foldable<F> foldable) {
        return new Fold<_<F, A>, A>() {
            @Override
            public <M> F1<_<F, A>, M> foldMap(final Monoid<M> m, final Function<A, M> f) {
                return fa -> foldable.foldMap(m, f, fa);
            }
        };
    }

    public static final ArrowChoice<Fold.µ> foldChoice = new ArrowChoice<Fold.µ>() {

        @Override
        public <B, C, D> __<Fold.µ, Either<B, C>, D> fanin(final __<Fold.µ, B, D> f,
                final __<Fold.µ, C, D> g) {
            return narrow(f).sum(narrow(g));
        }

        @Override
        public <B, C, D> __<Fold.µ, B, D> dot(final __<Fold.µ, C, D> cd, final __<Fold.µ, B, C> bc) {
            return narrow(bc).composeFold(narrow(cd));
        }

        @Override
        public <B> __<Fold.µ, B, B> identity() {
            return Fold.id();
        }

        @Override
        public <B, C, D> __<Fold.µ, T2<B, D>, T2<C, D>> first(final __<Fold.µ, B, C> arrow) {
            return narrow(arrow).first();
        }

        @Override
        public <B, C, D> __<Fold.µ, T2<D, B>, T2<D, C>> second(final __<Fold.µ, B, C> arrow) {
            return narrow(arrow).second();
        }

        @Override
        public <B, C> __<Fold.µ, B, C> arr(final Function<B, C> fn) {
            return Getter.getter(fn).asFold();
        }

        @Override
        public <B, C, D> __<Fold.µ, Either<B, D>, Either<C, D>> left(final __<Fold.µ, B, C> arrow) {
            return narrow(arrow).left();
        }

        @Override
        public <B, C, D> __<Fold.µ, Either<D, B>, Either<D, C>> right(final __<Fold.µ, B, C> arrow) {
            return narrow(arrow).right();
        }
    };

}
