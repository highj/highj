/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.stream_arrow;

import org.highj.__;
import org.highj.data.collection.Stream;
import org.highj.data.transformer.StreamArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.ArrowLazy;
import org.highj.typeclass2.arrow.ArrowTransformer;

/**
 *
 * @author clintonselke
 */
public interface StreamArrowArrowTransformer<A> extends StreamArrowArrow<A>, ArrowTransformer<StreamArrow.µ,A> {
    
    @Override
    public ArrowLazy<A> a();
    
    @Override
    public default <B, C> StreamArrow<A, B, C> lift(__<A, B, C> x) {
        class Util {
            __<A,Stream<B>,Stream<C>> helper(__<A,B,C> x2) {
                return a().dot(
                    a().arr((T2<C,Stream<C>> x3) -> Stream.newStream(x3._1(), x3._2())),
                    a().dot(
                        a().split(x, a().lazy(() -> helper(x2))),
                        a().arr((Stream<B> x3) -> T2.of(x3.head(), x3.tail()))
                    )
                );
            }
        }
        final Util util = new Util();
        return StreamArrow.streamArrow(util.helper(x));
    }
}
