/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error_arrow;

import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.collection.Either;
import org.highj.data.transformer.ErrorArrow;
import org.highj.typeclass2.arrow.ArrowChoice;

/**
 *
 * @author clintonselke
 */
public interface ErrorArrowArrowChoice<EX,A> extends ErrorArrowArrow<EX,A>, ArrowChoice<___.µ<____.µ<ErrorArrow.µ,EX>,A>> {

    @Override
    public default <B, C, D> ErrorArrow<EX, A, Either<B, D>, Either<C, D>> left(__<___.µ<____.µ<ErrorArrow.µ, EX>, A>, B, C> arrow) {
        class Util {
            <X,Y,Z> Either<X,Either<Y,Z>> assocsum(Either<Either<X,Y>,Z> x) {
                return x.either(
                    (Either<X,Y> x2) -> x2.either(
                        (X x3) -> Either.<X,Either<Y,Z>>newLeft(x3),
                        (Y x3) -> Either.<X,Either<Y,Z>>newRight(Either.<Y,Z>newLeft(x3))
                    ),
                    (Z x2) -> Either.<X,Either<Y,Z>>newRight(Either.<Y,Z>newRight(x2))
                );
            }
        }
        Util util = new Util();
        return ErrorArrow.errorArrow(
            a().dot(
                a().arr(util::assocsum),
                a().left(ErrorArrow.narrow(arrow).run())
            )
        );
    }
}
