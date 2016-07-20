package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.function.F1;

/** A {@link PSetter} with a monomorphic modify function */
public final class Setter<S, A> extends PSetter<S, S, A, A> implements __2<Setter.µ, S, A> {

    public static final class µ {
    }

    final PSetter<S, S, A, A> pSetter;

    public Setter(final PSetter<S, S, A, A> pSetter) {
        this.pSetter = pSetter;
    }

    @Override
    public F1<S, S> modify(final Function<A, A> f) {
        return pSetter.modify(f);
    }

    @Override
    public F1<S, S> set(final A b) {
        return pSetter.set(b);
    }

    /** Join two {@link Setter} with the same target
     * @param other the second {@link Setter}
     * @param <S1> the source type of the second {@link Setter}
     * @return the combined {@link Setter}
     */
    public final <S1> Setter<Either<S, S1>, A> sum(final Setter<S1, A> other) {
        return new Setter<>(pSetter.sum(other.pSetter));
    }

    /* ***************************************************************/
    /* * Compose methods between a {@link Setter} and another Optics */
    /* ***************************************************************/

    /** Compose a {@link Setter} with a {@link Setter}
     * @param other the second  {@link Setter}
     * @param <C> the target type of the second {@link Setter}
     * @return the composed {@link Setter}
     */
    public final <C> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pSetter.composeSetter(other.pSetter));
    }

    /** compose a {@link Setter} with a {@link Traversal}
     * @param other the {@link Traversal}
     * @param <C> the target type of the {@link Traversal}
     * @return the composed {@link Setter}
     */
    public final <C> Setter<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Setter<>(pSetter.composeTraversal(other.pTraversal));
    }

    /** Compose a {@link Setter} with an {@link Iso}
     * @param other the {@link Iso}
     * @param <C> the target type of the {@link Iso}
     * @return the composed {@link Setter}
     */
    public final <C> Setter<S, C> composeIso(final Iso<A, C> other) {
        return new Setter<>(pSetter.composeIso(other.pIso));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Setter<S, A> narrow(final __<__<µ, S>, A> value) {
        return (Setter) value;
    }

    public static <S> Setter<S, S> id() {
        return new Setter<>(PSetter.pId());
    }

    public static final <S> Setter<Either<S, S>, S> codiagonal() {
        return new Setter<>(PSetter.pCodiagonal());
    }

    /** Alias for {@link PSetter} constructor with a monomorphic modify function
     * @param modify the modify function
     * @param <S> the source type of the {@link Setter}
     * @param <A> the target type of the {@link Setter}
     * @return the {@link Setter}
     */
    public static final <S, A> Setter<S, A> setter(final Function<Function<A, A>, F1<S, S>> modify) {
        return new Setter<>(PSetter.pSetter(modify));
    }

}
