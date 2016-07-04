package org.highj.data.compare;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.typeclass0.compare.Ordering.*;

public class OrderingTest {
    @Test
    public void testCmpResult() throws Exception {
        assertThat(LT.cmpResult()).isEqualTo(-1);
        assertThat(EQ.cmpResult()).isEqualTo(0);
        assertThat(GT.cmpResult()).isEqualTo(1);
    }

    @Test
    public void testMonoid() throws Exception {
        assertThat(group.identity()).isEqualTo(EQ);
        assertThat(group.apply(EQ, EQ)).isEqualTo(EQ);
        assertThat(group.apply(LT, EQ)).isEqualTo(LT);
        assertThat(group.apply(LT, GT)).isEqualTo(LT);
        assertThat(group.apply(GT, EQ)).isEqualTo(GT);
        assertThat(group.apply(EQ, GT)).isEqualTo(GT);
        assertThat(group.fold(EQ, EQ, GT, LT, EQ)).isEqualTo(GT);
    }

    @Test
    public void testFromInt() {
        assertThat(fromInt("a".compareTo("b"))).isEqualTo(LT);
        assertThat(fromInt("b".compareTo("a"))).isEqualTo(GT);
        assertThat(fromInt("a".compareTo("a"))).isEqualTo(EQ);
    }

    @Test
    public void testCompare() {
        assertThat(compare("a", "b")).isEqualTo(LT);
        assertThat(compare("b", "a")).isEqualTo(GT);
        assertThat(compare("a", "a")).isEqualTo(EQ);

        assertThat(compare(1, 2)).isEqualTo(LT);
        assertThat(compare(3, 0)).isEqualTo(GT);
        assertThat(compare(-5, -5)).isEqualTo(EQ);

        assertThat(compare(false, true)).isEqualTo(LT);
        assertThat(compare(true, false)).isEqualTo(GT);
        assertThat(compare(false, false)).isEqualTo(EQ);
    }

    @Test
    public void testAndThen() {
        assertThat(compare("a", "b").andThen(compare("b","a"))).isEqualTo(LT);
        assertThat(compare("b", "b").andThen(compare("b","a"))).isEqualTo(GT);
        assertThat(compare("a", "a").andThen(compare("b","b"))).isEqualTo(EQ);
    }
}
