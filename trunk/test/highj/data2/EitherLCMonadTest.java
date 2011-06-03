/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.data2;

import fj.function.Strings;
import highj.typeclasses.category.Monad;
import highj._;
import highj.LC;
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
public class EitherLCMonadTest {
    private Monad<LC<EitherOf,Double>> monad;
    
    @Before
    public void setUp() {
        monad = new EitherLCMonad<Double>();
    }
    
    @After
    public void tearDown() {
        monad = null;
    }

    @Test
    public void testStar() {
        _<LC<EitherOf, Double>,F<String,Integer>> fourtyTwo = 
                EitherOf.wrapLC(Either.<Double,F<String,Integer>>left(42.0));
        _<LC<EitherOf, Double>,F<String,Integer>> lengthFnEither = 
                EitherOf.wrapLC(Either.<Double,F<String,Integer>>right(Strings.length));
        _<LC<EitherOf, Double>,String> twelve = 
                EitherOf.wrapLC(Either.<Double,String>left(12.0));
        _<LC<EitherOf, Double>,String> test = 
                EitherOf.wrapLC(Either.<Double,String>right("test"));
        
        //(Left 42.0 <*> Left 12) :: Either Double Int
        //-- Left 42.0
        assertEquals("Left(42.0)", EitherOf.toString(LC.uncurry(
                monad.ap(fourtyTwo, twelve))));
        //(Left 42.0 <*> Right "test") :: Either Double Int
        //-- Left 42.0
        assertEquals("Left(42.0)", EitherOf.toString(LC.uncurry(
                monad.ap(fourtyTwo, test))));
        //(Right length <*> Left 12.0) :: Either Double Int
        //-- Left 12.0
        assertEquals("Left(12.0)", EitherOf.toString(LC.uncurry(
                monad.ap(lengthFnEither, twelve))));
        //(Right length <*> Right "test") :: Either Double Int
        //-- Right 4
        assertEquals("Right(4)", EitherOf.toString(LC.uncurry(
                monad.ap(lengthFnEither, test))));
    }

    @Test
    public void testPure() {
        //(pure "test") :: Either Double String
        //-- Right "test"
        _<LC<EitherOf,Double>,String> either = monad.pure("test");
        assertEquals("Right(test)", EitherOf.toString(LC.uncurry(either)));
    }

    @Test
    public void testFmap() {
        //(fmap length (Left 42.0)) :: Either Double Int
        //-- Left 42.0
        _<LC<EitherOf,Double>,String> left = EitherOf.wrapLC(Either.<Double,String>left(42.0));
        assertEquals("Left(42.0)", EitherOf.toString(LC.uncurry(monad.fmap(Strings.length, left))));
        //(fmap length (Right "RIGHT")) :: Either Double Int
        //-- Right 5
        _<LC<EitherOf,Double>,String> right = EitherOf.wrapLC(Either.<Double,String>right("RIGHT"));
        assertEquals("Right(5)", EitherOf.toString(LC.uncurry(monad.fmap(Strings.length, right))));
    }
    

    @Test
    public void testBind() {
        _<LC<EitherOf, Double>,String> twelve = 
                EitherOf.wrapLC(Either.<Double,String>left(12.0));
        _<LC<EitherOf, Double>,String> pizza = 
                EitherOf.wrapLC(Either.<Double,String>right("pizza"));
        _<LC<EitherOf, Double>,String> pi = 
                EitherOf.wrapLC(Either.<Double,String>right("pi"));
        
        //fn "pi" = Left 3.14
        //fn x = Right $ length x
        F<String, _<LC<EitherOf, Double>, Integer>> fn = new F<String, _<LC<EitherOf, Double>, Integer>>() {
            @Override
            public _<LC<EitherOf, Double>, Integer> f(String a) {
                if (a.equals("pi")) {
                    return EitherOf.wrapLC(Either.<Double,Integer>left(3.14));
                } else {
                    return EitherOf.wrapLC(Either.<Double,Integer>right(a.length()));
                }
            }
        };
        
        //Left 12.0 >>= fn 
        //-- Left 12.0
        assertEquals("Left(12.0)", EitherOf.toString(LC.uncurry(
                monad.bind(twelve, fn))));
        
        //Right "pi" >>= fn 
        //-- Left 3.14
        assertEquals("Left(3.14)", EitherOf.toString(LC.uncurry(
                monad.bind(pi, fn))));
        
        //Right "pizza" >>= fn 
        //-- Right 5
        assertEquals("Right(5)", EitherOf.toString(LC.uncurry(
                monad.bind(pizza, fn))));
    }
 
}
