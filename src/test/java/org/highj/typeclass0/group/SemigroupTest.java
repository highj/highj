package org.highj.typeclass0.group;

import org.highj.data.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SemigroupTest {
    @Test
    public void testFirst() {
        Integer result = Semigroup.<Integer>first().fold(1, List.of(2, 3, 4, 5));
        assertThat(result).isEqualTo(Integer.valueOf(1));
    }

    @Test
    public void testLast() {
        Integer result = Semigroup.<Integer>last().fold(1, List.of(2, 3, 4, 5));
        assertThat(result).isEqualTo(Integer.valueOf(5));
    }

    @Test
    public void testDual() {
        Integer result = Semigroup.dual(Semigroup.<Integer>first()).fold(1, List.of(2, 3, 4, 5));
        assertThat(result).isEqualTo(Integer.valueOf(5));
    }

    @Test
    public void testMin() {
        Integer result = Semigroup.<Integer>min().fold(27, List.of(25, 11, 64, 57));
        assertThat(result).isEqualTo(Integer.valueOf(11));
    }

    @Test
    public void testMax() {
        Integer result = Semigroup.<Integer>max().fold(27, List.of(25, 11, 64, 57));
        assertThat(result).isEqualTo(Integer.valueOf(64));
    }
}
