/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.data.List;
import fj.F;
import fj.Unit;
import highj.data.ListOf;
import highj.data.ListMonadPlus;
import highj.data.OptionMonadPlus;
import highj._;
import highj.data.OptionOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Back-to-back test with Haskell
 * @author dgronau
 */
public class FunctorAbstractTest {

    private Functor<OptionOf> optionFunctor;
    private Functor<ListOf> listFunctor;
    private F<String, Integer> lengthFn;

    @Before
    public void setUp() {
        optionFunctor = OptionMonadPlus.getInstance();
        listFunctor = ListMonadPlus.getInstance();
        lengthFn = new F<String, Integer>() {
            @Override
            public Integer f(String a) {
                return a.length();
            }
        };
    }

    @After
    public void tearDown() {
        optionFunctor = null;
        listFunctor = null;
        lengthFn = null;
    }

    @Test
    public void testLeft$() {
        //"3" <$ Just 5
        //-- Just "3"
        assertEquals("3", OptionOf.get(optionFunctor.left$("3", OptionOf.some(5))));
        //"3" <$ Nothing
        //-- Nothing
        assertEquals(true, OptionOf.isNone(optionFunctor.left$("3", OptionOf.<Integer>none())));
        //"x" <$ [1,1,1]
        //-- ["x","x","x"]
        assertEquals("[x, x, x]", ListOf.toString(
                listFunctor.left$("x", ListOf.list(1, 1, 1))));
    }

    @Test
    public void testVoidF() {
        assertEquals(Unit.unit(), OptionOf.get(optionFunctor.voidF(OptionOf.some(5))));
        assertEquals(true, OptionOf.isNone(optionFunctor.voidF(OptionOf.<Integer>none())));
        List<Unit> units = ListOf.unwrap(listFunctor.voidF(ListOf.wrap(List.list(1, 1))));
        assertEquals(Unit.unit(), units.head());
        assertEquals(Unit.unit(), units.last());
        assertEquals(2, units.length());
    }

    @Test
    public void testFlip() {
        _<OptionOf, F<String, Integer>> optFn =
                OptionOf.some(lengthFn);
        _<OptionOf, Integer> result = optionFunctor.flip(optFn, "123456789");
        assertEquals(Integer.valueOf(9), OptionOf.get(result));
        _<OptionOf, Integer> resultEmpty =
                optionFunctor.flip(OptionOf.<F<String, Integer>>none(), "123456789");
        assertEquals(true, OptionOf.isNone(resultEmpty));
    }

    @Test
    public void testLift() {
        F<_<OptionOf, String>, _<OptionOf, Integer>> fn =
                optionFunctor.lift(lengthFn);
        assertEquals(Integer.valueOf(9), OptionOf.get(fn.f(OptionOf.some("123456789"))));
        assertEquals(true, OptionOf.isNone(fn.f(OptionOf.<String>none())));
    }
}
