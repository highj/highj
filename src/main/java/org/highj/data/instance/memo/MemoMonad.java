package org.highj.data.instance.memo;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.Memo;
import org.highj.typeclass1.monad.Monad;

import java.util.function.Function;

import static org.highj.Hkt.asMemo;

public interface MemoMonad extends MemoApplicative, Monad<Memo.µ> {

    @Override
    default <A, B> Memo<B> bind(__<Memo.µ, A> nestedA, Function<A, __<Memo.µ, B>> fn) {
        return asMemo(nestedA).bind(fn.andThen(Hkt::asMemo));
    }
}
