/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.writer_arrow;

import org.highj.__;
import org.highj.____;
import org.highj.data.transformer.WriterArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowTransformer;

/**
 *
 * @author clintonselke
 */
public interface WriterArrowArrowTransformer<W,A> extends WriterArrowArrow<W,A>, ArrowTransformer<____.µ<WriterArrow.µ,W>,A> {

    @Override
    public default <B, C> WriterArrow<W, A, B, C> lift(__<A, B, C> arrow) {
        return WriterArrow.writerArrow(
            a().dot(
                a().arr((C x) -> T2.of(x, w().identity())),
                arrow
            )
        );
    }
}
