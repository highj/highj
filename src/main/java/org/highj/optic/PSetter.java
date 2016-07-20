package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.function.F1;
import org.highj.typeclass1.functor.Functor;

/**
 * A {@link PSetter} is a generalisation of Functor map: - `map: (A =&gt; B) =&gt; F[A] =&gt; F[B]` - `modify: (A =&gt; B) =&gt; S =&gt; T`
 *
 * {@link PSetter} stands for Polymorphic Setter as it set and modify methods change a type `A` to `B` and `S` to `T`.
 *
 * {@link PTraversal}, {@link POptional}, {@link PPrism}, {@link PLens} and {@link PIso} are valid {@link PSetter}
 *
 * @param <S> the source of a {@link PSetter}
 * @param <T> the modified source of a {@link PSetter}
 * @param <A> the target of a {@link PSetter}
 * @param <B> the modified target of a {@link PSetter}
 */
public abstract class PSetter<S, T, A, B> {

    PSetter() {
        super();
    }

    /** Modify polymorphically the target of a {@link PSetter} with a function
     * @param f the target modifying function
     * @return the function returning the modified source
     */
    public abstract F1<S, T> modify(Function<A, B> f);

    /** Set polymorphically the target of a {@link PSetter} with a value
     * @param b the modified target value
     * @return the function returning the modified source
     */
    public abstract F1<S, T> set(final B b);

    /** Join two {@link PSetter} with the same target
     * @param other the second {@link PSetter}
     * @param <S1> the source type of the second {@link PSetter}
     * @param <T1> the modified source type of the second {@link PSetter}
     * @return the combined {@link PSetter}
     */
    public final <S1, T1> PSetter<Either<S, S1>, Either<T, T1>, A, B> sum(final PSetter<S1, T1, A, B> other) {
        return pSetter(f -> e -> e.bimap(modify(f), other.modify(f)));
    }

    /* ****************************************************************/
    /* * Compose methods between a {@link PSetter} and another Optics */
    /* ****************************************************************/

    /** Compose a {@link PSetter} with a {@link PSetter}
     * @param other the second {@link PSetter}
     * @param <C> the target type of the second {@link PSetter}
     * @param <D> the modified target type of the second {@link PSetter}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composeSetter(final PSetter<A, B, C, D> other) {
        final PSetter<S, T, A, B> self = this;
        return new PSetter<S, T, C, D>() {

            @Override
            public F1<S, T> modify(final Function<C, D> f) {
                return self.modify(other.modify(f));
            }

            @Override
            public F1<S, T> set(final D d) {
                return self.modify(other.set(d));
            }
        };
    }

    /** Compose a {@link PSetter} with a {@link PTraversal}
     * @param other the {@link PTraversal}
     * @param <C> the target type of the {@link PTraversal}
     * @param <D> the modified target type of the {@link PTraversal}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composeTraversal(final PTraversal<A, B, C, D> other) {
        return composeSetter(other.asSetter());
    }

    /** compose a {@link PSetter} with a {@link POptional}
     * @param other the {@link POptional}
     * @param <C> the target type of the {@link POptional}
     * @param <D> the modified target type of the {@link POptional}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composeOptional(final POptional<A, B, C, D> other) {
        return composeSetter(other.asSetter());
    }

    /** Compose a {@link PSetter} with a {@link PPrism}
     * @param other the {@link PPrism}
     * @param <C> the target type of the {@link PPrism}
     * @param <D> the modified target type of the {@link PPrism}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composePrism(final PPrism<A, B, C, D> other) {
        return composeSetter(other.asSetter());
    }

    /** Compose a {@link PSetter} with a {@link PLens}
     * @param other the {@link PLens}
     * @param <C> the target type of the {@link PLens}
     * @param <D> the modified target type of the {@link PLens}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composeLens(final PLens<A, B, C, D> other) {
        return composeSetter(other.asSetter());
    }

    /** Compose a {@link PSetter} with a {@link PIso}
     * @param other the {@link PIso}
     * @param <C> the target type of the {@link PIso}
     * @param <D> the modified target type of the {@link PIso}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composeIso(final PIso<A, B, C, D> other) {
        return composeSetter(other.asSetter());
    }

    public static <S, T> PSetter<S, T, S, T> pId() {
        return PIso.<S, T> pId().asSetter();
    }

    public static final <S, T> PSetter<Either<S, S>, Either<T, T>, S, T> pCodiagonal() {
        return pSetter(f -> e -> e.bimap(f, f));
    }

    public static final <S, T, A, B> PSetter<S, T, A, B> pSetter(final Function<Function<A, B>, F1<S, T>> modify) {
        return new PSetter<S, T, A, B>() {
            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return modify.apply(f);
            }

            @Override
            public F1<S, T> set(final B b) {
                return modify(__ -> b);
            }
        };
    }

    /** Create a {@link PSetter} from a {@link Functor}
     * @param functor the functor
     * @param <X> the functor type
     * @param <A> the target type and parameter type of the source
     * @param <B> the modified target type and parameter type of the modified source
     * @return the {@link PSetter}
     */
    public static <X, A, B> PSetter<__<X, A>, __<X, B>, A, B> fromFunctor(final Functor<X> functor) {
        return pSetter(f -> x -> functor.map(f, x));
    }

}
