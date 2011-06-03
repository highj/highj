/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category2;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import fj.F;
import highj.LC;
import highj.RC;
import highj.__;
import highj.data2.EitherBifunctor;
import highj.data2.EitherOf;
import highj.typeclasses.category.Functor;

/**
 *
 * @author dgronau
 */
public class BifunctorAbstractTest {
    
    private Bifunctor<EitherOf> bifunctor;
    private F<String, Integer> lengthFn;
    private F<Integer, Double> sqrtFn;
    
    @Before
    public void setUp() {
     bifunctor = EitherBifunctor.getInstance();
       lengthFn = new F<String,Integer>(){
            @Override
            public Integer f(String a) {
                return a.length();
            }
        };
        sqrtFn = new F<Integer,Double>(){
            @Override
            public Double f(Integer a) {
                return Math.sqrt(a);
            }
        };
    }
    
    @After
    public void tearDown() {
        bifunctor = null;
        lengthFn = null;
        sqrtFn = null;
    }

    @Test
    //tests both curried and uncurried version
    public void testFirst() {
        __<EitherOf, String, Integer> left = EitherOf.left("Hello");
        __<EitherOf, String, Integer> right = EitherOf.right(16);
        assertEquals("Left(5)", EitherOf.toString(bifunctor.first(lengthFn, left)));        
        assertEquals("Left(5)", EitherOf.toString(bifunctor.<String,Integer,Integer>first(lengthFn).f(left)));        
        assertEquals("Right(16)", EitherOf.toString(bifunctor.first(lengthFn, right)));        
        assertEquals("Right(16)", EitherOf.toString(bifunctor.<String,Integer,Integer>first(lengthFn).f(right)));        
    }

    @Test
    //tests both curried and uncurried version
    public void testSecond() {
        __<EitherOf, String, Integer> left = EitherOf.left("Hello");
        __<EitherOf, String, Integer> right = EitherOf.right(16);
        assertEquals("Left(Hello)", EitherOf.toString(bifunctor.second(sqrtFn, left)));        
        assertEquals("Left(Hello)", EitherOf.toString(bifunctor.<String,Integer,Double>second(sqrtFn).f(left)));        
        assertEquals("Right(4.0)", EitherOf.toString(bifunctor.second(sqrtFn, right)));        
        assertEquals("Right(4.0)", EitherOf.toString(bifunctor.<String,Integer,Double>second(sqrtFn).f(right)));        
    }

    @Test
    public void testGetLCFunctor() {
        __<EitherOf, String, Integer> left = EitherOf.left("Hello");
        __<EitherOf, String, Integer> right = EitherOf.right(16);
        Functor<LC<EitherOf,String>> functor = bifunctor.getLCFunctor();
        assertEquals("Left(Hello)",EitherOf.toString(LC.uncurry(functor.fmap(sqrtFn, LC.curry(left)))));
        assertEquals("Right(4.0)",EitherOf.toString(LC.uncurry(functor.fmap(sqrtFn, LC.curry(right)))));
    }

    @Test
    public void testGetRCFunctor() {
        __<EitherOf, String, Integer> left = EitherOf.left("Hello");
        __<EitherOf, String, Integer> right = EitherOf.right(16);
        Functor<RC<EitherOf,Integer>> functor = bifunctor.getRCFunctor();
        assertEquals("Left(5)",EitherOf.toString(RC.uncurry(functor.fmap(lengthFn, RC.curry(left)))));
        assertEquals("Right(16)",EitherOf.toString(RC.uncurry(functor.fmap(lengthFn, RC.curry(right)))));
    }


}
