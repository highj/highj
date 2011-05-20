/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F;
import highj.data.ListMonadPlus;
import highj.data.OptionMonadPlus;
import highj.data.OptionOf;
import highj.data.ListOf;
import highj._;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dgronau
 */
public class FunctorsTest {
    
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
    public void testBinary() {
        _<OptionOf, _<ListOf, String>> optStrings =
                OptionOf.some(ListOf.list("one", "two", "three"));
        _<OptionOf, _<ListOf, Integer>> optInts =
                Functors.binary(optionFunctor, listFunctor, lengthFn, optStrings);
        assertEquals("[3, 3, 5]", ListOf.toString(OptionOf.get(optInts)));
    }

    @Test
    public void testTrinary() {
        _<OptionOf, _<OptionOf, _<ListOf, String>>> optStrings =
                OptionOf.some(OptionOf.some(ListOf.list("one", "two", "three")));
        _<OptionOf, _<OptionOf, _<ListOf, Integer>>> optInts =
                Functors.trinary(optionFunctor, optionFunctor, listFunctor, lengthFn, optStrings);
        assertEquals("[3, 3, 5]", ListOf.toString(OptionOf.get(OptionOf.get(optInts))));
    }
}
