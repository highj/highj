/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.stream_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.transformer.StreamArrow;
import org.highj.typeclass2.arrow.Semigroupoid;

import static org.highj.Hkt.asStreamArrow;

/**
 *
 * @author clintonselke
 */
public interface StreamArrowSemigroupoid<A> extends Semigroupoid<__<StreamArrow.µ,A>> {
    
    public Semigroupoid<A> a();

    @Override
    public default <B, C, D> StreamArrow<A, B, D> dot(__2<__<StreamArrow.µ, A>, C, D> cd, __2<__<StreamArrow.µ, A>, B, C> bc) {
        return StreamArrow.streamArrow(
            a().dot(
                asStreamArrow(cd).unstreamArrow(),
                asStreamArrow(bc).unstreamArrow()
            )
        );
    }
}
