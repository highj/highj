package org.highj.do_;

import org.highj.data.collection.Maybe;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import org.highj._;
import static org.junit.Assert.assertTrue;

import org.highj.data.collection.Either;
import org.highj.data.collection.List;
import org.highj.do_.Do.DoBlock;
import org.highj.do_.Do.MValue;
import static org.highj.do_.Do.do_;

/**
 *
 * @author clintonselke
 */
public class DoTest {
    
    @Test
    @SuppressWarnings("UnusedAssignment")
    public void testNondeterminism() {
        List<String> results = List.narrow(do_(List.monadPlus, new DoBlock<List.µ,String>() {
            @Override
            public <H> _<List.µ, String> run(Do.MContext<H, List.µ> ctx) {
                MValue<H,Integer> va = ctx.assign(List.range(1, 1, 3));
                MValue<H,Integer> vb = ctx.assign(List.range(1, 1, 3));
                MValue<H,String> vr = ctx.let2(va, vb, (Integer a, Integer b) -> "" + a + " x " + b + " = " + (a*b));
                return ctx.doneRes(vr);
            }
        }));
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
        Maybe<String> onetwo = Maybe.narrow(do_(Maybe.monad, new DoBlock<Maybe.µ,String>() {
            @Override
            public <H> _<Maybe.µ, String> run(Do.MContext<H, Maybe.µ> ctx) {
                MValue<H,String> va = ctx.assign(Maybe.newJust("one"));
                MValue<H,String> vb = ctx.assign(Maybe.newJust("two"));
                MValue<H,String> vr = ctx.let2(va, vb, (String a, String b) -> a + b);
                return ctx.doneRes(vr);
            }
        }));
        assertEquals("onetwo", onetwo.get());

        Maybe<String> empty = Maybe.narrow(do_(Maybe.monad, new DoBlock<Maybe.µ,String>() {
            @Override
            public <H> _<Maybe.µ, String> run(Do.MContext<H, Maybe.µ> ctx) {
                MValue<H,String> va = ctx.assign(Maybe.newJust("one"));
                MValue<H,String> vb = ctx.assign(Maybe.newNothing());
                MValue<H,String> vr = ctx.let2(va, vb, (String a, String b) -> a + b);
                return ctx.doneRes(vr);
            }
        }));
        assertTrue(empty.isNothing());
    }

    @Test
    public void testBind() {
        Either<String, Integer> handSum = Either.narrow(do_(Either.<String>monad(), new DoBlock<_<Either.µ,String>,Integer>() {
            @Override
            public <H> _<_<Either.µ, String>, Integer> run(Do.MContext<H, _<Either.µ, String>> ctx) {
                MValue<H,Integer> va = ctx.assign(Either.newRight(6));
                MValue<H,Integer> vb = ctx.assign(Either.newRight(7));
                return ctx.doneRes(ctx.assignBind2(va, vb, (Integer a, Integer b) -> {
                    int r = a + b;
                    if (r > 10) {
                        return Either.<String, Integer>newLeft("Not enough fingers!");
                    } else {
                        return Either.<String, Integer>newRight(r);
                    }
                }));
            }
        }));
        assertEquals("Left(Not enough fingers!)", handSum.toString());
    }
}
