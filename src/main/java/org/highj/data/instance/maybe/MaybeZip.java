package org.highj.data.instance.maybe;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.typeclass1.zip.Zip;

import java.util.function.BiFunction;

import static org.highj.Hkt.asMaybe;

public interface MaybeZip extends Zip<Maybe.µ> {

    @Override
    default <A, B, C> Maybe<C> zipWith(__<Maybe.µ, A> fa, __<Maybe.µ, B> fb, BiFunction<A, B, C> fn) {
        return Maybe.zipWith(asMaybe(fa), asMaybe(fb), fn);
    }

}
