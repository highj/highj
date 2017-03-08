package org.highj.data.instance.memo;

import org.derive4j.hkt.__;
import org.highj.data.Memo;
import org.highj.typeclass1.comonad.Comonad;

import static org.highj.Hkt.asMemo;

public interface MemoComonad extends MemoFunctor, Comonad<Memo.µ> {
    @Override
    default <A> A extract(__<Memo.µ, A> nestedA) {
        return asMemo(nestedA).get();
    }

    @Override
    default <A> Memo<__<Memo.µ, A>> duplicate(__<Memo.µ, A> nestedA) {
        return Memo.from(nestedA);
    }
}
