/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class StringFunctorTest {
    private StringFunctor functor;
    private F<Character, Character> fn;
    
    

    @Before
    public void setUp() {
        functor = new StringFunctor();
        fn = new F<Character, Character>() {
            @Override
            public Character f(Character a) {
                return Character.toUpperCase(a);
            }
        };
    }
    
    @After
    public void tearDown() {
        functor = null;
        fn = null;
    }

    @Test
    public void testFmap() {
        assertEquals("UPPERCASE", StringOf.unwrap(functor.fmap(fn, StringOf.wrap("uPpErcaSe"))));
    }
    
    @Test
    public void testPure() {
        assertEquals("x", StringOf.unwrap(functor.pure(Character.valueOf('x'))));
    }    
}
