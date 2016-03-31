/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.writer_arrow;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.transformer.WriterArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.Semigroupoid;

/**
 *
 * @author clintonselke
 */
public interface WriterArrowSemigroupoid<W,A> extends Semigroupoid<_<_<WriterArrow.µ,W>,A>> {
    
    public Semigroup<W> w();
    
    public Arrow<A> a();

    @Override
    public default <B, C, D> WriterArrow<W, A, B, D> dot(__<_<_<WriterArrow.µ, W>, A>, C, D> cd, __<_<_<WriterArrow.µ, W>, A>, B, C> bc) {
        return WriterArrow.writerArrow(
            a().dot(
                a().arr((T2<T2<D,W>,W> x) -> T2.of(x._1()._1(), w().apply(x._2(), x._1()._2()))),
                a().dot(
                    a().first(WriterArrow.narrow(cd).run()),
                    WriterArrow.narrow(bc).run()
                )
            )
        );
    }
}
