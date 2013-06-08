package org.highj.data;

import org.highj._;
import org.highj.data.functions.F1;
import org.highj.data.structural.Dual;
import org.highj.typeclass2.arrow.Category;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class DualTest {

    @Test
    public void categoryIdentityTest() throws Exception {
        Category<_<Dual.µ,F1.µ>> category = Dual.category(F1.arrow);
        Dual<F1.µ, Integer, Integer> idDual =  Dual.narrow(category.<Integer>identity());
        F1<Integer, Integer> id = F1.narrow(idDual.get());
        assertEquals(Integer.valueOf(42), id.apply(42));
    }

    @Test
    public void categoryDotTest() throws Exception {
        Category<_<Dual.µ,F1.µ>> category = Dual.category(F1.arrow);

        Dual<F1.µ, Integer, Integer> squareDual = new Dual<>((F1<Integer, Integer>) x -> x*x);
        Dual<F1.µ, Integer, Integer> negateDual = new Dual<>((F1<Integer, Integer>) x -> -x);
        Dual<F1.µ, Integer, Integer> squareNegateDual = Dual.narrow(category.dot(squareDual, negateDual));

        F1<Integer, Integer> squareNegate = F1.narrow(squareNegateDual.get());
        // executes square first, and then negate
        // in contrast to category.dot(sqr, negate), which negates first, and squares then
        assertEquals(Integer.valueOf(-16), squareNegate.apply(4));
    }
}
