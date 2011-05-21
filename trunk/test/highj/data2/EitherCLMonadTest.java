/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import highj.typeclasses.category.Monad;
import highj._;
import highj.CL;
import fj.F;
import fj.data.Either;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DGronau
 */
public class EitherCLMonadTest {
    private Monad<CL<EitherOf,Double>> monad;
    private F<String, Integer> lengthFn;
    
    @Before
    public void setUp() {
        lengthFn = new F<String, Integer>() {
            @Override
            public Integer f(String a) {
                return a.length();
            }
        };
        monad = new EitherCLMonad<Double>();
    }
    
    @After
    public void tearDown() {
        lengthFn = null;
        monad = null;
    }

    @Test
    public void testBind() {
        _<CL<EitherOf, Double>,String> twelve = 
                EitherOf.wrapCL(Either.<Double,String>left(12.0));
        _<CL<EitherOf, Double>,String> pizza = 
                EitherOf.wrapCL(Either.<Double,String>right("pizza"));
        _<CL<EitherOf, Double>,String> pi = 
                EitherOf.wrapCL(Either.<Double,String>right("pi"));
        
        //fn "pi" = Left 3.14
        //fn x = Right $ length x
        F<String, _<CL<EitherOf, Double>, Integer>> fn = new F<String, _<CL<EitherOf, Double>, Integer>>() {
            @Override
            public _<CL<EitherOf, Double>, Integer> f(String a) {
                if (a.equals("pi")) {
                    return EitherOf.wrapCL(Either.<Double,Integer>left(3.14));
                } else {
                    return EitherOf.wrapCL(Either.<Double,Integer>right(a.length()));
                }
            }
        };
        
        //Left 12.0 >>= fn 
        //-- Left 12.0
        assertEquals("Left(12.0)", EitherOf.toString(CL.uncurry(
                monad.bind(twelve, fn))));
        
        //Right "pi" >>= fn 
        //-- Left 3.14
        assertEquals("Left(3.14)", EitherOf.toString(CL.uncurry(
                monad.bind(pi, fn))));
        
        //Right "pizza" >>= fn 
        //-- Right 5
        assertEquals("Right(5)", EitherOf.toString(CL.uncurry(
                monad.bind(pizza, fn))));
    }

    @Test
    public void testStar() {
        _<CL<EitherOf, Double>,F<String,Integer>> fourtyTwo = 
                EitherOf.wrapCL(Either.<Double,F<String,Integer>>left(42.0));
        _<CL<EitherOf, Double>,F<String,Integer>> lengthFnEither = 
                EitherOf.wrapCL(Either.<Double,F<String,Integer>>right(lengthFn));
        _<CL<EitherOf, Double>,String> twelve = 
                EitherOf.wrapCL(Either.<Double,String>left(12.0));
        _<CL<EitherOf, Double>,String> test = 
                EitherOf.wrapCL(Either.<Double,String>right("test"));
        
        //(Left 42.0 <*> Left 12) :: Either Double Int
        //-- Left 42.0
        assertEquals("Left(42.0)", EitherOf.toString(CL.uncurry(
                monad.star(fourtyTwo, twelve))));
        //(Left 42.0 <*> Right "test") :: Either Double Int
        //-- Left 42.0
        assertEquals("Left(42.0)", EitherOf.toString(CL.uncurry(
                monad.star(fourtyTwo, test))));
        //(Right length <*> Left 12.0) :: Either Double Int
        //-- Left 12.0
        assertEquals("Left(12.0)", EitherOf.toString(CL.uncurry(
                monad.star(lengthFnEither, twelve))));
        //(Right length <*> Right "test") :: Either Double Int
        //-- Right 4
        assertEquals("Right(4)", EitherOf.toString(CL.uncurry(
                monad.star(lengthFnEither, test))));
    }

    @Test
    public void testPure() {
        //(pure "test") :: Either Double String
        //-- Right "test"
        _<CL<EitherOf,Double>,String> either = monad.pure("test");
        assertEquals("Right(test)", EitherOf.toString(CL.uncurry(either)));
    }

    @Test
    public void testFmap() {
        //(fmap length (Left 42.0)) :: Either Double Int
        //-- Left 42.0
        _<CL<EitherOf,Double>,String> left = EitherOf.wrapCL(Either.<Double,String>left(42.0));
        assertEquals("Left(42.0)", EitherOf.toString(CL.uncurry(monad.fmap(lengthFn, left))));
        //(fmap length (Right "RIGHT")) :: Either Double Int
        //-- Right 5
        _<CL<EitherOf,Double>,String> right = EitherOf.wrapCL(Either.<Double,String>right("RIGHT"));
        assertEquals("Right(5)", EitherOf.toString(CL.uncurry(monad.fmap(lengthFn, right))));
    }
}
