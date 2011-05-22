/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import fj.F2;
import highj.data.OptionMonadPlus;
import highj.data.OptionOf;
import highj.data.ListMonadPlus;
import highj.data.ListOf;
import fj.F;
import highj._;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dgronau
 */
public class ApplicativeAbstractTest {
    
    private Applicative<ListOf> listApplicative;
    private Applicative<OptionOf> optionApplicative;
    private F<String, Integer> lengthFn;
    
    @Before
    public void setUp() {
        listApplicative = new ListMonadPlus();
        optionApplicative = new OptionMonadPlus();
        lengthFn = new F<String, Integer>() {
            @Override
            public Integer f(String a) {
                return a.length();
            }
        };
    }
    
    @After
    public void tearDown() {
        listApplicative = null;
        optionApplicative = null;
        lengthFn = null;
    }

    @Test
    public void testRightSeq() {
        //['a','b','c'] *> [1,2]
        //-- [1,2,1,2,1,2]
        _<ListOf, Integer> result = listApplicative.rightSeq(ListOf.list('a','b','c'), ListOf.list(1, 2));
        assertEquals("[1, 2, 1, 2, 1, 2]", ListOf.toString(result));
    }

    @Test
    public void testLeftSeq() {
        //['a','b','c'] <* [1,2]
        //-- "aabbcc"
        _<ListOf, Character> result = listApplicative.leftSeq(ListOf.list('a','b','c'), ListOf.list(1, 2));
        assertEquals("[a, a, b, b, c, c]", ListOf.toString(result));
        
    }

    @Test
    public void testLift2() {
        //liftA2 (\x y -> show x ++ show y) [1,2,3] [True, False]
        //-- ["1True","1False","2True","2False","3True","3False"]
        F<Integer, F<Boolean,String>> fn2 = new F2<Integer,Boolean,String>(){

            @Override
            public String f(Integer a, Boolean b) {
                return "" + a + b;
            }
        
        }.curry();
        _<ListOf,String> result = listApplicative.lift2(fn2).f(ListOf.list(1,2,3), ListOf.list(true,false));
        assertEquals("[1true, 1false, 2true, 2false, 3true, 3false]", ListOf.toString(result));
    }

    @Test
    public void testLift3() {
        //liftA3 (\x y z -> concat[show x,show y,show z]) [1,2,3] [True, False] [0.1,0.2]
        //-- ["1True0.1","1True0.2","1False0.1","1False0.2","2True0.1","2True0.2","2False0.1",
        //-- "2False0.2","3True0.1","3True0.2","3False0.1","3False0.2"]
        F<Integer, F<Boolean,F<Double,String>>> fn3 = new F<Integer, F<Boolean,F<Double,String>>>(){

            @Override
            public F<Boolean, F<Double, String>> f(final Integer a) {
                return new F2<Boolean, Double, String>() {

                    @Override
                    public String f(Boolean b, Double c) {
                        return "" + a + b + c;
                    }
                    
                }.curry();
            }
        };
        _<ListOf,String> result = listApplicative.lift3(fn3).f(ListOf.list(1,2), ListOf.list(true,false), ListOf.list(0.1, 0.2));
        assertEquals("[1true0.1, 1true0.2, 1false0.1, 1false0.2, 2true0.1, 2true0.2, 2false0.1, 2false0.2]",
                ListOf.toString(result));
    }
    
}
