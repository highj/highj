package org.highj.data.transformer;

import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.data.Stream;
import org.highj.data.transformer.stream_arrow.StreamArrowArrow;
import org.highj.data.transformer.stream_arrow.StreamArrowArrowCircuit;
import org.highj.data.transformer.stream_arrow.StreamArrowArrowLoop;
import org.highj.data.transformer.stream_arrow.StreamArrowCategory;
import org.highj.data.transformer.stream_arrow.StreamArrowSemigroupoid;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.ArrowLoop;
import org.highj.typeclass2.arrow.Category;
import org.highj.typeclass2.arrow.Semigroupoid;

/**
 *
 * @author clintonselke
 */
public class StreamArrow<A,B,C> implements __3<StreamArrow.µ,A,B,C> {
    public static class µ {}
    
    private final __2<A,Stream<B>,Stream<C>> _unstreamArrow;
    
    private StreamArrow(__2<A,Stream<B>,Stream<C>> unstreamArrow) {
        this._unstreamArrow = unstreamArrow;
    }
    
    public static <A,B,C> StreamArrow<A,B,C> streamArrow(__2<A,Stream<B>,Stream<C>> unstreamArrow) {
        return new StreamArrow<>(unstreamArrow);
    }

    public __2<A,Stream<B>,Stream<C>> unstreamArrow() {
        return _unstreamArrow;
    }
    
    public static <A,E,B,C> __2<A,T2<E,Stream<B>>,Stream<C>> run(ArrowLoop<A> aArrowLoop, StreamArrow<A,T2<E,B>,C> x) {
        Arrow<A> a = aArrowLoop;
        return a.dot(
            x.unstreamArrow(),
            a.arr((T2<E,Stream<B>> x2) -> x2._2().map((B x3) -> T2.of(x2._1(), x3)))
        );
    }
    
    public static <A> StreamArrowSemigroupoid<A> semigroupoid(Semigroupoid<A> a) {
        return () -> a;
    }
    
    public static <A> StreamArrowCategory<A> category(Category<A> a) {
        return () -> a;
    }
    
    public static <A> StreamArrowArrow<A> arrow(Arrow<A> a) {
        return () -> a;
    }
    
    public static <A> StreamArrowArrowLoop<A> arrowLoop(ArrowLoop<A> a) {
        return () -> a;
    }
    
    public static <A> StreamArrowArrowCircuit<A> arrowCircuit(ArrowLoop<A> a) {
        return () -> a;
    }
}
