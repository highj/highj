/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.examples;

import fj.F;
import fj.F2;
import fj.data.List;
import highj.LC;
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
        
        Applicative<ListOf> applicative = new ListMonadPlus();
        
        _<ListOf, String> strList = ListOf.wrap(List.list("one","two","three"));
        System.out.println("fmap(String.length(), " + ListOf.toString(strList) + ")");
        
        //length of String
        F<String,Integer> f = new F<String,Integer>(){
            @Override
            public Integer f(String a) {
                return a.length();
            }
        };
        System.out.println(ListOf.toString(applicative.fmap(f, strList))); 
        
        //Index of letter 'e'
        F<String,Integer> fe = new F<String,Integer>(){
            @Override
            public Integer f(String a) {
                return a.indexOf('e');
            }
        };
        _<ListOf, F<String,Integer>> funList = ListOf.wrap(List.cons(f, List.single(fe)));
        System.out.println("\n---Applicative.<*>---");
        System.out.println("[String.length(), String.indexOf('e')] <*> [\"one\",\"two\",\"three\"]");
        System.out.println(ListOf.toString(applicative.star(funList, strList)));
    }
    
    private static void testJoin() {
        System.out.println("\n---Monad.join---");
        
        Monad<OptionOf> monad = OptionMonadPlus.getInstance();
        
        _<OptionOf, String> some = OptionOf.some("rotfl");
        _<OptionOf, String> none = OptionOf.none();
        _<OptionOf, _<OptionOf,String>> someSome = OptionOf.some(some);
        _<OptionOf, _<OptionOf,String>> someNone = OptionOf.some(none);
        _<OptionOf, _<OptionOf,String>> noneNone = OptionOf.none();
        
        System.out.println("join (Some(Some(\"rotfl\"))) = "  + monad.join(someSome));
        System.out.println("join (Some(None)) = "  +monad.join(someNone));
        System.out.println("join (None) = "  + monad.join(noneNone));
    }

    private static void testSequence() {
        System.out.println("\n---Monad.sequence---");
        Monad<OptionOf> monad = OptionMonadPlus.getInstance();
        _<OptionOf, Integer> one = OptionOf.some(1);
        _<OptionOf, Integer> two = OptionOf.some(2);
        _<OptionOf, Integer> three = OptionOf.some(3);
        _<ListOf,_<OptionOf, Integer>> ListOfOptions = 
                ListOf.wrap(List.list(one, two, three));
        _<OptionOf,_<ListOf, Integer>> optionOfList = monad.sequence(ListOfOptions);
        System.out.println("sequence [Some(1), Some(2), Some(3)] =  " + optionOfList);
        
        _<OptionOf, Integer> none = OptionOf.none();
        _<ListOf,_<OptionOf, Integer>> ListOfOptionsWithNone = 
                ListOf.wrap(List.list(one, none, three));
        _<OptionOf,_<ListOf, Integer>> optionOfListWithNone = monad.sequence(ListOfOptionsWithNone);
        System.out.println("sequence [Some(1), None, Some(3)] =  " + optionOfListWithNone);
        
        _<ListOf,Integer> list12 = ListOf.wrap(List.list(1,2));
        _<ListOf,Integer> list3 = ListOf.wrap(List.list(3));
        _<ListOf,Integer> list456 = ListOf.wrap(List.list(4,5,6));
        _<ListOf,_<ListOf, Integer>> list = ListOf.wrap(List.list(list12,list3,list456));
        _<ListOf,_<ListOf, Integer>> sequencedList = ListMonadPlus.getInstance().sequence(list);
        System.out.println("sequence [[1,2],[3],[4,5,6]] = " + ListOf.unwrap(sequencedList).map(new F<_<ListOf, Integer>,String>(){
            @Override
            public String f(_<ListOf, Integer> a) {
                return ListOf.toString(a);
            }
        }).toCollection());
    }
    
    private static void testEither() {
        System.out.println("\n---Either.bimap---");
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
        __<EitherOf, String, Integer> left = EitherOf.left("Hello");
        System.out.println("bimap(Left(Hello), String.length, Math.sqrt) = " +
                bifunctor.bimap(f1, f2, left));
        __<EitherOf, String, Integer> right = EitherOf.right(16);
        System.out.println("bimap(Right(16), String.length, Math.sqrt) = " +
                bifunctor.bimap(f1, f2, right));

        System.out.println("\n---Either.fmap (curried)---");
        Functor<LC<EitherOf,String>> functor = bifunctor.<String>getLCFunctor();
        System.out.println("map(Left(Hello), Math.sqrt) = " +
                LC.uncurry(functor.fmap(f2, LC.curry(left))));
        System.out.println("map(Right(16), Math.sqrt) = " +
                LC.uncurry(functor.fmap(f2, LC.curry(right))));
        
    }

    private static void testFoldable() {
        System.out.println("\n---ListFoldable---");

        Foldable<ListOf> foldable = ListFoldable.getInstance();
        
        _<ListOf, String> strList = ListOf.wrap(List.list("one","two","three"));
        System.out.print("foldl((+), \"zero\", " + ListOf.toString(strList) + ") = ");
        
        //length of String
        F2<String,String,String> f = new F2<String,String,String>(){
            @Override
            public String f(String a, String b) {
                return a + " + " + b;
            }
        };
        System.out.println(foldable.foldl(f, "zero", strList)); 

        System.out.print("foldr((+), \"zero\", " + ListOf.toString(strList) + ") = ");
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
