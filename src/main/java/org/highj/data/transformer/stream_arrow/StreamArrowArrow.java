/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.stream_arrow;

import java.util.function.Function;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Stream;
import org.highj.data.transformer.StreamArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;

import static org.highj.Hkt.asStreamArrow;

/**
 *
 * @author clintonselke
 */
public interface StreamArrowArrow<A> extends StreamArrowCategory<A>, Arrow<__<StreamArrow.µ,A>> {
    
    @Override
    public Arrow<A> a();
    
    @Override
    public default <B, C> StreamArrow<A, B, C> arr(Function<B, C> fn) {
        return StreamArrow.streamArrow(
            a().arr((Stream<B> x) -> x.map(fn))
        );
    }

    @Override
    public default <B, C, D> StreamArrow<A, T2<B, D>, T2<C, D>> first(__2<__<StreamArrow.µ, A>, B, C> x) {
        return StreamArrow.streamArrow(
            a().dot(
                a().arr((T2<Stream<C>,Stream<D>> x2) -> Stream.zip(x2._1(), x2._2())),
                a().dot(
                    a().first(asStreamArrow(x).unstreamArrow()),
                    a().arr((Stream<T2<B,D>> x2) -> Stream.unzip(x2))
                )
            )
        );
    }
}
