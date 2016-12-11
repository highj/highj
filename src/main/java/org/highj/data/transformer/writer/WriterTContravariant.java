package org.highj.data.transformer.writer;

import org.derive4j.hkt.__;
import org.highj.data.transformer.WriterT;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.Hkt.asWriterT;

public interface WriterTContravariant<W, M> extends Contravariant<__<__<WriterT.µ, W>, M>> {

    Contravariant<M> get();

    @Override
    default <A, B> WriterT<W, M, A> contramap(Function<A, B> fn, __<__<__<WriterT.µ, W>, M>, B> nestedB) {
        return () -> get().contramap(t2 -> t2.map_1(fn), asWriterT(nestedB).run());
    }
}
