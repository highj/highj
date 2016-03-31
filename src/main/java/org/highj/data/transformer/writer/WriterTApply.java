package org.highj.data.transformer.writer;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.WriterT;
import org.highj.data.tuple.T2;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass1.monad.Apply;

import java.util.function.Function;

/**
 * @author Clinton Selke
 */
public interface WriterTApply<W, M> extends WriterTFunctor<W, M>, Apply<_<_<WriterT.µ, W>, M>> {

    public Semigroup<W> wSemigroup();

    public Apply<M> get();

    @Override
    public default <A, B> WriterT<W, M, B> ap(_<_<_<WriterT.µ, W>, M>, Function<A, B>> fn, _<_<_<WriterT.µ, W>, M>, A> nestedA) {
        return () -> get().apply2(
                (T2<Function<A, B>, W> x1) -> (T2<A, W> x2) -> T2.of(x1._1().apply(x2._1()), wSemigroup().apply(x1._2(), x2._2())),
                WriterT.narrow(fn).run(),
                WriterT.narrow(nestedA).run()
        );
    }
}