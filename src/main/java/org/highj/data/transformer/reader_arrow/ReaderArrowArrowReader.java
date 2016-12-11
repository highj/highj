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
import org.highj.typeclass2.arrow.ArrowReader;

import static org.highj.Hkt.asReaderArrow;

/**
 *
 * @author clintonselke
 */
public interface ReaderArrowArrowReader<R,A> extends ReaderArrowArrow<R,A>, ArrowReader<R,__<__<ReaderArrow.µ,R>,A>> {

    @Override
    public default <B> ReaderArrow<R, A, B, R> readState() {
        return ReaderArrow.readerArrow(a().arr(T2::_2));
    }

    @Override
    public default <B, C> ReaderArrow<R, A, T2<B, R>, C> newReader(__2<__<__<ReaderArrow.µ, R>, A>, B, C> a) {
        return ReaderArrow.readerArrow(
            a().dot(
                asReaderArrow(a).run(),
                a().arr((T2<T2<B,R>,R> x) -> x._1())
            )
        );
    }
}
