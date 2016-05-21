package org.highj.data.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.function.F1;
import org.highj.typeclass1.monad.Applicative;

/**
 * {@link PLens} with a monomorphic set function
 */
public final class Lens<S, A> extends PLens<S, S, A, A> implements __2<Lens.µ, S, A> {

    public static final class µ {
    }

    final PLens<S, S, A, A> pLens;

    public Lens(final PLens<S, S, A, A> pLens) {
        this.pLens = pLens;
    }

    @Override
    public A get(final S s) {
        return pLens.get(s);
    }

    @Override
    public F1<S, S> set(final A a) {
        return pLens.set(a);
    }

    @Override
    public <X> F1<S, __<X, S>> modifyF(final Applicative<X> applicative, final Function<A, __<X, A>> f) {
        return pLens.modifyF(applicative, f);
    }

    @Override
    public F1<S, S> modify(final Function<A, A> f) {
        return pLens.modify(f);
    }

    /** join two {@link Lens} with the same target */
    public final <S1> Lens<Either<S, S1>, A> sum(final Lens<S1, A> other) {
        return new Lens<>(pLens.sum(other.pLens));
    }

    /**********************************************************/
    /** Compose methods between a {@link Lens} and another Optics */
    /**********************************************************/

    /**
     * compose a {@link Lens} with a {@link Setter}
     */
    public final <C> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pLens.composeSetter(other.pSetter));
    }

    /**
     * compose a {@link Lens} with a {@link Traversal}
     */
    public final <C> Traversal<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Traversal<>(pLens.composeTraversal(other.pTraversal));
    }

    /** compose a {@link Lens} with an {@link Optional} */
    public final <C> Optional<S, C> composeOptional(final Optional<A, C> other) {
        return new Optional<>(pLens.composeOptional(other.pOptional));
    }

    /** compose a {@link Lens} with a {@link Prism} */
    public final <C> Optional<S, C> composePrism(final Prism<A, C> other) {
        return new Optional<>(pLens.composePrism(other.pPrism));
    }

    /** compose a {@link Lens} with a {@link Lens} */
    public final <C> Lens<S, C> composeLens(final Lens<A, C> other) {
        return new Lens<>(pLens.composeLens(other.pLens));
    }

    /** compose a {@link Lens} with an {@link Iso} */
    public final <C> Lens<S, C> composeIso(final Iso<A, C> other) {
        return new Lens<>(pLens.composeIso(other.pIso));
    }

    /****************************************************************/
    /** Transformation methods to view a {@link Lens} as another Optics */
    /****************************************************************/

    /** view a {@link Lens} as a {@link Setter} */
    @Override
    public Setter<S, A> asSetter() {
        return new Setter<>(pLens.asSetter());
    }

    /** view a {@link Lens} as a {@link Traversal} */
    @Override
    public final Traversal<S, A> asTraversal() {
        return new Traversal<>(pLens.asTraversal());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Lens<S, A> narrow(final __<__<µ, S>, A> value) {
        return (Lens) value;
    }

    /** view a {@link Lens} as an {@link Optional} */
    @Override
    public final Optional<S, A> asOptional() {
        return new Optional<>(pLens.asOptional());
    }

    public static final <S> Lens<S, S> id() {
        return new Lens<>(PLens.pId());
    }

    /**
     * create a {@link Lens} using a pair of functions: one to get the target, one to set the target.
     */
    public static <S, A> Lens<S, A> lens(final Function<S, A> get, final Function<A, F1<S, S>> set) {
        return new Lens<>(PLens.pLens(get, set));
    }

}