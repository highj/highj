package org.highj.data.instance.memo;

import org.derive4j.hkt.__;
import org.highj.data.Memo;
import org.highj.typeclass1.monad.Applicative;

import java.util.function.Function;

import static org.highj.Hkt.asMemo;

public interface MemoApplicative extends MemoFunctor, Applicative<Memo.µ> {

    @Override
    default <A> Memo<A> pure(A a) {
        return Memo.from(a);
    }

    @Override
    default <A, B> Memo<B> ap(__<Memo.µ, Function<A, B>> fn, __<Memo.µ, A> nestedA) {
        return asMemo(nestedA).ap(asMemo(fn));
    }
}
