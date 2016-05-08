/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.collection.Either;
import org.highj.data.transformer.ErrorArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowError;

/**
 *
 * @author clintonselke
 */
public interface ErrorArrowArrowError<EX,A> extends ErrorArrowArrow<EX,A>, ArrowError<EX,__<__<ErrorArrow.µ,EX>,A>> {

    @Override
    public default <B> ErrorArrow<EX, A, EX, B> raise() {
        return ErrorArrow.errorArrow(a().arr(Either::newLeft));
    }

    @Override
    public default <B, C, D> ErrorArrow<EX, A, B, D> tryInUnless(__2<__<__<ErrorArrow.µ, EX>, A>, B, C> body, __2<__<__<ErrorArrow.µ, EX>, A>, T2<B, C>, D> onSuccess, __2<__<__<ErrorArrow.µ, EX>, A>, T2<B, EX>, D> onError) {
        class Util {
            <X,Y> Either<T2<X,EX>,T2<X,Y>> distr(T2<X,Either<EX,Y>> x) {
                return x._2().either(
                    (EX ex) -> Either.<T2<X,EX>,T2<X,Y>>newLeft(T2.of(x._1(), ex)),
                    (Y x2) -> Either.<T2<X,EX>,T2<X,Y>>newRight(T2.of(x._1(), x2))
                );
            }
        }
        Util util = new Util();
        return ErrorArrow.errorArrow(a().dot(
            a().fanin(
                ErrorArrow.narrow(onError).run(),
                ErrorArrow.narrow(onSuccess).run()
            ),
            a().dot(
                a().arr(util::<B,C>distr),
                a().fanout(a().identity(), ErrorArrow.narrow(body).run())
            )
        ));
    }
}
