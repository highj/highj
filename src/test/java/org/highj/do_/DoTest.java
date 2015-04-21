package org.highj.do_;

import org.highj.data.collection.Maybe;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.highj.data.collection.Either;
import org.highj.data.collection.List;

/**
 *
 * @author clintonselke
 */
public class DoTest {
    
    @Test
    @SuppressWarnings("UnusedAssignment")
    public void testNondeterminism() {
        List<String> results = List.narrow(
            Do.with(List.monadPlus).
                assign(Var.a, List.range(1, 1, 3)).
                assign(Var.b, List.range(1, 1, 3)).
                with(Var.a).and(Var.b).apply((Integer a, Integer b) -> "" + a + " x " + b + " = " + (a*b)).
                done()
        );
        assertEquals("1 x 1 = 1", results.head()); results = results.tail();
        assertEquals("1 x 2 = 2", results.head()); results = results.tail();
        assertEquals("1 x 3 = 3", results.head()); results = results.tail();
        assertEquals("2 x 1 = 2", results.head()); results = results.tail();
        assertEquals("2 x 2 = 4", results.head()); results = results.tail();
        assertEquals("2 x 3 = 6", results.head()); results = results.tail();
        assertEquals("3 x 1 = 3", results.head()); results = results.tail();
        assertEquals("3 x 2 = 6", results.head()); results = results.tail();
        assertEquals("3 x 3 = 9", results.head()); results = results.tail();
    }

    @Test
    public void testDoBlock() {
        Maybe<String> onetwo = Maybe.doBlock(doit -> doit.
                        assign(Var.a, Maybe.Just("one")).
                        assign(Var.b, Maybe.Just("two")).
                        with(Var.a).and(Var.b).apply((a, b) -> a + b)
        );
        assertEquals("onetwo", onetwo.get());

        Maybe<String> empty = Maybe.doBlock(doit -> doit.
                        assign(Var.a, Maybe.Just("one")).
                        assign(Var.b, Maybe.Nothing()).
                        with(Var.a).and(Var.b).apply((a, b) -> a + b)
        );
        assertTrue(empty.isNothing());
    }

    @Test
    public void testBind() {
        Either<String, Integer> handSum = Either.narrow(
                Do.with(Either.<String>monad()).
                        assign(Var.a, Either.<String, Integer>newRight(6)).
                        assign(Var.b, Either.<String, Integer>newRight(7)).
                        with(Var.a).and(Var.b).bind((Integer a, Integer b) -> {
                    int r = a + b;
                    if (r > 10) {
                        return Either.<String, Integer>newLeft("Not enough fingers!");
                    } else {
                        return Either.<String, Integer>newRight(r);
                    }
                }).
                        done()
        );
        assertEquals("Left(Not enough fingers!)", handSum.toString());
    }
}
