package org.highj.data.transformer.writer;

import org.derive4j.hkt.__;
import org.highj.data.transformer.WriterT;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

import static org.highj.Hkt.asWriterT;

/**
 * @author Clinton Selke
 */
public interface WriterTApply<W, M> extends WriterTFunctor<W, M>, Apply<__<__<WriterT.µ, W>, M>> {

    Semigroup<W> getW();

    Apply<M> getM();

    @Override
    default <A, B> WriterT<W, M, B> ap(__<__<__<WriterT.µ, W>, M>, Function<A, B>> fn, __<__<__<WriterT.µ, W>, M>, A> nestedA) {
        return () -> getM().apply2(
                (T2<Function<A, B>, W> x1) -> (T2<A, W> x2) -> T2.of(x1._1().apply(x2._1()), getW().apply(x1._2(), x2._2())),
                asWriterT(fn).run(),
                asWriterT(nestedA).run()
        );
    }
}