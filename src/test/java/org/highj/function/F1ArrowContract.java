package org.highj.function;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.derive4j.hkt.__2;
import org.highj.Hkt;
import org.highj.typeclass2.arrow.Arrow;
import org.highj.typeclass2.arrow.ArrowContract;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class F1ArrowContract implements ArrowContract<F1.µ> {

    @Override
    public Arrow<F1.µ> subject() {
        return F1.arrow;
    }

    @Override
    public <B, C> boolean areEqual(__2<F1.µ, B, C> one, __2<F1.µ, B, C> two, B b) {
        F1<B, C> f1One = Hkt.asF1(one);
        F1<B, C> f1Two = Hkt.asF1(two);
        return f1One.apply(b).equals(f1Two.apply(b));
    }
}
