/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.P;
import fj.Show;
import fj.P2;
import fj.F;
import highj.__;
import highj.typeclasses.category2.Arrow;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class FunctionArrowTest {
  
    private Arrow<FunctionOf> arrow;
    private F<String, Integer> lengthFn;
    private F<Integer, Integer> sqrFn;
    
    @Before
    public void setUp() {
        arrow = new FunctionArrow();
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
    }
    
    @After
    public void tearDown() {
        arrow = null;
        lengthFn = null;
        sqrFn = null;
    }

    @Test
    public void testId() {
        //id "PIZZA"
        //-- "PIZZA"
        __<FunctionOf, String, String> idArrow = arrow.id();
        assertEquals("PIZZA", FunctionOf.apply(idArrow, "PIZZA"));
    }

    @Test
    public void testDot() {
        //(^2) <<< length $ "PIZZA"
        //-- 25        
        __<FunctionOf, Integer, Integer> sqrArrow = FunctionOf.wrap(sqrFn);
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(lengthFn);
        __<FunctionOf, String, Integer> dotArrow = arrow.dot(sqrArrow, lengthArrow);
        assertEquals(Integer.valueOf(25), FunctionOf.apply(dotArrow, "PIZZA"));
    }

    @Test
    public void testArr() {
        __<FunctionOf, String, Integer> lengthArrow = arrow.arr(lengthFn);
        //arr length $ ""
        //-- 0
        assertEquals(Integer.valueOf(0), FunctionOf.apply(lengthArrow, ""));
        //arr length $ "PIZZA"
        //-- 5
        assertEquals(Integer.valueOf(5), FunctionOf.apply(lengthArrow, "PIZZA"));
    }

    @Test
    public void testFirst() {
        //first length $ ("PIZZA", 3.14)
        //(5,3.14)
        __<FunctionOf, String, Integer> lengthArrow = FunctionOf.wrap(lengthFn);
        __<FunctionOf, P2<String, Double>, P2<Integer, Double>> fst = arrow.first(lengthArrow);
        Show<P2<Integer, Double>> show = Show.p2Show(Show.intShow, Show.doubleShow);
        assertEquals("(5,3.14)", show.showS(FunctionOf.apply(fst, P.p("PIZZA", 3.14))));
    }

}
