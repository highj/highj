package org.highj.typeclass1.alternative;

import org.derive4j.hkt.__;
import org.highj.data.List;
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
        return AltUtil.some_many(this, applicative, lazify, fa)._1();
    }

    default <A> __<F,List<A>> many(Applicative<F> applicative, LazifyH<F> lazify, __<F,A> fa) {
        return AltUtil.some_many(this, applicative, lazify, fa)._2();
    }

}
