package org.highj.data.optic;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.data.functions.F1;

/** {@link PSetter} with a monomorphic modify function */
public final class Setter<S, A> extends PSetter<S, S, A, A> implements __<Setter.µ, S, A> {

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

    /** join two {@link Setter} with the same target */
    public final <S1> Setter<Either<S, S1>, A> sum(final Setter<S1, A> other) {
        return new Setter<>(pSetter.sum(other.pSetter));
    }

    /************************************************************/
    /** Compose methods between a {@link Setter} and another Optics */
    /************************************************************/

    /** compose a {@link Setter} with a {@link Setter} */
    public final <C> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pSetter.composeSetter(other.pSetter));
    }

    /** compose a {@link Setter} with a {@link Traversal} */
    public final <C> Setter<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Setter<>(pSetter.composeTraversal(other.pTraversal));
    }

    /** compose a {@link Setter} with an {@link Iso} */
    public final <C> Setter<S, C> composeIso(final Iso<A, C> other) {
        return new Setter<>(pSetter.composeIso(other.pIso));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Setter<S, A> narrow(final _<__.µ<Setter.µ, S>, A> value) {
        return (Setter) value;
    }

    public static <S> Setter<S, S> id() {
        return new Setter<>(PSetter.pId());
    }

    public static final <S> Setter<Either<S, S>, S> codiagonal() {
        return new Setter<>(PSetter.pCodiagonal());
    }

    /** alias for {@link PSetter} constructor with a monomorphic modify function */
    public static final <S, A> Setter<S, A> setter(final Function<Function<A, A>, F1<S, S>> modify) {
        return new Setter<>(PSetter.pSetter(modify));
    }

}
