package org.highj.data.bool;

import org.highj.data.eq.Eq;
import org.highj.typeclass0.group.MonoidLaw;
import org.highj.util.Gen;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleansTest {
    @Test
    public void andGroup() {
        assertThat(Booleans.andMonoid.identity()).isTrue();
        for (boolean a : Arrays.asList(true, false)) {
            for (boolean b : Arrays.asList(true, false)) {
                assertThat(Booleans.andMonoid.apply(a, b)).isEqualTo(a && b);
            }
        }
        new MonoidLaw<>(Booleans.andMonoid, Gen.boolGen, Eq.fromEquals()).testAll();
    }

    @Test
    public void orGroup() {
        assertThat(Booleans.orMonoid.identity()).isFalse();
        for (boolean a : Arrays.asList(true, false)) {
            for (boolean b : Arrays.asList(true, false)) {
                assertThat(Booleans.orMonoid.apply(a, b)).isEqualTo(a || b);
            }
        }
        new MonoidLaw<>(Booleans.orMonoid, Gen.boolGen, Eq.fromEquals()).testAll();
    }

    @Test
    public void xorMonoid() {
        assertThat(Booleans.xorMonoid.identity()).isFalse();
        for (boolean a : Arrays.asList(true, false)) {
            for (boolean b : Arrays.asList(true, false)) {
                assertThat(Booleans.xorMonoid.apply(a, b)).isEqualTo(a ^ b);
            }
        }
        new MonoidLaw<>(Booleans.xorMonoid, Gen.boolGen, Eq.fromEquals()).testAll();
    }
}