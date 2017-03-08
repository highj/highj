package org.highj.data.instance.memo;

import org.derive4j.hkt.__;
import org.highj.data.Memo;
import org.highj.typeclass1.functor.Functor;

import java.util.function.Function;

import static org.highj.Hkt.asMemo;

public interface MemoFunctor extends Functor<Memo.µ> {
    @Override
    default <A, B> Memo<B> map(Function<A, B> fn, __<Memo.µ, A> nestedA) {
        return asMemo(nestedA).map(fn);
    }
}
