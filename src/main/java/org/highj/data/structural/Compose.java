package org.highj.data.structural;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__3;
import org.highj.data.eq.Eq1;
import org.highj.data.structural.compose.*;
import org.highj.typeclass1.alternative.Alternative;
import org.highj.typeclass1.foldable.Foldable;
import org.highj.typeclass1.foldable.Traversable;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;
import org.highj.typeclass1.monad.Apply;

import java.util.Objects;

/**
 * A wrapper class allowing to treat nested type constructors like <code>List&lt;Maybe&lt;A&gt;&gt;</code>
 * as a single type, so that certain type class instances can be derived from the instances of the constituting type
 * constructors.
 *
 * @param <F> the outer type constructor type
 * @param <G> the inner type constructor type
 * @param <A> the element type
 */
public final class Compose<F, G, A> implements __3<Compose.µ, F, G, A> {

    public interface µ {
    }

    public Compose(__<F, __<G, A>> value) {
        this.value = Objects.requireNonNull(value);
    }

    private final __<F, __<G, A>> value;

    /**
     * Retrieve the wrapped value.
     *
     * @return the value
     */
    public __<F, __<G, A>> get() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && value.equals(((Compose<?, ?, ?>) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Compose(" + value + ")";
    }

    /**
     * The {@link Functor} instance of {@link Compose}.
     *
     * @param fFunctor the outer {@link Functor}
     * @param gFunctor the inner {@link Functor}
     * @param <F>      the outer type constructor type
     * @param <G>      the inner type constructor type
     * @return the instance
     */
    public static <F, G> ComposeFunctor<F, G> functor(Functor<F> fFunctor, Functor<G> gFunctor) {
        return new ComposeFunctor<F, G>() {
            @Override
            public Functor<F> getF() {
                return fFunctor;
            }

            @Override
            public Functor<G> getG() {
                return gFunctor;
            }
        };
    }

    /**
     * The {@link Foldable} instance of {@link Compose}.
     *
     * @param fFoldable the outer {@link Foldable}
     * @param gFoldable the inner {@link Foldable}
     * @param <F>       the outer type constructor type
     * @param <G>       the inner type constructor type
     * @return the instance
     */
    public static <F, G> ComposeFoldable<F, G> foldable(Foldable<F> fFoldable, Foldable<G> gFoldable) {
        return new ComposeFoldable<F, G>() {
            @Override
            public Foldable<F> getF() {
                return fFoldable;
            }

            @Override
            public Foldable<G> getG() {
                return gFoldable;
            }
        };
    }

    /**
     * The {@link Apply} instance of {@link Compose}.
     *
     * @param fApply the outer {@link Apply}
     * @param gApply the inner {@link Apply}
     * @param <F>    the outer type constructor type
     * @param <G>    the inner type constructor type
     * @return the instance
     */
    public static <F, G> ComposeApply<F, G> apply(Apply<F> fApply, Apply<G> gApply) {
        return new ComposeApply<F, G>() {
            @Override
            public Apply<F> getF() {
                return fApply;
            }

            @Override
            public Apply<G> getG() {
                return gApply;
            }
        };
    }

    /**
     * The {@link Applicative} instance of {@link Compose}.
     *
     * @param fApplicative the outer {@link Applicative}
     * @param gApplicative the inner {@link Applicative}
     * @param <F>          the outer type constructor type
     * @param <G>          the inner type constructor type
     * @return the instance
     */
    public static <F, G> ComposeApplicative<F, G> applicative(Applicative<F> fApplicative, Applicative<G> gApplicative) {
        return new ComposeApplicative<F, G>() {
            @Override
            public Applicative<F> getF() {
                return fApplicative;
            }

            @Override
            public Applicative<G> getG() {
                return gApplicative;
            }
        };
    }

    /**
     * The {@link Alternative} instance of {@link Compose}.
     *
     * @param fAlternative the outer {@link Alternative}
     * @param gApplicative the inner {@link Applicative}
     * @param <F>          the outer type constructor type
     * @param <G>          the inner type constructor type
     * @return the instance
     */
    public static <F, G> ComposeAlternative<F, G> alternative(Alternative<F> fAlternative, Applicative<G> gApplicative) {
        return new ComposeAlternative<F, G>() {
            @Override
            public Alternative<F> getF() {
                return fAlternative;
            }

            @Override
            public Applicative<G> getG() {
                return gApplicative;
            }
        };
    }

    /**
     * The {@link Traversable} instance of {@link Compose}.
     *
     * @param fTraversable the outer {@link Traversable}
     * @param gTraversable the inner {@link Traversable}
     * @param <F>          the outer type constructor type
     * @param <G>          the inner type constructor type
     * @return the instance
     */
    public static <F, G> ComposeTraversable<F, G> traversable(Traversable<F> fTraversable, Traversable<G> gTraversable) {
        return new ComposeTraversable<F, G>() {
            @Override
            public Traversable<F> getF() {
                return fTraversable;
            }

            @Override
            public Traversable<G> getG() {
                return gTraversable;
            }
        };
    }

    /**
     * The {@link Eq1} instance of {@link Compose}.
     *
     * @param eq1F the outer {@link Eq1}
     * @param eq1G the inner {@link Eq1}
     * @param <F>  the outer type constructor type
     * @param <G>  the inner type constructor type
     * @return the instance
     */
    public static <F, G> ComposeEq1<F, G> eq1(Eq1<F> eq1F, Eq1<G> eq1G) {
        return new ComposeEq1<F, G>() {
            @Override
            public Eq1<F> getF() {
                return eq1F;
            }

            @Override
            public Eq1<G> getG() {
                return eq1G;
            }
        };
    }
}
