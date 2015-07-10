/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.data.transformer.stream_arrow;

import java.util.function.Function;
import org.highj.__;
import org.highj.___;
import org.highj.data.collection.Stream;
import org.highj.data.transformer.StreamArrow;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;

/**
 *
 * @author clintonselke
 */
public interface StreamArrowArrow<A> extends StreamArrowCategory<A>, Arrow<___.µ<StreamArrow.µ,A>> {
    
    @Override
    public Arrow<A> a();
    
    @Override
    public default <B, C> StreamArrow<A, B, C> arr(Function<B, C> fn) {
        return StreamArrow.streamArrow(
            a().arr((Stream<B> x) -> x.map(fn))
        );
    }

    @Override
    public default <B, C, D> StreamArrow<A, T2<B, D>, T2<C, D>> first(__<___.µ<StreamArrow.µ, A>, B, C> x) {
        return StreamArrow.streamArrow(
            a().dot(
                a().arr((T2<Stream<C>,Stream<D>> x2) -> Stream.zip(x2._1(), x2._2())),
                a().dot(
                    a().first(StreamArrow.narrow(x).unstreamArrow()),
                    a().arr((Stream<T2<B,D>> x2) -> Stream.unzip(x2))
                )
            )
        );
    }
}
