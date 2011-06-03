/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.function.Strings;
import highj.data.StringOf;
import highj.data.StringFunctor;
import highj._;
import highj.data.ListMonadPlus;
import highj.data.OptionMonadPlus;
import fj.F;
import fj.function.Characters;
import highj.data.ListOf;
import highj.data.OptionOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Back-to-back test with Haskell
 * @author DGronau
 */
public class FunctorBoundedAbstractTest {
   
    private FunctorBounded<OptionOf, Object> optionFunctor;
    private FunctorBounded<ListOf, Object> listFunctor;
    private FunctorBounded<StringOf, Character> stringFunctor;

    @Before
    public void setUp() {
        optionFunctor = OptionMonadPlus.getInstance();
        listFunctor = ListMonadPlus.getInstance();
        stringFunctor = StringFunctor.getInstance();
    }

    @After
    public void tearDown() {
        optionFunctor = null;
        listFunctor = null;
        stringFunctor = null;
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
        //Prelude Control.Monad Data.Functor> '?' <$ "blub"
        //"????"
        assertEquals("????", StringOf.unwrap(stringFunctor.left$('?', StringOf.wrap("blub"))));
    }

    @Test
    public void testLift() {
        F<_<OptionOf, String>, _<OptionOf, Integer>> fn =
                optionFunctor.lift(Strings.length);
        //liftM length (Just "123456789")
        //-- Just 9        
        assertEquals(Integer.valueOf(9), OptionOf.get(fn.f(OptionOf.some("123456789"))));
        //liftM length Nothing
        //-- Nothing
        assertEquals(true, OptionOf.isNone(fn.f(OptionOf.<String>none())));
        
        F<_<StringOf,Character>,_<StringOf,Character>> upperFn = 
                stringFunctor.lift(Characters.toUpperCase);
        //liftM toUpper ""
        //-- ""
        assertEquals("", StringOf.unwrap(upperFn.f(StringOf.wrap(""))));
        //liftM toUpper "pIzZa"
        //-- "PIZZA"        
        assertEquals("PIZZA", StringOf.unwrap(upperFn.f(StringOf.wrap("pIzZa"))));
    }
}
