package org.highj.data.structural;

import org.derive4j.hkt.__2;
import org.highj.data.structural.tagged.*;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Tagged is a wrapped value with an attached phantom type.
 *
 * @param <S> the phantom type
 * @param <A> the value type
 */
public final class Tagged<S, A> implements __2<Tagged.µ, S, A>, Supplier<A> {

    public interface µ {
    }

    private final A value;

    private Tagged(A value) {
        this.value = value;
    }

    public static <S, A> Tagged<S, A> Tagged(A value) {
        return new Tagged<>(value);
    }

    @Override
    public A get() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public <T> Tagged<T, A> retag() {
        return (Tagged<T, A>) this;
    }

    public <B> Tagged<S, B> map(Function<A, B> fn) {
        return new Tagged<>(fn.apply(value));
    }

    public static <S> TaggedFunctor<S> functor() {
        return new TaggedFunctor<S>() {
        };
    }

    public static <S> TaggedApply<S> apply() {
        return new TaggedApply<S>() {
        };
    }

    public static <S> TaggedApplicative<S> applicative() {
        return new TaggedApplicative<S>() {
        };
    }

    public static <S> TaggedMonad<S> monad() {
        return new TaggedMonad<S>() {
        };
    }

    public static final TaggedProfunctor profunctor = new TaggedProfunctor() {
    };

}
