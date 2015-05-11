package org.highj.data.optic;

import java.util.function.Function;

import org.highj._;
import org.highj.data.collection.Either;
import org.highj.data.collection.Maybe;
import org.highj.data.functions.F1;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass1.monad.Applicative;

/**
 * A {@link PPrism} can be seen as a pair of functions: - `getOrModify: S => T \/ A` - `reverseGet : B => T`
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

    /** get the target of a {@link PPrism} or modify the source in case there is no target */

    public abstract Either<T, A> getOrModify(S s);

    /** get the modified source of a {@link PPrism} */
    public abstract T reverseGet(B b);

    /** get the target of a {@link PPrism} or nothing if there is no target */
    public abstract Maybe<A> getMaybe(final S s);

    /** modify polymorphically the target of a {@link PPrism} with an Applicative function */
    public final <X> F1<S, _<X, T>> modifyF(final Applicative<X> applicative, final Function<A, _<X, B>> f) {
        return s -> getOrModify(s).either(
                applicative::pure,
                a -> applicative.map(this::reverseGet, f.apply(a))
                );

    }

    /** modify polymorphically the target of a {@link PPrism} with a function */
    public final F1<S, T> modify(final Function<A, B> f) {
        return s -> getOrModify(s).either(F1.id(), a -> reverseGet(f.apply(a)));
    }

    /**
     * modify polymorphically the target of a {@link PPrism} with a function. return empty if the {@link PPrism} is not matching
     */
    public final F1<S, Maybe<T>> modifyMaybe(final Function<A, B> f) {
        return s -> getMaybe(s).map(a -> reverseGet(f.apply(a)));
    }

    /** set polymorphically the target of a {@link PPrism} with a value */
    public final F1<S, T> set(final B b) {
        return modify(__ -> b);
    }

    /** set polymorphically the target of a {@link PPrism} with a value. return empty if the {@link PPrism} is not matching */
    public final F1<S, Maybe<T>> setMaybe(final B b) {
        return modifyMaybe(__ -> b);
    }

    /** check if a {@link PPrism} has a target */
    public final boolean isMatching(final S s) {
        return getMaybe(s).isJust();
    }

    /** create a {@link Getter} from the modified target to the modified source of a {@link PPrism} */
    public final Getter<B, T> re() {
        return Getter.getter(this::reverseGet);
    }

    /************************************************************/
    /** Compose methods between a {@link PPrism} and another Optics */
    /************************************************************/

    /** compose a {@link PPrism} with a {@link Fold} */
    public final <C> Fold<S, C> composeFold(final Fold<A, C> other) {
        return asFold().composeFold(other);
    }

    /** compose a {@link PPrism} with a {@link Getter} */
    public final <C> Fold<S, C> composeGetter(final Getter<A, C> other) {
        return asFold().composeGetter(other);
    }

    /** compose a {@link PPrism} with a {@link PSetter} */
    public final <C, D> PSetter<S, T, C, D> composeSetter(final PSetter<A, B, C, D> other) {
        return asSetter().composeSetter(other);
    }

    /** compose a {@link PPrism} with a {@link PTraversal} */
    public final <C, D> PTraversal<S, T, C, D> composeTraversal(final PTraversal<A, B, C, D> other) {
        return asTraversal().composeTraversal(other);
    }

    /** compose a {@link PPrism} with a {@link POptional} */
    public final <C, D> POptional<S, T, C, D> composeOptional(final POptional<A, B, C, D> other) {
        return asOptional().composeOptional(other);
    }

    /** compose a {@link PPrism} with a {@link PLens} */
    public final <C, D> POptional<S, T, C, D> composeLens(final PLens<A, B, C, D> other) {
        return asOptional().composeOptional(other.asOptional());
    }

    /** compose a {@link PPrism} with a {@link PPrism} */
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

    /** compose a {@link PPrism} with a {@link PIso} */
    public final <C, D> PPrism<S, T, C, D> composeIso(final PIso<A, B, C, D> other) {
        return composePrism(other.asPrism());
    }

    /******************************************************************/
    /** Transformation methods to view a {@link PPrism} as another Optics */
    /******************************************************************/

    /** view a {@link PPrism} as a {@link Fold} */
    public final Fold<S, A> asFold() {
        return new Fold<S, A>() {
            @Override
            public <M> F1<S, M> foldMap(final Monoid<M> monoid, final Function<A, M> f) {
                return s -> getMaybe(s).map(f).getOrElse(monoid.identity());
            }
        };
    }

    /** view a {@link PPrism} as a {@link Setter} */
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

    /** view a {@link PPrism} as a {@link PTraversal} */
    public PTraversal<S, T, A, B> asTraversal() {
        final PPrism<S, T, A, B> self = this;
        return new PTraversal<S, T, A, B>() {

            @Override
            public <X> F1<S, _<X, T>> modifyF(final Applicative<X> applicative, final Function<A, _<X, B>> f) {
                return self.modifyF(applicative, f);
            }

        };
    }

    /** view a {@link PPrism} as a {@link POptional} */
    public POptional<S, T, A, B> asOptional() {
        final PPrism<S, T, A, B> self = this;
        return new POptional<S, T, A, B>() {

            @Override
            public Either<T, A> getOrModify(final S s) {
                return self.getOrModify(s);
            }

            @Override
            public <X> F1<S, _<X, T>> modifyF(final Applicative<X> applicative, final Function<A, _<X, B>> f) {
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

    /** create a {@link PPrism} using the canonical functions: getOrModify and reverseGet */
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