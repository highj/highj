/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.function.Strings;
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

    @Before
    public void setUp() {
        optionFunctor = OptionMonadPlus.getInstance();
        listFunctor = ListMonadPlus.getInstance();
    }

    @After
    public void tearDown() {
        optionFunctor = null;
        listFunctor = null;
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
                OptionOf.some(Strings.length);
        _<OptionOf, Integer> result = optionFunctor.flip(optFn, "123456789");
        assertEquals(Integer.valueOf(9), OptionOf.get(result));
        _<OptionOf, Integer> resultEmpty =
                optionFunctor.flip(OptionOf.<F<String, Integer>>none(), "123456789");
        assertEquals(true, OptionOf.isNone(resultEmpty));
    }

}

