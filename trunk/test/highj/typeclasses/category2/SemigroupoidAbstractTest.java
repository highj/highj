/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import highj.data2.FunctionArrow;
import highj.data2.FunctionOf;
import fj.F;
import highj.__;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class SemigroupoidAbstractTest {
    
    Semigroupoid<FunctionOf> semi;
    F<String, Integer> lengthFn;
    F<Integer, Integer> sqrFn;
    F<Integer, String> toStringFn;
    
    @Before
    public void setUp() {
        semi = FunctionArrow.getInstance();
        lengthFn = new F<String,Integer>(){
            @Override
            public Integer f(String a) {
                return a.length();
            }
        };
        sqrFn = new F<Integer, Integer>() {
            @Override
            public Integer f(Integer a) {
               return a*a;
            }
        };
        toStringFn = new F<Integer, String>() {
            @Override
            public String f(Integer a) {
               return "" + a;
            }
        };
    }
    
    @After
    public void tearDown() {
        semi = null;
        lengthFn = null;
        sqrFn = null;
    }

 
    @Test
    public void testThen() {
        //length >>> (^2) $ "PIZZA"
        //-- 25
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(lengthFn);
        __<FunctionOf, Integer, Integer> sqrArrow = FunctionOf.wrap(sqrFn);
        __<FunctionOf, String, Integer> thenArrow = semi.then(lengthArrow, sqrArrow);
        assertEquals(Integer.valueOf(25), FunctionOf.apply(thenArrow, "PIZZA"));
    }

    @Test
    public void testThen2() {
        //Prelude Control.Arrow> length >>> (^2) >>> show $ "PIZZA"
        //"25"
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(lengthFn);
        __<FunctionOf, Integer, Integer> sqrArrow = FunctionOf.wrap(sqrFn);
        __<FunctionOf, Integer, String> toStringArrow = FunctionOf.wrap(toStringFn);
        __<FunctionOf, String, String> thenArrow = semi.then(lengthArrow, sqrArrow, toStringArrow);
        assertEquals("25", FunctionOf.apply(thenArrow, "PIZZA"));
    }
    
    @Test
    public void testThen3() {
        //Prelude Control.Arrow> length >>> (^2) >>> show >>> length $ "PIZZAPIZZA"
        //-- 3
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(lengthFn);
        __<FunctionOf, Integer, Integer> sqrArrow = FunctionOf.wrap(sqrFn);
        __<FunctionOf, Integer, String> toStringArrow = FunctionOf.wrap(toStringFn);
        __<FunctionOf, String, Integer> thenArrow = 
                semi.then(lengthArrow, sqrArrow, toStringArrow, lengthArrow);
        assertEquals(Integer.valueOf(3), FunctionOf.apply(thenArrow, "PIZZAPIZZA"));
    }
    
}
