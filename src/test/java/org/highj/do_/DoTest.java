package org.highj.do_;

import org.highj.data.collection.Maybe;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
}
