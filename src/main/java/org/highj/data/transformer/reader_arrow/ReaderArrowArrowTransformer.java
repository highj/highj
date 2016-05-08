/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.reader_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.ReaderArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowTransformer;

/**
 *
 * @author clintonselke
 */
public interface ReaderArrowArrowTransformer<R,A> extends ReaderArrowArrow<R,A>, ArrowTransformer<__<ReaderArrow.µ,R>,A> {

    @Override
    public default <B, C> ReaderArrow<R, A, B, C> lift(__2<A, B, C> arrow) {
        return ReaderArrow.readerArrow(
            a().dot(
                arrow,
                a().arr(T2::_1)
            )
        );
    }
    
}
