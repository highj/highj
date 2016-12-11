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
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.Semigroupoid;

import static org.highj.Hkt.asReaderArrow;

/**
 *
 * @author clintonselke
 */
public interface ReaderArrowSemigroupoid<R,A> extends Semigroupoid<__<__<ReaderArrow.µ,R>,A>> {
    
    public Arrow<A> a();

    @Override
    public default <B, C, D> ReaderArrow<R, A, B, D> dot(__2<__<__<ReaderArrow.µ, R>, A>, C, D> cd, __2<__<__<ReaderArrow.µ, R>, A>, B, C> bc) {
        return ReaderArrow.readerArrow(
            a().dot(
                asReaderArrow(cd).run(),
                a().fanout(
                    asReaderArrow(bc).run(),
                    a().arr(T2::_2)
                )
            )
        );
    }
}
