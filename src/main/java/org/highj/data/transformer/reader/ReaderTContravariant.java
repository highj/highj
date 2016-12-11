package org.highj.data.transformer.reader;

import org.derive4j.hkt.__;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.Hkt.asReaderT;

public interface ReaderTContravariant<R, M> extends Contravariant<__<__<ReaderT.µ, R>, M>> {

    Contravariant<M> get();

    @Override
    default <A, B> ReaderT<R, M, A> contramap(Function<A, B> fn, __<__<__<ReaderT.µ, R>, M>, B> nestedB) {
        return r -> get().contramap(fn, asReaderT(nestedB).run(r));
    }
}

