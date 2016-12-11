package org.highj.data.transformer.rws;

import org.derive4j.hkt.__;
import org.highj.data.transformer.RWST;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.Hkt.asRWST;

public interface RWSTContravariant<R,W,S,M> extends Contravariant<__<__<__<__<RWST.µ,R>,W>,S>,M>> {

    Contravariant<M> getM();

    @Override
    default <A, B> RWST<R, W, S, M, A> contramap(Function<A, B> fn, __<__<__<__<__<RWST.µ, R>, W>, S>, M>, B> nestedB) {
        return (r,s) -> getM().contramap(t3 -> t3.map_1(fn), asRWST(nestedB).run(r, s));
    }
}
