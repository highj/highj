package org.highj.util;

import org.highj.data.Either;

import java.util.function.Supplier;

public class Memo<A> implements Supplier<A> {

    private Either<Supplier<A>,A> either;

    public Memo(Supplier<A> supplier) {
        either = Either.Left(supplier);
    }

    @Override
    public A get() {
        if (either.isLeft()) {
            either = Either.Right(either.getLeft().get());
        }
        return either.getRight();
    }
}
