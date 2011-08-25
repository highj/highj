/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data;

import fj.F;
import highj._;
import java.util.Set;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dgronau
 */
public class SetApplicativeTest {
    
    private SetApplicative appl;
    
    @Before
    public void setUp() {
        appl = new SetApplicative();
    }
    
    @After
    public void tearDown() {
        appl = null;
    }

    @Test
    public void testPure() {
        _<SetOf, Character> nestedSet = appl.pure('x');
        Set<Character> set = SetOf.unwrap(nestedSet);
        assertEquals("[x]", set.toString());
    }

    @Test
    public void testAp() {
        _<SetOf, Integer> nestedSet = SetOf.set(1,4,9);
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
        
        _<SetOf, F<Integer,Double>> fnSet = SetOf.set(sqrtF,sqrF,sqrtF,sqrF);
        _<SetOf, Double> resultSet = appl.ap(fnSet, nestedSet);
        Set<Double> result = SetOf.unwrap(resultSet);
        assertEquals(true, result.contains(1.0));
        assertEquals(true, result.contains(2.0));
        assertEquals(true, result.contains(3.0));
        assertEquals(true, result.contains(16.0));
        assertEquals(true, result.contains(81.0));
        assertEquals(5, result.size());
   }
}
