package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.highj.data.coroutine.ProducerT;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
import org.highj.do_.Do_;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.highj.Hkt.asProducerT;

public class GeneratorTTest {

    // Example: Towers of Hanoi
    // hanoi 0 _ _ _ = return ()
    // hanoi n from to other = do
    //     hanoi (n-1) from other to
    //     yield (from, to)
    //     hanoi (n-1) other to from
    private static <A> ProducerT<T2<A, A>, T1.µ, T0> hanoi(int n, A from, A to, A other) {
        if (n == 0) {
            return ProducerT.done(T0.of());
        }
        return ProducerT.suspend(() -> asProducerT(
            Do_.<__<__<ProducerT.µ, T2<A, A>>, T1.µ>>do_()
                .__(hanoi(n - 1, from, other, to))
                .__(ProducerT.yield(T2.of(from, to)))
                .__(hanoi(n - 1, other, to, from))
                .runNoResultNoTailRec(ProducerT.monad())
            )
        );
    }

    @Test
    public void testHanoi() {
        Iterator<T2<Character, Character>> iterator = ProducerT.toIterator(hanoi(3, 'A', 'B', 'C'));
        char[][] expectedResult = {{'A', 'B'}, {'A', 'C'}, {'B', 'C'}, {'A', 'B'}, {'C', 'A'}, {'C', 'B'}, {'A', 'B'}};
        int i = 0;
        while (iterator.hasNext()) {
            T2<Character, Character> x = iterator.next();
            assertThat(expectedResult[i][0]).isEqualTo(x._1().charValue());
            assertThat(expectedResult[i][1]).isEqualTo(x._2().charValue());
            ++i;
        }
    }
}
