package org.highj.typeclass1.alternative;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
import org.highj.function.F1;
import org.highj.typeclass1.LazifyH;
import org.highj.typeclass1.monad.Applicative;

class AltUtil {

    static <F,A> T2<__<F,List<A>>,__<F,List<A>>> some_many(Alt<F> alt, Applicative<F> applicative, LazifyH<F> lazify, __<F,A> fa) {
        return F1.fix(
            (T1<T2<__<F,List<A>>,__<F,List<A>>>> rec) -> {
                T2<T1<__<F,List<A>>>,T1<__<F,List<A>>>> rec2 = T2.of(T1.of$(() -> rec._1()._1()), T1.of$(() -> rec._1()._2()));
                return T2.of(
                    applicative.apply2(
                        (A head) -> (List<A> tail) -> List.Cons(head, tail),
                        fa,
                        lazify.lazifyH(rec2._2())
                    ),
                    alt.mplus(lazify.lazifyH(rec2._1()), applicative.pure(List.Nil()))
                );
            }
        );
    }

}
