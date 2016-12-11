/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.reader_arrow;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.function.F1;
import org.highj.data.transformer.ReaderArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;

import static org.highj.Hkt.asReaderArrow;

/**
 *
 * @author clintonselke
 */
public interface ReaderArrowArrow<R,A> extends ReaderArrowCategory<R,A>, Arrow<__<__<ReaderArrow.µ,R>,A>> {

    @Override
    public default <B, C> ReaderArrow<R, A, B, C> arr(Function<B, C> fn) {
        return ReaderArrow.readerArrow(a().arr(F1.compose(fn, T2::_1)));
    }

    @Override
    public default <B, C, D> ReaderArrow<R, A, T2<B, D>, T2<C, D>> first(__2<__<__<ReaderArrow.µ, R>, A>, B, C> arrow) {
        return ReaderArrow.readerArrow(
            a().dot(
                a().first(asReaderArrow(arrow).run()),
                a().arr((T2<T2<B,D>,R> x) -> T2.of(T2.of(x._1()._1(), x._2()), x._1()._2()))
            )
        );
    }
}
