/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.stream_arrow;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StreamArrow;
import org.highj.typeclass2.arrow.Category;

/**
 *
 * @author clintonselke
 */
public interface StreamArrowCategory<A> extends StreamArrowSemigroupoid<A>, Category<__<StreamArrow.µ,A>> {
    
    @Override
    public Category<A> a();

    @Override
    public default <B> StreamArrow<A, B, B> identity() {
        return StreamArrow.streamArrow(a().identity());
    }
}
