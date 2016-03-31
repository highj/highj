/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.reader_arrow;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.____;
import org.highj.data.transformer.ReaderArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.Semigroupoid;

/**
 *
 * @author clintonselke
 */
public interface ReaderArrowSemigroupoid<R,A> extends Semigroupoid<_<_<ReaderArrow.µ,R>,A>> {
    
    public Arrow<A> a();

    @Override
    public default <B, C, D> ReaderArrow<R, A, B, D> dot(__<_<_<ReaderArrow.µ, R>, A>, C, D> cd, __<_<_<ReaderArrow.µ, R>, A>, B, C> bc) {
        return ReaderArrow.readerArrow(
            a().dot(
                ReaderArrow.narrow(cd).run(),
                a().fanout(
                    ReaderArrow.narrow(bc).run(),
                    a().arr(T2::_2)
                )
            )
        );
    }
}
