/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error_arrow;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.function.F1;
import org.highj.data.transformer.ErrorArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;

import static org.highj.Hkt.asErrorArrow;

/**
 *
 * @author clintonselke
 */
public interface ErrorArrowArrow<EX,A> extends ErrorArrowCategory<EX,A>, Arrow<__<__<ErrorArrow.µ,EX>,A>> {

    @Override
    public default <B, C> ErrorArrow<EX, A, B, C> arr(Function<B, C> fn) {
        return ErrorArrow.errorArrow(a().arr(F1.compose(Either::Right, fn)));
    }

    @Override
    public default <B, C, D> ErrorArrow<EX, A, T2<B, D>, T2<C, D>> first(__2<__<__<ErrorArrow.µ, EX>, A>, B, C> arrow) {
        class Util {
            <X,Y> Either<EX,T2<X,Y>> rstrength(T2<Either<EX,X>,Y> x) {
                return x._1().either(
                    Either::<EX,T2<X,Y>>Left,
                    (X x2) -> Either.<EX,T2<X,Y>>Right(T2.of(x2, x._2()))
                );
            }
        }
        Util util = new Util();
        return ErrorArrow.errorArrow(
            a().dot(
                a().arr(util::rstrength),
                a().first(asErrorArrow(arrow).run())
            )
        );
    }
}
