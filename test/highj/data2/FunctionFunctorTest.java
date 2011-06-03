/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.function.Strings;
import highj.typeclasses.category.Functor;
import highj.LC;
import fj.F;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class FunctionFunctorTest {
    
    @Test
    public void testFmap() {
        Functor<LC<FunctionOf,String>> functor = new FunctionFunctor<String>();
        
        F<Integer, Double> sqrt = new F<Integer, Double>() {
            @Override
            public Double f(Integer a) {
                return Math.sqrt(a);
            }
        };

        F<String, Double> sqrtOfLength = FunctionOf.unwrapLC(
                functor.fmap(sqrt, FunctionOf.wrapLC(Strings.length)));
        
        assertEquals(Double.valueOf(3.0), sqrtOfLength.f("123456789"));
    }
}
