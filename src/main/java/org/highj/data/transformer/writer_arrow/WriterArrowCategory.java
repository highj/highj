/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.writer_arrow;

import org.derive4j.hkt.__;
import org.highj.data.transformer.WriterArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass2.arrow.Category;

/**
 *
 * @author clintonselke
 */
public interface WriterArrowCategory<W,A> extends WriterArrowSemigroupoid<W,A>, Category<__<__<WriterArrow.µ,W>,A>> {
    
    @Override
    public Monoid<W> w();
    
    @Override
    public default <B> WriterArrow<W, A, B, B> identity() {
        return WriterArrow.writerArrow(a().arr((B x) -> T2.of(x, w().identity())));
    }
}
