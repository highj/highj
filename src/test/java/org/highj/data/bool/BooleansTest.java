package org.highj.data.bool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleansTest {

    private static final boolean[] BOOLS = {true, false};

    @Test
    public void andMonoid() {
        assertThat(Booleans.andMonoid.identity()).isTrue();
        for (boolean a : BOOLS) {
            for (boolean b : BOOLS) {
                assertThat(Booleans.andMonoid.apply(a, b)).isEqualTo(a && b);
            }
        }
    }

    @Test
    public void orMonoid() {
        assertThat(Booleans.orMonoid.identity()).isFalse();
        for (boolean a : BOOLS) {
            for (boolean b : BOOLS) {
                assertThat(Booleans.orMonoid.apply(a, b)).isEqualTo(a || b);
            }
        }
    }

    @Test
    public void xorMonoid() {
        assertThat(Booleans.xorMonoid.identity()).isFalse();
        for (boolean a : BOOLS) {
            for (boolean b : BOOLS) {
                assertThat(Booleans.xorMonoid.apply(a, b)).isEqualTo(a ^ b);
            }
        }
    }
}