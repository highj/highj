/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.examples;

import fj.F;
import fj.F2;
import fj.data.List;
import highj.CL;
import highj._;
import highj.__;
import highj.data.ListFoldable;
import highj.typeclasses.category.Applicative;
import highj.typeclasses.category.Monad;
import highj.data.ListMonadPlus;
import highj.data.ListOf;
import highj.data.OptionMonadPlus;
import highj.data.OptionOf;
import highj.data2.EitherBifunctor;
import highj.data2.EitherOf;
import highj.typeclasses.category.Functor;
import highj.typeclasses.structural.Foldable;

/**
 *
 * @author DGronau
 */
public class Main {

    private static void testApplicative() {
        System.out.println("\n---Functor.fmap---");
        
        ListOf listOf = ListOf.getInstance();
        Applicative<ListOf> applicative = new ListMonadPlus();
        
        _<ListOf, String> strList = listOf.wrap(List.list("one","two","three"));
        System.out.println("fmap(String.length(), " + listOf.toString(strList) + ")");
        
        //length of String
        F<String,Integer> f = new F<String,Integer>(){
            @Override
            public Integer f(String a) {
                return a.length();
            }
        };
        System.out.println(listOf.toString(applicative.fmap(f, strList))); 
        
        //Index of letter 'e'
        F<String,Integer> fe = new F<String,Integer>(){
            @Override
            public Integer f(String a) {
                return a.indexOf('e');
            }
        };
        _<ListOf, F<String,Integer>> funList = listOf.wrap(List.cons(f, List.single(fe)));
        System.out.println("\n---Applicative.<*>---");
        System.out.println("[String.length(), String.indexOf('e')] <*> [\"one\",\"two\",\"three\"]");
        System.out.println(listOf.toString(applicative.star(funList, strList)));
    }
    
    private static void testJoin() {
        System.out.println("\n---Monad.join---");
        
        Monad<OptionOf> monad = OptionMonadPlus.getInstance();
        OptionOf optionOf = OptionOf.getInstance();
        
        _<OptionOf, String> some = optionOf.some("rotfl");
        _<OptionOf, String> none = optionOf.none();
        _<OptionOf, _<OptionOf,String>> someSome = optionOf.some(some);
        _<OptionOf, _<OptionOf,String>> someNone = optionOf.some(none);
        _<OptionOf, _<OptionOf,String>> noneNone = optionOf.none();
        
        System.out.println("join (Some(Some(\"rotfl\"))) = "  + monad.join(someSome));
        System.out.println("join (Some(None)) = "  +monad.join(someNone));
        System.out.println("join (None) = "  + monad.join(noneNone));
    }

    private static void testSequence() {
        System.out.println("\n---Monad.sequence---");
        Monad<OptionOf> monad = OptionMonadPlus.getInstance();
        OptionOf optionOf = OptionOf.getInstance();
        ListOf listOf = ListOf.getInstance();
        _<OptionOf, Integer> one = optionOf.some(1);
        _<OptionOf, Integer> two = optionOf.some(2);
        _<OptionOf, Integer> three = optionOf.some(3);
        _<ListOf,_<OptionOf, Integer>> listOfOptions = 
                listOf.wrap(List.list(one, two, three));
        _<OptionOf,_<ListOf, Integer>> optionOfList = monad.sequence(listOfOptions);
        System.out.println("sequence [Some(1), Some(2), Some(3)] =  " + optionOfList);
        
        _<OptionOf, Integer> none = optionOf.none();
        _<ListOf,_<OptionOf, Integer>> listOfOptionsWithNone = 
                listOf.wrap(List.list(one, none, three));
        _<OptionOf,_<ListOf, Integer>> optionOfListWithNone = monad.sequence(listOfOptionsWithNone);
        System.out.println("sequence [Some(1), None, Some(3)] =  " + optionOfListWithNone);
    }
    
    private static void testEither() {
        System.out.println("\n---Either.bimap---");
        EitherOf eitherOf = EitherOf.getInstance();
        EitherBifunctor bifunctor = EitherBifunctor.getInstance();
        F<String,Integer> f1 = new F<String,Integer>(){
            @Override
            public Integer f(String a) {
                return a.length();
            }
        };
        F<Integer,Double> f2 = new F<Integer,Double>(){
            @Override
            public Double f(Integer a) {
                return Math.sqrt(a);
            }
        };
        __<EitherOf, String, Integer> left = eitherOf.left("Hello");
        System.out.println("bimap(Left(Hello), String.length, Math.sqrt) = " +
                bifunctor.bimap(f1, f2, left));
        __<EitherOf, String, Integer> right = eitherOf.right(16);
        System.out.println("bimap(Right(16), String.length, Math.sqrt) = " +
                bifunctor.bimap(f1, f2, right));

        System.out.println("\n---Either.fmap (curried)---");
        CL<EitherOf, String> cl = new CL<EitherOf, String>();
        Functor<CL<EitherOf,String>> functor = bifunctor.<String>getCLFunctor();
        System.out.println("map(Left(Hello), Math.sqrt) = " +
                cl.uncurry(functor.fmap(f2, cl.curry(left))));
        System.out.println("map(Right(16), Math.sqrt) = " +
                cl.uncurry(functor.fmap(f2, cl.curry(right))));
        
    }

    private static void testFoldable() {
        System.out.println("\n---ListFoldable---");

        ListOf listOf = ListOf.getInstance();
        Foldable<ListOf> foldable = ListFoldable.getInstance();
        
        _<ListOf, String> strList = listOf.wrap(List.list("one","two","three"));
        System.out.print("foldl((+), \"zero\", " + listOf.toString(strList) + ") = ");
        
        //length of String
        F2<String,String,String> f = new F2<String,String,String>(){
            @Override
            public String f(String a, String b) {
                return a + " + " + b;
            }
        };
        System.out.println(foldable.foldl(f, "zero", strList)); 

        System.out.print("foldr((+), \"zero\", " + listOf.toString(strList) + ") = ");
        System.out.println(foldable.foldr(f, "zero", strList)); 
    }
    
    public static void main(String[] args) {
        testApplicative();
        testJoin();
        testSequence();
        testEither();
        testFoldable();
    }

}
