package org.highj.data.optic;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.data.functions.F1;
import org.highj.data.functions.F3;
import org.highj.typeclass1.monad.Applicative;

public final class Traversal<S, A> extends PTraversal<S, S, A, A> implements __<Traversal.µ, S, A> {

    public static final class µ {
    }

    final PTraversal<S, S, A, A> pTraversal;

    public Traversal(final PTraversal<S, S, A, A> pTraversal) {
        this.pTraversal = pTraversal;
    }

    @Override
    public <X> F1<S, _<X, S>> modifyF(final Applicative<X> applicative, final Function<A, _<X, A>> f) {
        return pTraversal.modifyF(applicative, f);
    }

    /** join two {@link Traversal} with the same target */
    public final <S1> Traversal<Either<S, S1>, A> sum(final Traversal<S1, A> other) {
        return new Traversal<>(pTraversal.sum(other.pTraversal));
    }

    /***************************************************************/
    /** Compose methods between a {@link Traversal} and another Optics */
    /***************************************************************/

    /** compose a {@link Traversal} with a {@link Setter} */
    public final <C> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pTraversal.composeSetter(other.pSetter));
    }

    /** compose a {@link Traversal} with a {@link Traversal} */
    public final <C> Traversal<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Traversal<>(pTraversal.composeTraversal(other.pTraversal));
    }

    /*********************************************************************/
    /** Transformation methods to view a {@link Traversal} as another Optics */
    /*********************************************************************/

    /** view a {@link Traversal} as a {@link Setter} */
    @Override
    public final Setter<S, A> asSetter() {
        return new Setter<>(pTraversal.asSetter());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Traversal<S, A> narrow(final _<_<Traversal.µ, S>, A> value) {
        return (Traversal) value;
    }

    public static <S> Traversal<S, S> id() {
        return new Traversal<>(PTraversal.pId());
    }

    public static <S> Traversal<Either<S, S>, S> codiagonal() {
        return new Traversal<>(PTraversal.pCodiagonal());
    }

    public static <S, A> Traversal<S, A> traversal(final Function<S, A> get1, final Function<S, A> get2,
            final BiFunction<A, A, S> set) {
        return new Traversal<>(PTraversal.pTraversal(get1, get2, (a1, a2, s) -> set.apply(a1, a2)));
    }

    public static <S, A> Traversal<S, A> traversal(final Function<S, A> get1, final Function<S, A> get2,
            final Function<S, A> get3,
            final F3<A, A, A, S> set) {
        return new Traversal<>(PTraversal.pTraversal(get1, get2, get3, (a1, a2, a3, s) -> set.apply(a1, a2, a3)));
    }

}
