package org.highj.data.optic;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.data.collection.Either;
import org.highj.data.collection.Maybe;
import org.highj.typeclass2.arrow.Category;

/**
 * {@link PPrism} restricted to monomorphic update
 */
public final class Prism<S, A> extends PPrism<S, S, A, A> implements __<Prism.µ, S, A> {

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

    /***********************************************************/
    /** Compose methods between a {@link Prism} and another Optics */
    /***********************************************************/

    /** compose a {@link Prism} with a {@link Setter} */
    public final <C, D> Setter<S, C> composeSetter(final Setter<A, C> other) {
        return new Setter<>(pPrism.composeSetter(other.pSetter));
    }

    /** compose a {@link Prism} with a {@link Traversal} */
    public final <C, D> Traversal<S, C> composeTraversal(final Traversal<A, C> other) {
        return new Traversal<>(pPrism.composeTraversal(other.pTraversal));
    }

    /** compose a {@link Prism} with a {@link Optional} */
    public final <C, D> Optional<S, C> composeOptional(final Optional<A, C> other) {
        return new Optional<>(pPrism.composeOptional(other.pOptional));
    }

    /** compose a {@link Prism} with a {@link Lens} */
    public final <C, D> Optional<S, C> composeLens(final Lens<A, C> other) {
        return new Optional<>(pPrism.composeLens(other.pLens));
    }

    /** compose a {@link Prism} with a {@link Prism} */
    public final <C> Prism<S, C> composePrism(final Prism<A, C> other) {
        return new Prism<>(pPrism.composePrism(other.pPrism));
    }

    /** compose a {@link Prism} with an {@link Iso} */
    public final <C, D> Prism<S, C> composeIso(final Iso<A, C> other) {
        return new Prism<>(pPrism.composeIso(other.pIso));
    }

    /*****************************************************************/
    /** Transformation methods to view a {@link Prism} as another Optics */
    /*****************************************************************/

    /** view a {@link Prism} as a {@link Setter} */
    @Override
    public final Setter<S, A> asSetter() {
        return new Setter<>(pPrism.asSetter());
    }

    /** view a {@link Prism} as a {@link Traversal} */
    @Override
    public final Traversal<S, A> asTraversal() {
        return new Traversal<>(pPrism.asTraversal());
    }

    /** view a {@link Prism} as a {@link Optional} */
    @Override
    public final Optional<S, A> asOptional() {
        return new Optional<>(pPrism.asOptional());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <S, A> Prism<S, A> narrow(final _<__.µ<Prism.µ, S>, A> value) {
        return (Prism) value;
    }

    public static <S> Prism<S, S> id() {
        return new Prism<>(PPrism.pId());
    }

    /** create a {@link Prism} using the canonical functions: getOrModify and reverseGet */
    public static <S, A> Prism<S, A> prism(final Function<S, Either<S, A>> getOrModify, final Function<A, S> reverseGet) {
        return new Prism<>(PPrism.pPrism(getOrModify, reverseGet));
    }

    public static final Category<Prism.µ> prismCategory = new Category<Prism.µ>() {

        @Override
        public <B, C, D> __<Prism.µ, B, D> dot(final __<Prism.µ, C, D> cd, final __<Prism.µ, B, C> bc) {
            return narrow(bc).composePrism(narrow(cd));
        }

        @Override
        public <B> __<Prism.µ, B, B> identity() {
            return id();
        }
    };

}