package org.highj.data.optic;

import java.util.function.Function;

import org.highj._;
import org.highj.data.collection.Either;
import org.highj.data.collection.List;
import org.highj.data.collection.Maybe;
import org.highj.data.functions.Booleans;
import org.highj.data.functions.F1;
import org.highj.data.functions.F3;
import org.highj.data.functions.F4;
import org.highj.data.structural.Const;
import org.highj.data.tuple.T1;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

/**
 * A {@link PTraversal} can be seen as a {@link POptional} generalised to 0 to n targets where n can be infinite.
 *
 * {@link PTraversal} stands for Polymorphic Traversal as it set and modify methods change a type `A` to `B` and `S` to `T`.
 * {@link Traversal} is a {@link PTraversal} restricted to monomoprhic updates.
 *
 * @param <S> the source of a {@link PTraversal}
 * @param <T> the modified source of a {@link PTraversal}
 * @param <A> the target of a {@link PTraversal}
 * @param <B> the modified target of a {@link PTraversal}
 */
public abstract class PTraversal<S, T, A, B> {

    /**
     * modify polymorphically the target of a {@link PTraversal} with an Applicative function
     */
    public abstract <X> F1<S, _<X, T>> modifyF(Applicative<X> applicative, Function<A, _<X, B>> f);

    /** map each target to a {@link Monoid} and combine the results */
    public final <M> F1<S, M> foldMap(final Monoid<M> monoid, final Function<A, M> f) {
        return s -> Const.narrow(modifyF(Const.applicative(monoid), a -> new Const<>(f.apply(a))).apply(s)).get();
    }

    /** combine all targets using a target's {@link Monoid} */
    public final F1<S, A> fold(final Monoid<A> m) {
        return foldMap(m, F1.id());
    }

    /** get all the targets of a {@link PTraversal} */
    public final List<A> getAll(final S s) {
        return foldMap(List.monoid(), List.monadPlus::pure).apply(s);
    }

    /** find the first target of a {@link PTraversal} matching the predicate */
    public final F1<S, Maybe<A>> find(final Function<A, Boolean> p) {
        return foldMap(Maybe.firstMonoid(), a -> p.apply(a) ? Maybe.Just(a) : Maybe.Nothing());
    }

    /** get the first target of a {@link PTraversal} */
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

    /** modify polymorphically the target of a {@link PTraversal} with a function */
    public final F1<S, T> modify(final Function<A, B> f) {
        return s -> T1.narrow(modifyF(T1.monad, a -> T1.of(f.apply(a))).apply(s))._1();
    }

    /** set polymorphically the target of a {@link PTraversal} with a value */
    public final F1<S, T> set(final B b) {
        return modify(F1.constant(b));
    }

    /** join two {@link PTraversal} with the same target */
    public final <S1, T1> PTraversal<Either<S, S1>, Either<T, T1>, A, B> sum(final PTraversal<S1, T1, A, B> other) {
        final PTraversal<S, T, A, B> self = this;
        return new PTraversal<Either<S, S1>, Either<T, T1>, A, B>() {
            @Override
            public <X> F1<Either<S, S1>, _<X, Either<T, T1>>> modifyF(final Applicative<X> applicative,
                    final Function<A, _<X, B>> f) {
                return ss1 -> ss1.either(
                        s -> applicative.map(Either::newLeft, self.modifyF(applicative, f).apply(s)),
                        s1 -> applicative.map(Either::newRight, other.modifyF(applicative, f).apply(s1))
                        );
            }

        };
    }

    /****************************************************************/
    /** Compose methods between a {@link PTraversal} and another Optics */
    /****************************************************************/

    /** compose a {@link PTraversal} with a {@link Fold} */
    public final <C> Fold<S, C> composeFold(final Fold<A, C> other) {
        return asFold().composeFold(other);
    }

    //
    /** compose a {@link PTraversal} with a {@link Getter} */
    public final <C> Fold<S, C> composeFold(final Getter<A, C> other) {
        return asFold().composeGetter(other);
    }

    /** compose a {@link PTraversal} with a {@link PSetter} */
    public final <C, D> PSetter<S, T, C, D> composeSetter(final PSetter<A, B, C, D> other) {
        return asSetter().composeSetter(other);
    }

    /** compose a {@link PTraversal} with a {@link PTraversal} */
    public final <C, D> PTraversal<S, T, C, D> composeTraversal(final PTraversal<A, B, C, D> other) {
        final PTraversal<S, T, A, B> self = this;
        return new PTraversal<S, T, C, D>() {
            @Override
            public <X> F1<S, _<X, T>> modifyF(final Applicative<X> applicative, final Function<C, _<X, D>> f) {
                return self.modifyF(applicative, other.modifyF(applicative, f));
            }

        };
    }

    /** compose a {@link PTraversal} with a {@link POptional} */
    public final <C, D> PTraversal<S, T, C, D> composeOptional(final POptional<A, B, C, D> other) {
        return composeTraversal(other.asTraversal());
    }

    /** compose a {@link PTraversal} with a {@link PPrism} */
    public final <C, D> PTraversal<S, T, C, D> composePrism(final PPrism<A, B, C, D> other) {
        return composeTraversal(other.asTraversal());
    }

    /** compose a {@link PTraversal} with a {@link PLens} */
    public final <C, D> PTraversal<S, T, C, D> composeLens(final PLens<A, B, C, D> other) {
        return composeTraversal(other.asTraversal());
    }

    /** compose a {@link PTraversal} with a {@link PIso} */
    public final <C, D> PTraversal<S, T, C, D> composeIso(final PIso<A, B, C, D> other) {
        return composeTraversal(other.asTraversal());
    }

    /**********************************************************************/
    /** Transformation methods to view a {@link PTraversal} as another Optics */
    /**********************************************************************/

    /** view a {@link PTraversal} as a {@link Fold} */
    public final Fold<S, A> asFold() {
        return new Fold<S, A>() {
            @Override
            public <M> F1<S, M> foldMap(final Monoid<M> monoid, final Function<A, M> f) {
                return PTraversal.this.foldMap(monoid, f);
            }
        };
    }

    /** view a {@link PTraversal} as a {@link PSetter} */
    public PSetter<S, T, A, B> asSetter() {
        return PSetter.pSetter(this::modify);
    }

    public static <S, T> PTraversal<S, T, S, T> pId() {
        return PIso.<S, T> pId().asTraversal();
    }

    public static <S, T> PTraversal<Either<S, S>, Either<T, T>, S, T> pCodiagonal() {
        return new PTraversal<Either<S, S>, Either<T, T>, S, T>() {

            @Override
            public <X> F1<Either<S, S>, _<X, Either<T, T>>> modifyF(final Applicative<X> applicative,
                    final Function<S, _<X, T>> f) {
                return s -> s.bimap(f, f).either(
                        e -> applicative.map(Either::newLeft, e),
                        e -> applicative.map(Either::newRight, e)
                        );
            }

        };
    }

    /** create a {@link PTraversal} from a {@link Traversable} */
    public static <T, A, B> PTraversal<_<T, A>, _<T, B>, A, B> fromTraversable(final Traversable<T> traverse) {
        return new PTraversal<_<T, A>, _<T, B>, A, B>() {
            @Override
            public <X> F1<_<T, A>, _<X, _<T, B>>> modifyF(final Applicative<X> applicative, final Function<A, _<X, B>> f) {
                return traversable -> traverse.traverse(applicative, f, traversable);
            }
        };
    }

    public static <S, T, A, B> PTraversal<S, T, A, B> pTraversal(final Function<S, A> get1, final Function<S, A> get2,
            final F3<B, B, S, T> set) {
        return new PTraversal<S, T, A, B>() {

            @Override
            public <X> F1<S, _<X, T>> modifyF(final Applicative<X> applicative, final Function<A, _<X, B>> f) {
                return s -> applicative.apply2(b1 -> b2 -> set.apply(b1, b2, s),
                        f.apply(get1.apply(s)), f.apply(get2.apply(s)));
            }
        };
    }

    public static <S, T, A, B> PTraversal<S, T, A, B> pTraversal(final Function<S, A> get1, final Function<S, A> get2,
            final Function<S, A> get3,
            final F4<B, B, B, S, T> set) {
        return new PTraversal<S, T, A, B>() {
            @Override
            public <X> F1<S, _<X, T>> modifyF(final Applicative<X> applicative, final Function<A, _<X, B>> f) {
                return s -> applicative.apply3(b1 -> b2 -> b3 -> set.apply(b1, b2, b3, s),
                        f.apply(get1.apply(s)), f.apply(get2.apply(s)), f.apply(get3.apply(s)));
            }
        };
    }

}
