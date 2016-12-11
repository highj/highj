/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.writer_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.WriterArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.Semigroupoid;

import static org.highj.Hkt.asWriterArrow;

/**
 *
 * @author clintonselke
 */
public interface WriterArrowSemigroupoid<W,A> extends Semigroupoid<__<__<WriterArrow.µ,W>,A>> {
    
    public Semigroup<W> w();
    
    public Arrow<A> a();

    @Override
    public default <B, C, D> WriterArrow<W, A, B, D> dot(__2<__<__<WriterArrow.µ, W>, A>, C, D> cd, __2<__<__<WriterArrow.µ, W>, A>, B, C> bc) {
        return WriterArrow.writerArrow(
            a().dot(
                a().arr((T2<T2<D,W>,W> x) -> T2.of(x._1()._1(), w().apply(x._2(), x._1()._2()))),
                a().dot(
                    a().first(asWriterArrow(cd).run()),
                    asWriterArrow(bc).run()
                )
            )
        );
    }
}
