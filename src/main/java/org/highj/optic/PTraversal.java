package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.bool.Booleans;
import org.highj.function.F1;
import org.highj.function.F3;
import org.highj.function.F4;
import org.highj.data.structural.Const;
import org.highj.data.tuple.T1;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.monad.Applicative;

import static org.highj.Hkt.asConst;
import static org.highj.Hkt.asT1;
import static org.highj.data.structural.Const.Const;

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
     * Modify polymorphically the target of a {@link PTraversal} with an Applicative function
     * @param applicative the applicative functor
     * @param f target modification function
     * @param <X> applicative type
     * @return result function
     */
    public abstract <X> F1<S, __<X, T>> modifyF(Applicative<X> applicative, Function<A, __<X, B>> f);

    /** Map each target to a {@link Monoid} and combine the results
     * @param monoid the {@link Monoid}
     * @param f the function to convert the target type to the monoid type
     * @param <M> the element type of the monoid
     * @return the function to fold the source into a monoid value
     */
    public final <M> F1<S, M> foldMap(final Monoid<M> monoid, final Function<A, M> f) {
        return s -> asConst(modifyF(Const.applicative(monoid), a -> Const(f.apply(a))).apply(s)).get();
    }

    /** Combine all targets using a target's {@link Monoid}
     * @param m the monoid
     * @return the function to fold the source into a target monoid value
     */
    public final F1<S, A> fold(final Monoid<A> m) {
        return foldMap(m, F1.id());
    }

    /** Get all the targets of a {@link PTraversal}
     * @param s the source value
     * @return a list of all targets
     */
    public final List<A> getAll(final S s) {
        return foldMap(List.monoid(), List.monadPlus::pure).apply(s);
    }

    /** Find the first target of a {@link PTraversal} matching the predicate
     * @param p the predicate
     * @return the partial function
     */
    public final F1<S, Maybe<A>> find(final Function<A, Boolean> p) {
        return foldMap(Maybe.firstMonoid(), a -> Maybe.JustWhenTrue(p.apply(a),() -> a));
    }

    /** Get the first target of a {@link PTraversal}
     *  @param s the source value
     *  @return the value as {@link Maybe#Just}, if there is any
     */
    public final Maybe<A> headOption(final S s) {
        return find(__ -> true).apply(s);
    }

    /** Check if at least one target satisfies the predicate
     * @param p the predicate
     * @return the function to test a source
     */
    public final F1<S, Boolean> exist(final Function<A, Boolean> p) {
        return foldMap(Booleans.orMonoid, p);
    }

    /** Check if all targets satisfy the predicate
     * @param p the predicate
     * @return the function to test a source
     */
    public final F1<S, Boolean> all(final Function<A, Boolean> p) {
        return foldMap(Booleans.andMonoid, p);
    }

    /** Modify polymorphically the target of a {@link PTraversal} with a function
     * @param f the target modifying function
     * @return the source modifying function
     */
    public final F1<S, T> modify(final Function<A, B> f) {
        return s -> asT1(modifyF(T1.monad, a -> T1.of(f.apply(a))).apply(s))._1();
    }

    /** Set polymorphically the target of a {@link PTraversal} with a value
     * @param b the modified target value
     * @return the source modifying function
     */
    public final F1<S, T> set(final B b) {
        return modify(F1.constant(b));
    }

    /** Join two {@link PTraversal} with the same target
     * @param other the second {@link PTraversal}
     * @param <S1> the source type of the second {@link PTraversal}
     * @param <T1> the modified source type of the second {@link PTraversal}
     * @return the combined {@link PTraversal}
     */
    public final <S1, T1> PTraversal<Either<S, S1>, Either<T, T1>, A, B> sum(final PTraversal<S1, T1, A, B> other) {
        final PTraversal<S, T, A, B> self = this;
        return new PTraversal<Either<S, S1>, Either<T, T1>, A, B>() {
            @Override
            public <X> F1<Either<S, S1>, __<X, Either<T, T1>>> modifyF(final Applicative<X> applicative,
                    final Function<A, __<X, B>> f) {
                return ss1 -> ss1.either(
                        s -> applicative.map(Either::<T,T1>Left, self.modifyF(applicative, f).apply(s)),
                        s1 -> applicative.map(Either::<T,T1>Right, other.modifyF(applicative, f).apply(s1))
                        );
            }

        };
    }

    /* *******************************************************************/
    /* * Compose methods between a {@link PTraversal} and another Optics */
    /* *******************************************************************/

    /** Compose a {@link PTraversal} with a {@link Fold}
     * @param other the {@link Fold}
     * @param <C> the target type of the {@link Fold}
     * @return the composed {@link Fold}
     */
    public final <C> Fold<S, C> composeFold(final Fold<A, C> other) {
        return asFold().composeFold(other);
    }

    //
    /** compose a {@link PTraversal} with a {@link Getter}
     * @param other the {@link Getter}
     * @param <C> the target type of the {@link Getter}
     * @return the composed {@link Fold}
     */
    public final <C> Fold<S, C> composeFold(final Getter<A, C> other) {
        return asFold().composeGetter(other);
    }

    /** Compose a {@link PTraversal} with a {@link PSetter}
     * @param other the {@link PSetter}
     * @param <C> the target type of the {@link PSetter}
     * @param <D> the modified target type of the {@link PSetter}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composeSetter(final PSetter<A, B, C, D> other) {
        return asSetter().composeSetter(other);
    }

    /** compose a {@link PTraversal} with a {@link PTraversal}
     * @param other the second {@link PTraversal}
     * @param <C> the target type of the second {@link PTraversal}
     * @param <D> the modified target type of the second {@link PTraversal}
     * @return the composed {@link PTraversal}
     */
    public final <C, D> PTraversal<S, T, C, D> composeTraversal(final PTraversal<A, B, C, D> other) {
        final PTraversal<S, T, A, B> self = this;
        return new PTraversal<S, T, C, D>() {
            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<C, __<X, D>> f) {
                return self.modifyF(applicative, other.modifyF(applicative, f));
            }

        };
    }

    /** Compose a {@link PTraversal} with a {@link POptional}
     * @param other the {@link POptional}
     * @param <C> the target type of the {@link POptional}
     * @param <D> the modified target type of the {@link POptional}
     * @return the composed {@link PTraversal}
     */
    public final <C, D> PTraversal<S, T, C, D> composeOptional(final POptional<A, B, C, D> other) {
        return composeTraversal(other.asTraversal());
    }

    /** Compose a {@link PTraversal} with a {@link PPrism}
     * @param other the {@link PPrism}
     * @param <C> the target type of the {@link PPrism}
     * @param <D> the modified target type of the {@link PPrism}
     * @return the composed {@link PTraversal}
     */
    public final <C, D> PTraversal<S, T, C, D> composePrism(final PPrism<A, B, C, D> other) {
        return composeTraversal(other.asTraversal());
    }

    /** Compose a {@link PTraversal} with a {@link PLens}
     * @param other the {@link PLens}
     * @param <C> the target type of the {@link PLens}
     * @param <D> the modified target type of the {@link PLens}
     * @return the composed {@link PTraversal}
     */
    public final <C, D> PTraversal<S, T, C, D> composeLens(final PLens<A, B, C, D> other) {
        return composeTraversal(other.asTraversal());
    }

    /** Compose a {@link PTraversal} with a {@link PIso}
     * @param other the {@link PIso}
     * @param <C> the target type of the {@link PIso}
     * @param <D> the modified target type of the {@link PIso}
     * @return the composed {@link PTraversal}
     */
    public final <C, D> PTraversal<S, T, C, D> composeIso(final PIso<A, B, C, D> other) {
        return composeTraversal(other.asTraversal());
    }

    /* *************************************************************************/
    /* * Transformation methods to view a {@link PTraversal} as another Optics */
    /* *************************************************************************/

    /** View a {@link PTraversal} as a {@link Fold}
     * @return the {@link Fold}
     */
    public final Fold<S, A> asFold() {
        return new Fold<S, A>() {
            @Override
            public <M> F1<S, M> foldMap(final Monoid<M> monoid, final Function<A, M> f) {
                return PTraversal.this.foldMap(monoid, f);
            }
        };
    }

    /** View a {@link PTraversal} as a {@link PSetter}
     * @return the {@link PSetter}
     */
    public PSetter<S, T, A, B> asSetter() {
        return PSetter.pSetter(this::modify);
    }

    public static <S, T> PTraversal<S, T, S, T> pId() {
        return PIso.<S, T> pId().asTraversal();
    }

    public static <S, T> PTraversal<Either<S, S>, Either<T, T>, S, T> pCodiagonal() {
        return new PTraversal<Either<S, S>, Either<T, T>, S, T>() {

            @Override
            public <X> F1<Either<S, S>, __<X, Either<T, T>>> modifyF(final Applicative<X> applicative,
                    final Function<S, __<X, T>> f) {
                return s -> s.bimap(f, f).either(
                        e -> applicative.map(Either::<T,T>Left, e),
                        e -> applicative.map(Either::<T,T>Right, e)
                        );
            }

        };
    }

    /** Create a {@link PTraversal} from a {@link Traversable}
     * @param traverse the {@link Traversable}
     * @param <T> the traversable type
     * @param <A> the target type and source parameter type
     * @param <B> the modified target type and modified source parameter type
     * @return the {@link PTraversal}
     */
    public static <T, A, B> PTraversal<__<T, A>, __<T, B>, A, B> fromTraversable(final Traversable<T> traverse) {
        return new PTraversal<__<T, A>, __<T, B>, A, B>() {
            @Override
            public <X> F1<__<T, A>, __<X, __<T, B>>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return traversable -> traverse.traverse(applicative, f, traversable);
            }
        };
    }

    public static <S, T, A, B> PTraversal<S, T, A, B> pTraversal(final Function<S, A> get1, final Function<S, A> get2,
            final F3<B, B, S, T> set) {
        return new PTraversal<S, T, A, B>() {

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
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
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return s -> applicative.apply3(b1 -> b2 -> b3 -> set.apply(b1, b2, b3, s),
                        f.apply(get1.apply(s)), f.apply(get2.apply(s)), f.apply(get3.apply(s)));
            }
        };
    }

}
