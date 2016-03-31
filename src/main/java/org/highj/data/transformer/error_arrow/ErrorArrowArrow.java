/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error_arrow;

import java.util.function.Function;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.collection.Either;
import org.highj.data.functions.F1;
import org.highj.data.transformer.ErrorArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;

/**
 *
 * @author clintonselke
 */
public interface ErrorArrowArrow<EX,A> extends ErrorArrowCategory<EX,A>, Arrow<_<_<ErrorArrow.µ,EX>,A>> {

    @Override
    public default <B, C> ErrorArrow<EX, A, B, C> arr(Function<B, C> fn) {
        return ErrorArrow.errorArrow(a().arr(F1.compose(Either::newRight, fn)));
    }

    @Override
    public default <B, C, D> ErrorArrow<EX, A, T2<B, D>, T2<C, D>> first(__<_<_<ErrorArrow.µ, EX>, A>, B, C> arrow) {
        class Util {
            <X,Y> Either<EX,T2<X,Y>> rstrength(T2<Either<EX,X>,Y> x) {
                return x._1().either(
                    Either::<EX,T2<X,Y>>newLeft,
                    (X x2) -> Either.<EX,T2<X,Y>>newRight(T2.of(x2, x._2()))
                );
            }
        }
        Util util = new Util();
        return ErrorArrow.errorArrow(
            a().dot(
                a().arr(util::rstrength),
                a().first(ErrorArrow.narrow(arrow).run())
            )
        );
    }
}
