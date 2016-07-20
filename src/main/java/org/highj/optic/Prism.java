package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.typeclass2.arrow.Category;

/**
 * {@link PPrism} restricted to monomorphic update
 */
public final class Prism<S, A> extends PPrism<S, S, A, A> implements __2<Prism.µ, S, A> {

    public static final class µ {
    }

    final PPrism<S, S, A, A> pPrism;

    public Prism(final PPrism<S, S, A, A> pPrism) {
        this.pPrism = pPrism;
    }

    @Override
    public Either<S, A> getOrModify(final S s) {
        return pPrism.getOrModify(s);
    }

    @Override
    public S reverseGet(final A a) {
        return pPrism.reverseGet(a);
    }

    @Override
    public Maybe<A> getMaybe(final S s) {
        return pPrism.getMaybe(s);
    }

    /* **************************************************************/
    /* * Compose methods between a {@link Prism} and another Optics */
    /* **************************************************************/

    /** Compose a {@link Prism} with a {@link Setter}
     * @param other the {@link Setter}
     * @param <C> the target type of the {@link Setter}
     * @return the composed {@link Setter}
     */
    public final <C> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pPrism.composeSetter(other.pSetter));
    }

    /** Compose a {@link Prism} with a {@link Traversal}
     * @param other the {@link Traversal}
     * @param <C> the target type of the {@link Traversal}
     * @return the composed {@link Traversal}
     */
    public final <C> Traversal<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Traversal<>(pPrism.composeTraversal(other.pTraversal));
    }

    /** Compose a {@link Prism} with a {@link Optional}
     * @param other the {@link Optional}
     * @param <C> the target type of the {@link Optional}
     * @return the composed {@link Optional}
     */
    public final <C> Optional<S, C> composeOptional(final Optional<A, C> other) {
        return new Optional<>(pPrism.composeOptional(other.pOptional));
    }

    /** Compose a {@link Prism} with a {@link Lens}
     * @param other the {@link Lens}
     * @param <C> the target type of the {@link Lens}
     * @return the composed {@link Optional}
     */
    public final <C> Optional<S, C> composeLens(final Lens<A, C> other) {
        return new Optional<>(pPrism.composeLens(other.pLens));
    }

    /** Compose a {@link Prism} with a {@link Prism}
     * @param other the second {@link Prism}
     * @param <C> the target type of the second {@link Prism}
     * @return the composed {@link Prism}
     */
    public final <C> Prism<S, C> composePrism(final Prism<A, C> other) {
        return new Prism<>(pPrism.composePrism(other.pPrism));
    }

    /** Compose a {@link Prism} with an {@link Iso}
     * @param other the {@link Iso}
     * @param <C> the target type of the {@link Iso}
     * @return the composed {@link Prism}
     */
    public final <C> Prism<S, C> composeIso(final Iso<A, C> other) {
        return new Prism<>(pPrism.composeIso(other.pIso));
    }

    /* ********************************************************************/
    /* * Transformation methods to view a {@link Prism} as another Optics */
    /* ********************************************************************/

    /** View a {@link Prism} as a {@link Setter}
     * @return the {@link Setter}
     */
    @Override
    public final Setter<S, A> asSetter() {
        return new Setter<>(pPrism.asSetter());
    }

    /** view a {@link Prism} as a {@link Traversal}
     * @return the {@link Traversal}
     */
    @Override
    public final Traversal<S, A> asTraversal() {
        return new Traversal<>(pPrism.asTraversal());
    }

    /** view a {@link Prism} as a {@link Optional}
     * @return the {@link Optional}
     */
    @Override
    public final Optional<S, A> asOptional() {
        return new Optional<>(pPrism.asOptional());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Prism<S, A> narrow(final __<__<µ, S>, A> value) {
        return (Prism) value;
    }

    public static <S> Prism<S, S> id() {
        return new Prism<>(PPrism.pId());
    }

    public static <S, A> Prism<S, A> prism(final Function<S, Maybe<A>> getMaybe, final Function<A, S> reverseGet) {
        return new Prism<>(new PPrism<S, S, A, A>() {

            @Override
            public Either<S, A> getOrModify(final S s) {
                return getMaybe.apply(s).cata(Either.<S, A>Left(s), Either::<S, A>Right);
            }

            @Override
            public S reverseGet(final A a) {
                return reverseGet.apply(a);
            }

            @Override
            public Maybe<A> getMaybe(final S s) {
                return getMaybe.apply(s);
            }
        });
    }

    public static final Category<Prism.µ> prismCategory = new Category<Prism.µ>() {

        @Override
        public <B, C, D> __2<µ, B, D> dot(final __2<µ, C, D> cd, final __2<µ, B, C> bc) {
            return narrow(bc).composePrism(narrow(cd));
        }

        @Override
        public <B> __2<µ, B, B> identity() {
            return id();
        }
    };

}