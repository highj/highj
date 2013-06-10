package org.highj.data.structural.constant;

import org.highj._;
import org.highj.__;
import org.highj.data.structural.Const;
import org.highj.typeclass1.contravariant.Contravariant;

import java.util.function.Function;

import static org.highj.data.structural.Const.narrow;
import static org.highj.data.structural.Const.µ;

public interface ConstContravariant<S> extends Contravariant<__.µ<µ,S>> {

    @Override
    public default <A, B> Const<S, A> contramap(Function<A, B> fn,_<__.µ<µ, S>, B> nestedB) {
        //contramap _ (Const a) = Const a
        Const<S,B> constB = narrow(nestedB);
        return new Const<>(constB.get());
    }
}
