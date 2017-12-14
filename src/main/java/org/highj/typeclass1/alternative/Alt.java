package org.highj.typeclass1.alternative;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.tuple.T1;
import org.highj.typeclass1.LazifyH;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.monad.Applicative;

public interface Alt<F> extends Functor<F> {

    <A> __<F, A> mplus(__<F, A> first, __<F, A> second);

    default <A> Semigroup<__<F, A>> asSemigroup() {
        return Alt.this::mplus;
    }

    default <A> __<F,List<A>> some(Applicative<F> applicative, LazifyH<F> lazify, __<F,A> fa) {
        return applicative.apply2(
            (A head) -> (List<A> tail) -> List.Cons(head, tail),
            fa,
            lazify.lazifyH(T1.of$(() -> many(applicative, lazify, fa)))
        );
    }

    default <A> __<F,List<A>> many(Applicative<F> applicative, LazifyH<F> lazify, __<F,A> fa) {
        return mplus(some(applicative, lazify, fa), applicative.pure(List.Nil()));
    }

}
