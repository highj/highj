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
import org.highj.typeclass2.arrow.Semigroupoid;

/**
 *
 * @author clintonselke
 */
public interface ErrorArrowSemigroupoid<EX,A> extends Semigroupoid<___.µ<____.µ<ErrorArrow.µ,EX>,A>> {
    
    public ArrowChoice<A> a();

    @Override
    public default <B, C, D> ErrorArrow<EX, A, B, D> dot(__<___.µ<____.µ<ErrorArrow.µ, EX>, A>, C, D> cd, __<___.µ<____.µ<ErrorArrow.µ, EX>, A>, B, C> bc) {
        return ErrorArrow.errorArrow(a().dot(
            a().fanin(
                a().arr(Either::newLeft),
                ErrorArrow.narrow(cd).run()
            ),
            ErrorArrow.narrow(bc).run()
        ));
    }
}
