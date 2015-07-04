/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.error_arrow;

import org.highj.___;
import org.highj.____;
import org.highj.data.collection.Either;
import org.highj.data.transformer.ErrorArrow;
import org.highj.typeclass2.arrow.Category;

/**
 *
 * @author clintonselke
 */
public interface ErrorArrowCategory<EX,A> extends ErrorArrowSemigroupoid<EX,A>, Category<___.µ<____.µ<ErrorArrow.µ,EX>,A>> {

    @Override
    public default <B> ErrorArrow<EX, A, B, B> identity() {
        return ErrorArrow.errorArrow(a().arr(Either::newRight));
    }
}
