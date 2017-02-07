package org.highj.util;

import org.highj.data.Either;

import java.util.function.Supplier;

public interface Memo<A> extends Supplier<A> {

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

    static <A> Memo<A> of(Supplier<A> supplier) {
        return supplier instanceof Memo
                ? (Memo<A>) supplier
                : new Memo$<>(supplier);
    }

    static <A> Memo<A> from(A a) {
        return () -> a;
    }
}
