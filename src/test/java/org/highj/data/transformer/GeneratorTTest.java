package org.highj.data.transformer;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T1;
import org.highj.data.tuple.T2;
import org.highj.do_.Do;
import org.junit.Test;

import java.util.Iterator;

import static junit.framework.Assert.assertEquals;

public class GeneratorTTest {

    // Example: Towers of Hanoi
    // hanoi 0 _ _ _ = return ()
    // hanoi n from to other = do
    //     hanoi (n-1) from other to
    //     yield (from, to)
    //     hanoi (n-1) other to from
    private static <A> GeneratorT<T2<A,A>,T1.µ,T0> hanoi(int n, A from, A to, A other) {
        if (n == 0) {
            return GeneratorT.done(T0.of());
        }
        return GeneratorT.suspend(() ->
            GeneratorT.narrow(Do.do_(GeneratorT.<T2<A, A>, T1.µ>monad(), new Do.DoBlock<__<__<GeneratorT.µ, T2<A, A>>, T1.µ>, T0>() {
                @Override
                public <H> __<__<__<GeneratorT.µ, T2<A, A>>, T1.µ>, T0> run(Do.MContext<H, __<__<GeneratorT.µ, T2<A, A>>, T1.µ>> ctx) {
                    ctx.seq(hanoi(n-1, from, other, to));
                    ctx.seq(GeneratorT.emit(T2.of(from, to)));
                    ctx.seq(hanoi(n-1, other, to, from));
                    return ctx.done();
                }
            }))
        );
    }

    @Test
    public void testHanoi() {
        Iterator<T2<Character,Character>> iterator = GeneratorT.toIterator(hanoi(3, 'A', 'B', 'C'));
        char[][] expectedResult = {{'A','B'},{'A','C'},{'B','C'},{'A','B'},{'C','A'},{'C','B'},{'A','B'}};
        int i = 0;
        while (iterator.hasNext()) {
            T2<Character,Character> x = iterator.next();
            assertEquals(x._1().charValue(), expectedResult[i][0]);
            assertEquals(x._2().charValue(), expectedResult[i][1]);
            ++i;
        }
    }
}
