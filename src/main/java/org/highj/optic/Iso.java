package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Category;

/** {@link PIso} when S = T and A = B */
public final class Iso<S, A> extends PIso<S, S, A, A> implements __2<Iso.µ, S, A> {

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

    /** Pair two disjoint {@link Iso}s
     * @param other the second {@link Iso}
     * @param <S1> the souce type of the second {@link Iso}
     * @param <A1> the target type of the second {@link Iso}
     * @return the combined {@link Iso}
     */
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

    /* *************************************************************/
    /* * Compose methods between an {@link Iso} and another Optics */
    /* *************************************************************/

    /** Compose an {@link Iso} with a {@link Setter}
     *  @param other the {@link Setter}
     *  @param <C> the target type of the {@link Setter}
     *  @return the composed {@link Setter}
     */
    public final <C> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pIso.composeSetter(other.pSetter));
    }

    /** Compose an {@link Iso} with a {@link Traversal}
     * @param other the {@link Traversal}
     * @param <C> the target type of the {@link Traversal}
     * @return the composed {@link Traversal}
     */
    public final <C> Traversal<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Traversal<>(pIso.composeTraversal(other.pTraversal));
    }

    /** Compose an {@link Iso} with a {@link Optional}
     * @param other the {@link Optional}
     * @param <C> the target type of the {@link Optional}
     * @return the composed {@link Optional}
     */
    public final <C> Optional<S, C> composeOptional(final Optional<A, C> other) {
        return new Optional<>(pIso.composeOptional(other.pOptional));
    }

    /** Compose an {@link Iso} with a {@link Prism}
     * @param other the {@link Prism}
     * @param <C> the target type of the {@link Prism}
     * @return the composed {@link Prism}
     */
    public final <C> Prism<S, C> composePrism(final Prism<A, C> other) {
        return new Prism<>(pIso.composePrism(other.pPrism));
    }

    /** Compose an {@link Iso} with a {@link Lens}
     * @param other the {@link Lens}
     * @param <C> the target type of the {@link Lens}
     * @return the composed {@link Lens}
     */
    public final <C> Lens<S, C> composeLens(final Lens<A, C> other) {
        return asLens().composeLens(other);
    }

    /** Compose an {@link Iso} with an {@link Iso}
     * @param other the second {@link Iso}
     * @param <C> the target type of the second {@link Iso}
     * @return the composed {@link Iso}
     */
    public final <C> Iso<S, C> composeIso(final Iso<A, C> other) {
        return new Iso<>(pIso.composeIso(other.pIso));
    }

    /* *******************************************************************/
    /* * Transformation methods to view an {@link Iso} as another Optics */
    /* *******************************************************************/

    /** View an {@link Iso} as a {@link Setter}
     * @return the {@link Setter}
     */
    @Override
    public final Setter<S, A> asSetter() {
        return new Setter<>(pIso.asSetter());
    }

    /** View an {@link Iso} as a {@link Traversal}
     * @return the {@link Traversal}
     */
    @Override
    public final Traversal<S, A> asTraversal() {
        return new Traversal<>(pIso.asTraversal());
    }

    /** View an {@link Iso} as a {@link Optional}
     * @return the {@link Optional}
     */
    @Override
    public final Optional<S, A> asOptional() {
        return new Optional<>(pIso.asOptional());
    }

    /** View an {@link Iso} as a {@link Prism}
     * @return the {@link Prism}
     */
    @Override
    public final Prism<S, A> asPrism() {
        return new Prism<>(pIso.asPrism());
    }

    /** View an {@link Iso} as a {@link Lens}
     * @return the {@link Lens}
     */
    @Override
    public final Lens<S, A> asLens() {
        return new Lens<>(pIso.asLens());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Iso<S, A> narrow(final __<__<µ, S>, A> value) {
        return (Iso) value;
    }

    /** Create an {@link Iso} using a pair of functions: one to get the target and one to get the source.
     * @param get function to get the target
     * @param reverseGet function to get the source
     * @param <S> the source type of the {@link Iso}
     * @param <A> the target type of the {@link Iso}
     * @return the {@link Iso}
     */
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
     *
     * @param <S> the source type
     * @return the {@link Iso}
     */
    public static <S> Iso<S, S> id() {
        return new Iso<>(PIso.pId());
    }

    public static final Category<Iso.µ> isoCategory = new Category<Iso.µ>() {

        @Override
        public <B, C, D> __2<µ, B, D> dot(final __2<µ, C, D> cd, final __2<µ, B, C> bc) {
            return narrow(bc).composeIso(narrow(cd));
        }

        @Override
        public <B> __2<µ, B, B> identity() {
            return id();
        }
    };
}
