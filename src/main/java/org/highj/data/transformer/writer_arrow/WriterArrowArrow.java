/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.writer_arrow;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.function.F1;
import org.highj.data.transformer.WriterArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;

import static org.highj.Hkt.asWriterArrow;

/**
 *
 * @author clintonselke
 */
public interface WriterArrowArrow<W,A> extends WriterArrowCategory<W,A>, Arrow<__<__<WriterArrow.µ,W>,A>> {

    @Override
    public default <B, C> __2<__<__<WriterArrow.µ, W>, A>, B, C> arr(Function<B, C> fn) {
        return WriterArrow.writerArrow(
            a().arr(F1.compose((C x) -> T2.of(x, w().identity()), fn))
        );
    }

    @Override
    public default <B, C, D> WriterArrow<W, A, T2<B, D>, T2<C, D>> first(__2<__<__<WriterArrow.µ, W>, A>, B, C> arrow) {
        return WriterArrow.writerArrow(
            a().dot(
                a().arr((T2<T2<C,W>,D> x) -> T2.of(T2.of(x._1()._1(), x._2()), x._1()._2())),
                a().first(asWriterArrow(arrow).run())
            )
        );
    }
}
