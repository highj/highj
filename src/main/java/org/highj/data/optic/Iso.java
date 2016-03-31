package org.highj.data.optic;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Category;

/** {@link PIso} when S = T and A = B */
public final class Iso<S, A> extends PIso<S, S, A, A> implements __<Iso.µ, S, A> {

    public static final class µ {
    }

    final PIso<S, S, A, A> pIso;

    public Iso(final PIso<S, S, A, A> pIso) {
        this.pIso = pIso;
    }

    @Override
    public A get(final S s) {
        return pIso.get(s);
    }

    @Override
    public S reverseGet(final A a) {
        return pIso.reverseGet(a);
    }

    @Override
    public Iso<A, S> reverse() {
        return new Iso<>(pIso.reverse());
    }

    /** pair two disjoint {@link Iso} */
    public <S1, A1> Iso<T2<S, S1>, T2<A, A1>> product(final Iso<S1, A1> other) {
        return new Iso<>(pIso.product(other.pIso));
    }

    @Override
    public <C> Iso<T2<S, C>, T2<A, C>> first() {
        return new Iso<>(pIso.first());
    }

    @Override
    public <C> Iso<T2<C, S>, T2<C, A>> second() {
        return new Iso<>(pIso.second());
    }

    /**********************************************************/
    /** Compose methods between an {@link Iso} and another Optics */
    /**********************************************************/

    /** compose an {@link Iso} with a {@link Setter} */
    public final <C> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pIso.composeSetter(other.pSetter));
    }

    /** compose an {@link Iso} with a {@link Traversal} */
    public final <C> Traversal<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Traversal<>(pIso.composeTraversal(other.pTraversal));
    }

    /** compose an {@link Iso} with a {@link Optional} */
    public final <C> Optional<S, C> composeOptional(final Optional<A, C> other) {
        return new Optional<>(pIso.composeOptional(other.pOptional));
    }

    /** compose an {@link Iso} with a {@link Prism} */
    public final <C> Prism<S, C> composePrism(final Prism<A, C> other) {
        return new Prism<>(pIso.composePrism(other.pPrism));
    }

    /** compose an {@link Iso} with a {@link Lens} */
    public final <C> Lens<S, C> composeLens(final Lens<A, C> other) {
        return asLens().composeLens(other);
    }

    /** compose an {@link Iso} with an {@link Iso} */
    public final <C> Iso<S, C> composeIso(final Iso<A, C> other) {
        return new Iso<>(pIso.composeIso(other.pIso));
    }

    /****************************************************************/
    /** Transformation methods to view an {@link Iso} as another Optics */
    /****************************************************************/

    /** view an {@link Iso} as a {@link Setter} */
    @Override
    public final Setter<S, A> asSetter() {
        return new Setter<>(pIso.asSetter());
    }

    /** view an {@link Iso} as a {@link Traversal} */
    @Override
    public final Traversal<S, A> asTraversal() {
        return new Traversal<>(pIso.asTraversal());
    }

    /** view an {@link Iso} as a {@link Optional} */
    @Override
    public final Optional<S, A> asOptional() {
        return new Optional<>(pIso.asOptional());
    }

    /** view an {@link Iso} as a {@link Prism} */
    @Override
    public final Prism<S, A> asPrism() {
        return new Prism<>(pIso.asPrism());
    }

    /** view an {@link Iso} as a {@link Lens} */
    @Override
    public final Lens<S, A> asLens() {
        return new Lens<>(pIso.asLens());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Iso<S, A> narrow(final _<_<Iso.µ, S>, A> value) {
        return (Iso) value;
    }

    /** create an {@link Iso} using a pair of functions: one to get the target and one to get the source. */
    public static <S, A> Iso<S, A> iso(final Function<S, A> get, final Function<A, S> reverseGet) {
        return new Iso<>(PIso.pIso(get, reverseGet));
    }

    /**
     * create an {@link Iso} between any type and itself. id is the zero element of optics composition, for all optics o of type
     * O (e.g. Lens, Iso, Prism, ...):
     *
     * <pre>
     *  o composeIso Iso.id == o
     *  Iso.id composeO o == o
     * </pre>
     *
     * (replace composeO by composeLens, composeIso, composePrism, ...)
     */
    public static <S> Iso<S, S> id() {
        return new Iso<>(PIso.pId());
    }

    public static final Category<Iso.µ> isoCategory = new Category<Iso.µ>() {

        @Override
        public <B, C, D> __<Iso.µ, B, D> dot(final __<Iso.µ, C, D> cd, final __<Iso.µ, B, C> bc) {
            return narrow(bc).composeIso(narrow(cd));
        }

        @Override
        public <B> __<Iso.µ, B, B> identity() {
            return id();
        }
    };
}
