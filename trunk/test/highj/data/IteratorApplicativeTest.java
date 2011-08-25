/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import highj._;
import java.util.Iterator;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dgronau
 */
public class IteratorApplicativeTest {
    
    private IteratorApplicative appl;
    
    @Before
    public void setUp() {
        appl = new IteratorApplicative();
    }
    
    @After
    public void tearDown() {
        appl = null;
    }

    @Test
    public void testPure() {
        _<IteratorOf, Character> nestedIt = appl.pure('x');
        Iterator<Character> it = IteratorOf.unwrap(nestedIt);
        for(int i = 0; i < 10; i++) {
            assertEquals(Character.valueOf('x'), it.next());
        }
    }

    @Test
    public void testAp() {
        _<IteratorOf, Integer> nestedIt = IteratorOf.iterator(1,2,3,4,5);
        F<Integer,Double> sqrtF = new F<Integer, Double>(){
            @Override
            public Double f(Integer a) {
                return Math.sqrt(a);
            }
        };
        F<Integer,Double> sqrF = new F<Integer, Double>(){
            @Override
            public Double f(Integer a) {
                return 1.0*a*a;
            }
        };
        
        _<IteratorOf, F<Integer,Double>> fnIt = IteratorOf.iterator(sqrtF,sqrF,sqrF,sqrtF);
        _<IteratorOf, Double> resultIt = appl.ap(fnIt, nestedIt);
        Iterator<Double> result = IteratorOf.unwrap(resultIt);
        assertEquals(Double.valueOf(1.0), result.next());
        assertEquals(Double.valueOf(4.0), result.next());
        assertEquals(Double.valueOf(9.0), result.next());
        assertEquals(Double.valueOf(2.0), result.next());
        assertEquals(false, result.hasNext());
   }
}
