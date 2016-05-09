/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.reader_arrow;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ReaderArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Category;

/**
 *
 * @author clintonselke
 */
public interface ReaderArrowCategory<R,A> extends ReaderArrowSemigroupoid<R,A>, Category<__<__<ReaderArrow.µ,R>,A>> {

    @Override
    public default <B> ReaderArrow<R, A, B, B> identity() {
        return ReaderArrow.readerArrow(a().arr(T2::_1));
    }
}
