package org.highj.data;

import org.derive4j.hkt.__;
import org.highj.data.instance.memo.MemoApplicative;
import org.highj.data.instance.memo.MemoComonad;
import org.highj.data.instance.memo.MemoFunctor;
import org.highj.data.instance.memo.MemoMonad;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A memoizing {@link Supplier}.
 *
 * @param <A> the element type.
 */
public interface Memo<A> extends Supplier<A>, __<Memo.µ, A> {

    interface µ{}

    final class Memo$<A> implements Memo<A> {
        private volatile Either<Supplier<A>, A> either;

        private Memo$(Supplier<A> supplier) {
            either = Either.Left(supplier);
        }

        @Override
        public A get() {
            if (either.isLeft()) {
                synchronized (this) {
                    if (either.isLeft()) {
                        either = Either.Right(either.getLeft().get());
                    }
                }
            }
            return either.getRight();
        }
    }

    /**
     * A {@link Memo} initialized by a {@link Supplier}.
     *
     * Note that if the given {@link Supplier} is already a {@link Memo},
     * it won't be wrapped unnecessarily.
     *
     * @param supplier the underlying supplier
     * @param <A> the element type
     * @return the {@link Memo} instance
     */
    static <A> Memo<A> of(Supplier<A> supplier) {
        return supplier instanceof Memo
                ? (Memo<A>) supplier
                : new Memo$<>(supplier);
    }

    /**
     * A {@link Memo} initialized by an already evaluated value.
     * @param a the value
     * @param <A> the element type
     * @return the {@link Memo} instance
     */
    static <A> Memo<A> from(A a) {
        return () -> a;
    }

    /**
     * Maps the content in a lazy way.
     * @param fn the transformation function
     * @param <B> the result type
     * @return the transformed {@link Memo}
     */
    default <B> Memo<B> map(Function<A, B> fn) {
        return Memo.of(() -> fn.apply(get()));
    }

    /**
     * Maps the content in a lazy way, when the transformation function is also
     * wrapped as a {@link Memo}.
     * @param fn the {@link Memo} wrapping the transformation function
     * @param <B> the result type
     * @return the transformed {@link Memo}
     */
    default <B> Memo<B> ap(Memo<Function<A,B>> fn) {
        return Memo.of(() -> fn.get().apply(get()));
    }

    /**
     * Maps the content in a lazy way, when the transformation function returns a value
     * wrapped as a {@link Memo}.
     * @param fn the transformation function
     * @param <B> the result type
     * @return the transformed {@link Memo}
     */
    default <B> Memo<B> bind(Function<A, Memo<B>> fn) {
        return Memo.of(() -> fn.apply(get()).get());
    }

    /**
     * The functor type class of {@link Memo}.
     */
    MemoFunctor memoFunctor = new MemoFunctor() {};

    /**
     * The applicative type class of {@link Memo}.
     */
    MemoApplicative memoApplicative = new MemoApplicative() {};

    /**
     * The monad type class of {@link Memo}.
     */
    MemoMonad memoMonad = new MemoMonad() {};

    /**
     * The comonad type class of {@link Memo}.
     */
    MemoComonad memoComonad = new MemoComonad() {};
}
