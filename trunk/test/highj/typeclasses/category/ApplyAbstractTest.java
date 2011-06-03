/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F3;
import fj.F2;
import highj.data.ListMonadPlus;
import highj.data.ListOf;
import fj.F;
import fj.Function;
import highj._;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class ApplyAbstractTest {
    private Apply<ListOf> listApply;

    @Before
    public void setUp() {
        listApply = new ListMonadPlus();
    }

    @After
    public void tearDown() {
        listApply = null;
    }

    @Test
    public void testRightSeq() {
        //['a','b','c'] *> [1,2]
        //-- [1,2,1,2,1,2]
        _<ListOf, Integer> result = listApply.rightSeq(ListOf.list('a', 'b', 'c'), ListOf.list(1, 2));
        assertEquals("[1, 2, 1, 2, 1, 2]", ListOf.toString(result));
    }

    @Test
    public void testLeftSeq() {
        //['a','b','c'] <* [1,2]
        //-- "aabbcc"
        _<ListOf, Character> result = listApply.leftSeq(ListOf.list('a', 'b', 'c'), ListOf.list(1, 2));
        assertEquals("[a, a, b, b, c, c]", ListOf.toString(result));
    }

    @Test
    //tests flat version too
    public void testLift2() {
        //liftA2 (\x y -> show x ++ show y) [1,2,3] [True, False]
        //-- ["1True","1False","2True","2False","3True","3False"]
        F2<Integer, Boolean, String> fn2Flat = new F2<Integer, Boolean, String>() {
            @Override
            public String f(Integer a, Boolean b) {
                return "" + a + b;
            }
        };
        F<Integer, F<Boolean, String>> fn2 = fn2Flat.curry();
        _<ListOf, String> result = listApply.lift2(fn2).f(ListOf.list(1, 2, 3), ListOf.list(true, false));
        assertEquals("[1true, 1false, 2true, 2false, 3true, 3false]", ListOf.toString(result));
        _<ListOf, String> resultFlat = listApply.lift2Flat(fn2Flat).f(ListOf.list(1, 2, 3), ListOf.list(true, false));
        assertEquals("[1true, 1false, 2true, 2false, 3true, 3false]", ListOf.toString(resultFlat));
    }

    @Test
    //tests flat version too
    public void testLift3() {
        //liftA3 (\x y z -> concat[show x,show y,show z]) [1,2,3] [True, False] [0.1,0.2]
        //-- ["1True0.1","1True0.2","1False0.1","1False0.2","2True0.1","2True0.2","2False0.1",
        //-- "2False0.2","3True0.1","3True0.2","3False0.1","3False0.2"]
        F3<Integer, Boolean, Double, String> fn3Flat = new F3<Integer, Boolean, Double, String>() {
            @Override
            public String f(Integer a, Boolean b, Double c) {
                return "" + a + b + c;
            }
        };
        F<Integer, F<Boolean, F<Double, String>>> fn3 = Function.curry(fn3Flat);

        _<ListOf, String> result = listApply.lift3(fn3).f(ListOf.list(1, 2), ListOf.list(true, false), ListOf.list(0.1, 0.2));
        assertEquals("[1true0.1, 1true0.2, 1false0.1, 1false0.2, 2true0.1, 2true0.2, 2false0.1, 2false0.2]",
                ListOf.toString(result));
        _<ListOf, String> resultFlat = listApply.lift3Flat(fn3Flat).f(ListOf.list(1, 2), ListOf.list(true, false), ListOf.list(0.1, 0.2));
        assertEquals("[1true0.1, 1true0.2, 1false0.1, 1false0.2, 2true0.1, 2true0.2, 2false0.1, 2false0.2]",
                ListOf.toString(resultFlat));
    }
}
