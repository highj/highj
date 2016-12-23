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

public final class Compose<F, G, A> implements __3<Compose.µ, F, G, A> {

    public interface µ {
    }

    public Compose(__<F, __<G, A>> value) {
        this.value = Objects.requireNonNull(value);
    }

    private final __<F, __<G, A>> value;

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
