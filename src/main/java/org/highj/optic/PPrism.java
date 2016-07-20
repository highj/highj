package org.highj.optic;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.function.F1;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

/**
 * A {@link PPrism} can be seen as a pair of functions: - `getOrModify: S =&gt; T \/ A` - `reverseGet : B =&gt; T`
 *
 * A {@link PPrism} could also be defined as a weaker {@link PIso} where get can fail.
 *
 * Typically a {@link PPrism} or {@link Prism} encodes the relation between a Sum or CoProduct type (e.g. sealed trait) and one
 * of it is element.
 *
 * {@link PPrism} stands for Polymorphic Prism as it set and modify methods change a type `A` to `B` and `S` to `T`.
 * {@link Prism} is a {@link PPrism} where the type of target cannot be modified.
 *
 * A {@link PPrism} is also a valid {@link Fold}, {@link POptional}, {@link PTraversal} and {@link PSetter}
 *
 * @param <S> the source of a {@link PPrism}
 * @param <T> the modified source of a {@link PPrism}
 * @param <A> the target of a {@link PPrism}
 * @param <B> the modified target of a {@link PPrism}
 */
public abstract class PPrism<S, T, A, B> {

    PPrism() {
        super();
    }

    /** Get the target of a {@link PPrism} or modify the source in case there is no target
     * @param s the source value
     * @return the {@link Either}
     */
    public abstract Either<T, A> getOrModify(S s);

    /** Get the modified source of a {@link PPrism}
     * @param b the modified target value
     * @return the modified source
     */
    public abstract T reverseGet(B b);

    /** Get the target of a {@link PPrism} or nothing if there is no target
     * @param s the source value
     * @return the {@link Maybe}
     */
    public abstract Maybe<A> getMaybe(final S s);

    /** Modify polymorphically the target of a {@link PPrism} with an Applicative function
     * @param applicative the applicative functor
     * @param f target modification function
     * @param <X> applicative type
     * @return result function
     */
    public final <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
        return s -> getOrModify(s).either(
                applicative::pure,
                a -> applicative.map(this::reverseGet, f.apply(a))
                );
    }

    /** Modify polymorphically the target of a {@link PPrism} with a function
     * @param f target modification function
     * @return source modifying function
     */
    public final F1<S, T> modify(final Function<A, B> f) {
        return s -> getOrModify(s).either(F1.id(), a -> reverseGet(f.apply(a)));
    }

    /** Modify polymorphically the target of a {@link PPrism} with a function. Return empty if the {@link PPrism} is not matching
     * @param f the target modifying function
     * @return the partial source modifying function
     */
    public final F1<S, Maybe<T>> modifyMaybe(final Function<A, B> f) {
        return s -> getMaybe(s).map(a -> reverseGet(f.apply(a)));
    }

    /** Set polymorphically the target of a {@link PPrism} with a value
     * @param b the target value
     * @return the source modifying function
     */
    public final F1<S, T> set(final B b) {
        return modify(__ -> b);
    }

    /** Set polymorphically the target of a {@link PPrism} with a value. Return empty if the {@link PPrism} is not matching
     * @param b the target value
     * @return the partial, source modifying function
     */
    public final F1<S, Maybe<T>> setMaybe(final B b) {
        return modifyMaybe(__ -> b);
    }

    /** Check if a {@link PPrism} has a target
     * @param s the modified source value
     * @return true if matching
     */
    public final boolean isMatching(final S s) {
        return getMaybe(s).isJust();
    }

    /** Create a {@link Getter} from the modified target to the modified source of a {@link PPrism}
     * @return the {@link Getter}
     */
    public final Getter<B, T> re() {
        return Getter.getter(this::reverseGet);
    }

    /* ***************************************************************/
    /* * Compose methods between a {@link PPrism} and another Optics */
    /* ***************************************************************/

    /** Compose a {@link PPrism} with a {@link Fold}
     * @param other the {@link Fold}
     * @param <C> the target type of the {@link Fold}
     * @return the composed {@link Fold}
     */
    public final <C> Fold<S, C> composeFold(final Fold<A, C> other) {
        return asFold().composeFold(other);
    }

    /** Compose a {@link PPrism} with a {@link Getter}
     * @param other the {@link Getter}
     * @param <C> the target type of the {@link Getter}
     * @return the composed {@link Fold}
     */
    public final <C> Fold<S, C> composeGetter(final Getter<A, C> other) {
        return asFold().composeGetter(other);
    }

    /** Compose a {@link PPrism} with a {@link PSetter}
     * @param other the {@link PSetter}
     * @param <C> the target type of the {@link PSetter}
     * @param <D> the modified target type of the {@link PSetter}
     * @return the composed {@link PSetter}
     */
    public final <C, D> PSetter<S, T, C, D> composeSetter(final PSetter<A, B, C, D> other) {
        return asSetter().composeSetter(other);
    }

    /** Compose a {@link PPrism} with a {@link PTraversal}
     * @param other the {@link PTraversal}
     * @param <C> the target type of the {@link PTraversal}
     * @param <D> the modified target type of the {@link PTraversal}
     * @return the composed {@link PTraversal}
     */
    public final <C, D> PTraversal<S, T, C, D> composeTraversal(final PTraversal<A, B, C, D> other) {
        return asTraversal().composeTraversal(other);
    }

    /** Compose a {@link PPrism} with a {@link POptional}
     * @param other the {@link POptional}
     * @param <C> the target type of the {@link POptional}
     * @param <D> the modified target type of the {@link POptional}
     * @return the composed {@link POptional}
     */
    public final <C, D> POptional<S, T, C, D> composeOptional(final POptional<A, B, C, D> other) {
        return asOptional().composeOptional(other);
    }

    /** Compose a {@link PPrism} with a {@link PLens}
     * @param other the {@link PLens}
     * @param <C> the target type of the {@link PLens}
     * @param <D> the modified target type of the {@link PLens}
     * @return the composed {@link POptional}
     */
    public final <C, D> POptional<S, T, C, D> composeLens(final PLens<A, B, C, D> other) {
        return asOptional().composeOptional(other.asOptional());
    }

    /** Compose a {@link PPrism} with a {@link PPrism}
     * @param other the second {@link PPrism}
     * @param <C> the target type of the second {@link PPrism}
     * @param <D> the modified target type of the second {@link PPrism}
     * @return the composed {@link PPrism}
     */
    public final <C, D> PPrism<S, T, C, D> composePrism(final PPrism<A, B, C, D> other) {
        return new PPrism<S, T, C, D>() {

            @Override
            public Either<T, C> getOrModify(final S s) {
                return Either.<T> monad().bind(PPrism.this.getOrModify(s),
                        a -> other.getOrModify(a).bimap(b -> PPrism.this.set(b).apply(s), F1.id())
                        );
            }

            @Override
            public T reverseGet(final D d) {
                return PPrism.this.reverseGet(other.reverseGet(d));
            }

            @Override
            public Maybe<C> getMaybe(final S s) {
                return PPrism.this.getMaybe(s).bind(other::getMaybe);
            }
        };
    }

    /** Compose a {@link PPrism} with a {@link PIso}
     * @param other the {@link PIso}
     * @param <C> the target type of the {@link PIso}
     * @param <D> the modified target type of the {@link PIso}
     * @return the composed {@link PPrism}
     */
    public final <C, D> PPrism<S, T, C, D> composeIso(final PIso<A, B, C, D> other) {
        return composePrism(other.asPrism());
    }

    /* *********************************************************************/
    /* * Transformation methods to view a {@link PPrism} as another Optics */
    /* *********************************************************************/

    /** View a {@link PPrism} as a {@link Fold}
     * @return the {@link Fold}
     */
    public final Fold<S, A> asFold() {
        return new Fold<S, A>() {
            @Override
            public <M> F1<S, M> foldMap(final Monoid<M> monoid, final Function<A, M> f) {
                return s -> getMaybe(s).map(f).getOrElse(monoid.identity());
            }
        };
    }

    /** View a {@link PPrism} as a {@link PSetter}
     * @return the {@link PSetter}
     */
    public PSetter<S, T, A, B> asSetter() {
        return new PSetter<S, T, A, B>() {
            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return PPrism.this.modify(f);
            }

            @Override
            public F1<S, T> set(final B b) {
                return PPrism.this.set(b);
            }
        };
    }

    /** View a {@link PPrism} as a {@link PTraversal}
     * @return the {@link PTraversal}
     */
    public PTraversal<S, T, A, B> asTraversal() {
        final PPrism<S, T, A, B> self = this;
        return new PTraversal<S, T, A, B>() {

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return self.modifyF(applicative, f);
            }

        };
    }

    /** View a {@link PPrism} as a {@link POptional}
     * @return the {@link POptional}
     */
    public POptional<S, T, A, B> asOptional() {
        final PPrism<S, T, A, B> self = this;
        return new POptional<S, T, A, B>() {

            @Override
            public Either<T, A> getOrModify(final S s) {
                return self.getOrModify(s);
            }

            @Override
            public <X> F1<S, __<X, T>> modifyF(final Applicative<X> applicative, final Function<A, __<X, B>> f) {
                return self.modifyF(applicative, f);
            }

            @Override
            public F1<S, T> set(final B b) {
                return self.set(b);
            }

            @Override
            public Maybe<A> getMaybe(final S s) {
                return self.getMaybe(s);
            }

            @Override
            public F1<S, T> modify(final Function<A, B> f) {
                return self.modify(f);
            }

        };
    }

    public static <S, T> PPrism<S, T, S, T> pId() {
        return PIso.<S, T> pId().asPrism();
    }

    /** Create a {@link PPrism} using the canonical functions: getOrModify and reverseGet
     * @param getOrModify the getModify function
     * @param reverseGet the reverseGet function
     * @param <S> the source type
     * @param <T> the modified source type
     * @param <A> the target type
     * @param <B> the modified target type
     * @return the {@link PPrism}
     */
    public static <S, T, A, B> PPrism<S, T, A, B> pPrism(final Function<S, Either<T, A>> getOrModify,
            final Function<B, T> reverseGet) {
        return new PPrism<S, T, A, B>() {

            @Override
            public Either<T, A> getOrModify(final S s) {
                return getOrModify.apply(s);
            }

            @Override
            public T reverseGet(final B b) {
                return reverseGet.apply(b);
            }

            @Override
            public Maybe<A> getMaybe(final S s) {
                return getOrModify.apply(s).maybeRight();
            }
        };
    }

}