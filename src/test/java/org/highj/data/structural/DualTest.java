package org.highj.data.structural;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.derive4j.hkt.__3;
import org.highj.Hkt;
import org.highj.data.num.BigIntegers;
import org.highj.data.num.Integers;
import org.highj.data.structural.dual.DualGroup;
import org.highj.data.structural.dual.DualMonoid;
import org.highj.data.structural.dual.DualSemigroup;
import org.highj.data.tuple.T2;
import org.highj.function.F1;
import org.highj.function.Strings;
import org.highj.typeclass0.group.Group;
import org.highj.typeclass0.group.Monoid;
import org.highj.typeclass0.group.Semigroup;
import org.highj.typeclass2.arrow.Category;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asDual;
import static org.highj.Hkt.asF1;


public class DualTest {

    @Test
    public void categoryIdentity() {
        Category<__<Dual.µ, F1.µ>> category = Dual.category(F1.arrow);
        Dual<F1.µ, Integer, Integer> idDual = asDual(category.identity());
        F1<Integer, Integer> id = asF1(idDual.get());
        assertThat(id.apply(42)).isEqualTo(Integer.valueOf(42));
    }

    @Test
    public void categoryDot() {
        Category<__<Dual.µ, F1.µ>> category = Dual.category(F1.arrow);

        Dual<F1.µ, Integer, Integer> squareDual = new Dual<>((F1<Integer, Integer>) x -> x * x);
        Dual<F1.µ, Integer, Integer> negateDual = new Dual<>((F1<Integer, Integer>) x -> -x);
        Dual<F1.µ, Integer, Integer> squareNegateDual = asDual(category.dot(squareDual, negateDual));

        F1<Integer, Integer> squareNegate = asF1(squareNegateDual.get());
        // executes square first, and then negate
        // in contrast to category.apply(sqr, negate), which negates first, and squares then
        assertThat(squareNegate.apply(4)).isEqualTo(Integer.valueOf(-16));
    }

    @Test
    public void semigroup() {
        __3<Dual.µ, T2.µ, String, Integer> d1 = Dual.of(T2.of(42, "foo"));
        __3<Dual.µ, T2.µ, String, Integer> d2 = Dual.of(T2.of(23, "bar"));

        Semigroup<__2<T2.µ, Integer, String>> t2Semigroup = (x, y) -> {
            T2<Integer, String> tx = Hkt.asT2(x);
            T2<Integer, String> ty = Hkt.asT2(y);
            return T2.of(tx._1() + ty._1(), tx._2() + ty._2());
        };
        DualSemigroup<T2.µ, String, Integer> semigroup = Dual.semigroup(t2Semigroup);

        assertThat(semigroup.apply(d1, d2)).isEqualTo(Dual.of(T2.of(65, "barfoo")));
    }

    @Test
    public void monoid() {
        __3<Dual.µ, T2.µ, String, Integer> d1 = Dual.of(T2.of(42, "foo"));
        __3<Dual.µ, T2.µ, String, Integer> d2 = Dual.of(T2.of(23, "bar"));

        Monoid<__2<T2.µ, Integer, String>> t2Monoid = T2.monoid(Integers.additiveGroup, Strings.monoid);
        DualMonoid<T2.µ, String, Integer> monoid = Dual.monoid(t2Monoid);

        assertThat(monoid.fold(d1, d2, d2)).isEqualTo(Dual.of(T2.of(88, "barbarfoo")));
    }

    @Test
    public void group() {
        __3<Dual.µ, T2.µ, BigInteger, Integer> d1 = Dual.of(T2.of(42, BigInteger.TEN));

        Group<__2<T2.µ, Integer, BigInteger>> t2Group = T2.group(Integers.additiveGroup, BigIntegers.additiveGroup);
        DualGroup<T2.µ, BigInteger, Integer> group = Dual.group(t2Group);

        assertThat(group.inverse(d1)).isEqualTo(Dual.of(T2.of(-42, BigInteger.valueOf(-10))));
    }

}
