package org.highj.data.eq.instances;

import org.derive4j.hkt.__;
import org.highj.Hkt;
import org.highj.data.eq.Eq1;
import org.highj.function.NF;
import org.highj.typeclass1.contravariant.Contravariant1;

public interface Eq1Contravariant1 extends Contravariant1<Eq1.µ> {

    @Override
    default <A, B> Eq1<A> contramap(NF<A, B> fn, __<Eq1.µ, B> nestedB) {
        return Hkt.asEq1(nestedB).contramap(fn);
    }
}
