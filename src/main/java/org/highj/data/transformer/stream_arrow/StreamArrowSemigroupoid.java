/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.stream_arrow;

import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.StreamArrow;
import org.highj.typeclass2.arrow.Semigroupoid;

/**
 *
 * @author clintonselke
 */
public interface StreamArrowSemigroupoid<A> extends Semigroupoid<___.µ<StreamArrow.µ,A>> {
    
    public Semigroupoid<A> a();

    @Override
    public default <B, C, D> StreamArrow<A, B, D> dot(__<___.µ<StreamArrow.µ, A>, C, D> cd, __<___.µ<StreamArrow.µ, A>, B, C> bc) {
        return StreamArrow.streamArrow(
            a().dot(
                StreamArrow.narrow(cd).unstreamArrow(),
                StreamArrow.narrow(bc).unstreamArrow()
            )
        );
    }
}
