/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.stream_arrow;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Stream;
import org.highj.data.transformer.StreamArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowLoop;

import static org.highj.Hkt.asStreamArrow;

/**
 *
 * @author clintonselke
 */
public interface StreamArrowArrowLoop<A> extends StreamArrowArrow<A>, ArrowLoop<__<StreamArrow.µ,A>> {
    
    @Override
    public ArrowLoop<A> a();

    @Override
    public default <B, C, D> StreamArrow<A, B, C> loop(__2<__<StreamArrow.µ, A>, T2<B, D>, T2<C, D>> x) {
        return StreamArrow.streamArrow(
            a().loop(
                a().dot(
                    a().arr((Stream<T2<C,D>> x2) -> Stream.unzip(x2)),
                    a().dot(
                        asStreamArrow(x).unstreamArrow(),
                        a().arr((T2<Stream<B>,Stream<D>> x2) -> Stream.zip(x2._1(), x2._2()))
                    )
                )
            )
        );
    }
}
