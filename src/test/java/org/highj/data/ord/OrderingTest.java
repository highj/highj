package org.highj.data.ord;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.data.ord.Ordering.*;

public class OrderingTest {
    @Test
    public void testCmpResult() {
        assertThat(LT.cmpResult()).isEqualTo(-1);
        assertThat(EQ.cmpResult()).isEqualTo(0);
        assertThat(GT.cmpResult()).isEqualTo(1);
    }

    @Test
    public void testGroup() {
        assertThat(group.identity()).isEqualTo(EQ);
        assertThat(group.inverse(GT)).isEqualTo(LT);
        assertThat(group.inverse(EQ)).isEqualTo(EQ);
        assertThat(group.inverse(LT)).isEqualTo(GT);
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
        assertThat(compare(false, true)).isEqualTo(LT);
        assertThat(compare(true, false)).isEqualTo(GT);
        assertThat(compare(false, false)).isEqualTo(EQ);

        assertThat(compare('a', 'b')).isEqualTo(LT);
        assertThat(compare('b', 'a')).isEqualTo(GT);
        assertThat(compare('a', 'a')).isEqualTo(EQ);

        assertThat(compare((byte) 1, (byte) 2)).isEqualTo(LT);
        assertThat(compare((byte) 3, (byte) 0)).isEqualTo(GT);
        assertThat(compare((byte) -5, (byte) -5)).isEqualTo(EQ);

        assertThat(compare((short) 1, (short) 2)).isEqualTo(LT);
        assertThat(compare((short) 3, (short) 0)).isEqualTo(GT);
        assertThat(compare((short) -5, (short) -5)).isEqualTo(EQ);

        assertThat(compare(1, 2)).isEqualTo(LT);
        assertThat(compare(3, 0)).isEqualTo(GT);
        assertThat(compare(-5, -5)).isEqualTo(EQ);

        assertThat(compare(1L, 2L)).isEqualTo(LT);
        assertThat(compare(3L, 0L)).isEqualTo(GT);
        assertThat(compare(-5L, -5L)).isEqualTo(EQ);

        assertThat(compare(1.0, 2.0)).isEqualTo(LT);
        assertThat(compare(3.0, 0.0)).isEqualTo(GT);
        assertThat(compare(-5.0, -5.0)).isEqualTo(EQ);

        assertThat(compare(1.0F, 2.0F)).isEqualTo(LT);
        assertThat(compare(3.0F, 0.0F)).isEqualTo(GT);
        assertThat(compare(-5.0F, -5.0F)).isEqualTo(EQ);

        assertThat(compare("a", "b")).isEqualTo(LT);
        assertThat(compare("b", "a")).isEqualTo(GT);
        assertThat(compare("a", "a")).isEqualTo(EQ);
    }

    @Test
    public void testAndThen() {
        assertThat(LT.andThen(GT)).isEqualTo(LT);
        assertThat(GT.andThen(EQ)).isEqualTo(GT);
        assertThat(EQ.andThen(LT)).isEqualTo(LT);
        assertThat(EQ.andThen(GT)).isEqualTo(GT);
        assertThat(EQ.andThen(EQ)).isEqualTo(EQ);

        assertThat(LT.andThen(false, true)).isEqualTo(LT);
        assertThat(GT.andThen(false, false)).isEqualTo(GT);
        assertThat(EQ.andThen(false, true)).isEqualTo(LT);
        assertThat(EQ.andThen(true, false)).isEqualTo(GT);
        assertThat(EQ.andThen(true, true)).isEqualTo(EQ);

        assertThat(LT.andThen('a', 'b')).isEqualTo(LT);
        assertThat(GT.andThen('a', 'a')).isEqualTo(GT);
        assertThat(EQ.andThen('a', 'b')).isEqualTo(LT);
        assertThat(EQ.andThen('b', 'a')).isEqualTo(GT);
        assertThat(EQ.andThen('a', 'a')).isEqualTo(EQ);

        assertThat(LT.andThen((byte) 1, (byte) 2)).isEqualTo(LT);
        assertThat(GT.andThen((byte) 1, (byte) 1)).isEqualTo(GT);
        assertThat(EQ.andThen((byte) 1, (byte) 2)).isEqualTo(LT);
        assertThat(EQ.andThen((byte) 2, (byte) 1)).isEqualTo(GT);
        assertThat(EQ.andThen((byte) 1, (byte) 1)).isEqualTo(EQ);

        assertThat(LT.andThen((short) 1, (short) 2)).isEqualTo(LT);
        assertThat(GT.andThen((short) 1, (short) 1)).isEqualTo(GT);
        assertThat(EQ.andThen((short) 1, (short) 2)).isEqualTo(LT);
        assertThat(EQ.andThen((short) 2, (short) 1)).isEqualTo(GT);
        assertThat(EQ.andThen((short) 1, (short) 1)).isEqualTo(EQ);

        assertThat(LT.andThen(1, 2)).isEqualTo(LT);
        assertThat(GT.andThen(1, 1)).isEqualTo(GT);
        assertThat(EQ.andThen(1, 2)).isEqualTo(LT);
        assertThat(EQ.andThen(2, 1)).isEqualTo(GT);
        assertThat(EQ.andThen(1, 1)).isEqualTo(EQ);

        assertThat(LT.andThen(1L, 2L)).isEqualTo(LT);
        assertThat(GT.andThen(1L, 1L)).isEqualTo(GT);
        assertThat(EQ.andThen(1L, 2L)).isEqualTo(LT);
        assertThat(EQ.andThen(2L, 1L)).isEqualTo(GT);
        assertThat(EQ.andThen(1L, 1L)).isEqualTo(EQ);

        assertThat(LT.andThen(1.0, 2.0)).isEqualTo(LT);
        assertThat(GT.andThen(1.0, 1.0)).isEqualTo(GT);
        assertThat(EQ.andThen(1.0, 2.0)).isEqualTo(LT);
        assertThat(EQ.andThen(2.0, 1.0)).isEqualTo(GT);
        assertThat(EQ.andThen(1.0, 1.0)).isEqualTo(EQ);

        assertThat(LT.andThen(1.0F, 2.0F)).isEqualTo(LT);
        assertThat(GT.andThen(1.0F, 1.0F)).isEqualTo(GT);
        assertThat(EQ.andThen(1.0F, 2.0F)).isEqualTo(LT);
        assertThat(EQ.andThen(2.0F, 1.0F)).isEqualTo(GT);
        assertThat(EQ.andThen(1.0F, 1.0F)).isEqualTo(EQ);

        assertThat(LT.andThen("a", "b")).isEqualTo(LT);
        assertThat(GT.andThen("a", "a")).isEqualTo(GT);
        assertThat(EQ.andThen("a", "b")).isEqualTo(LT);
        assertThat(EQ.andThen("b", "a")).isEqualTo(GT);
        assertThat(EQ.andThen("a", "a")).isEqualTo(EQ);

    }

    @Test
    public void testInverse() {
        assertThat(GT.inverse()).isEqualTo(LT);
        assertThat(EQ.inverse()).isEqualTo(EQ);
        assertThat(LT.inverse()).isEqualTo(GT);
    }

    @Test
    public void testCaseLT() {
        assertThat(LT.caseLT(() -> 10).caseEQ(() -> 20).caseGT(() -> 30)).isEqualTo(10);
        assertThat(EQ.caseLT(() -> 10).caseEQ(() -> 20).caseGT(() -> 30)).isEqualTo(20);
        assertThat(GT.caseLT(() -> 10).caseEQ(() -> 20).caseGT(() -> 30)).isEqualTo(30);

        //ensure laziness
        Supplier<Integer> ex = () -> {
            throw new RuntimeException();
        };
        assertThat(LT.caseLT(() -> 10).caseEQ(ex).caseGT(ex)).isEqualTo(10);
        assertThat(EQ.caseLT(ex).caseEQ(() -> 10).caseGT(ex)).isEqualTo(10);
        assertThat(GT.caseLT(ex).caseEQ(ex).caseGT(() -> 10)).isEqualTo(10);
    }
}
