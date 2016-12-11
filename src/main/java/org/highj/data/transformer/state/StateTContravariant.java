package org.highj.data.transformer.state;

import org.derive4j.hkt.__;
import org.highj.data.transformer.StateT;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.Hkt.asStateT;

public interface StateTContravariant<S, M> extends Contravariant<__<__<StateT.µ, S>, M>> {

    Contravariant<M> get();

    @Override
    default <A, B> StateT<S, M, A> contramap(Function<A, B> fn, __<__<__<StateT.µ, S>, M>, B> nestedB) {
        return s -> get().contramap(t2 -> t2.map_1(fn), asStateT(nestedB).run(s));
    }
}
