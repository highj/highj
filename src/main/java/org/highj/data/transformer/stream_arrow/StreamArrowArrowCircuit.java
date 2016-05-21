/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.stream_arrow;

import org.derive4j.hkt.__;
import org.highj.data.Stream;
import org.highj.data.transformer.StreamArrow;
import org.highj.typeclass2.arrow.ArrowCircuit;

/**
 *
 * @author clintonselke
 */
public interface StreamArrowArrowCircuit<A> extends StreamArrowArrowLoop<A>, ArrowCircuit<__<StreamArrow.µ,A>> {

    @Override
    public default <B> StreamArrow<A, B, B> delay(B b) {
        return StreamArrow.streamArrow(a().arr((Stream<B> x) -> Stream.newStream(b, x)));
    }
}
