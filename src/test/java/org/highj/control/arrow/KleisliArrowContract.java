package org.highj.control.arrow;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.data.Maybe;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.ArrowContract;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class KleisliArrowContract implements ArrowContract<__<Kleisli.µ, Maybe.µ>> {

    @Override
    public Arrow<__<Kleisli.µ, Maybe.µ>> subject() {
        return Kleisli.arrow(Maybe.monad);
    }

    @Override
    public <B, C> boolean areEqual(__2<__<Kleisli.µ, Maybe.µ>, B, C> one, __2<__<Kleisli.µ, Maybe.µ>, B, C> two, B b) {
        Kleisli<Maybe.µ, B, C> kleisliOne = Hkt.asKleisli(one);
        Kleisli<Maybe.µ, B, C> kleisliTwo = Hkt.asKleisli(two);
        Maybe<C> maybeOne = Hkt.asMaybe(kleisliOne.apply(b));
        Maybe<C> maybeTwo = Hkt.asMaybe(kleisliTwo.apply(b));
        return maybeOne.equals(maybeTwo);
    }
}
