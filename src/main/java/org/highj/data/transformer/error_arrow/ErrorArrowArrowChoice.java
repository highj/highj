package org.highj.data.transformer.error_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.transformer.ErrorArrow;
import org.highj.typeclass2.arrow.ArrowChoice;

import static org.highj.Hkt.asErrorArrow;

/**
 *
 * @author clintonselke
 */
public interface ErrorArrowArrowChoice<EX,A> extends ErrorArrowArrow<EX,A>, ArrowChoice<__<__<ErrorArrow.µ,EX>,A>> {

    @Override
    default <B, C, D> ErrorArrow<EX, A, Either<B, D>, Either<C, D>> left(__2<__<__<ErrorArrow.µ, EX>, A>, B, C> arrow) {
        class Util {
            <X,Y,Z> Either<X,Either<Y,Z>> assocsum(Either<Either<X,Y>,Z> x) {
                return x.either(
                    (Either<X,Y> x2) -> x2.either(
                        (X x3) -> Either.<X,Either<Y,Z>>Left(x3),
                        (Y x3) -> Either.<X,Either<Y,Z>>Right(Either.<Y,Z>Left(x3))
                    ),
                    (Z x2) -> Either.<X,Either<Y,Z>>Right(Either.<Y,Z>Right(x2))
                );
            }
        }
        Util util = new Util();
        return ErrorArrow.errorArrow(
            a().dot(
                a().arr(util::assocsum),
                a().left(asErrorArrow(arrow).run())
            )
        );
    }
}
