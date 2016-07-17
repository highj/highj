package org.highj.data.bool;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleansTest {
    @Test
    public void andGroup() {
        assertThat(Booleans.andGroup.identity()).isTrue();
        assertThat(Booleans.andGroup.inverse(true)).isFalse();
        assertThat(Booleans.andGroup.inverse(false)).isTrue();
        for(boolean a : Arrays.asList(true, false)) {
            for(boolean b : Arrays.asList(true, false)) {
                assertThat(Booleans.andGroup.apply(a,b)).isEqualTo(a && b);
            }
        }
    }

    @Test
    public void orGroup() {
        assertThat(Booleans.orGroup.identity()).isFalse();
        assertThat(Booleans.orGroup.inverse(true)).isFalse();
        assertThat(Booleans.orGroup.inverse(false)).isTrue();
        for(boolean a : Arrays.asList(true, false)) {
            for(boolean b : Arrays.asList(true, false)) {
                assertThat(Booleans.orGroup.apply(a,b)).isEqualTo(a || b);
            }
        }
    }

    @Test
    public void xorMonoid() {
        assertThat(Booleans.xorMonoid.identity()).isFalse();
        for(boolean a : Arrays.asList(true, false)) {
            for(boolean b : Arrays.asList(true, false)) {
                assertThat(Booleans.xorMonoid.apply(a,b)).isEqualTo(a ^ b);
            }
        }
    }
}