/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.examples;

import fj.F;
import fj.F2;
import highj._;
import highj.data.OptionMonadPlus;
import highj.data.OptionOf;
import highj.doblock.Do;
import highj.typeclasses.category.Monad;

/**
 *
 * @author DGronau
 */
public class DoTest {
    private Monad<OptionOf> monad = OptionMonadPlus.getInstance();
    
    private F<Integer, _<OptionOf,Integer>> halfIfPossible = new F<Integer, _<OptionOf,Integer>>(){
        @Override
            public _<OptionOf, Integer> f(Integer a) {
                return a % 2 == 0 ? OptionOf.some(a/2) : OptionOf.<Integer>none();
            }
    };

    private F2<Integer, Integer, _<OptionOf,Integer>> minusIfPossible = 
            new F2<Integer, Integer, _<OptionOf,Integer>>(){

        @Override
        public _<OptionOf, Integer> f(Integer a, Integer b) {
            return a - b >= 0 ? OptionOf.some(a - b) : OptionOf.<Integer>none();
        }
    };
    
    private void testSomeSome() {
        _<OptionOf, Integer> result =
                Do.with(monad)
                .act(OptionOf.some("a"))
                .act(OptionOf.some(42))
                .return_();
        System.out.println("do Some(\"a\"); Some(42) --> " + result);
    }

    private void testNoneSome() {
        _<OptionOf, Integer> result =
                Do.with(monad)
                .act(OptionOf.<String>none())
                .act(OptionOf.some(42))
                .return_();
        System.out.println("do None; Some(42) --> " + result);
    }
    
    private void testSomeReturn() {
        _<OptionOf, Integer> result =
                Do.with(monad)
                .act(OptionOf.some("a"))
                .return_(42);
        System.out.println("do Some(\"a\"); return 42 --> " + result);
    }

    private void testNoneReturn() {
        _<OptionOf, Integer> result =
                Do.with(monad)
                .act(OptionOf.<String>none())
                .return_(42);
        System.out.println("do None; return 42 --> " + result);
    }
    
    private void testAssign1() {
        _<OptionOf, Integer> result =
                Do.with(monad)
                .assign1(OptionOf.some(42))
                .return_();
        System.out.println("do v1 <- Some 42; return v1 --> " + result);
    }

    private void testBind1() {
        _<OptionOf, Integer> result =
                Do.with(monad)
                .assign1(OptionOf.some(42))
                .bind1(halfIfPossible)
                .return_();
        System.out.println("do v1 <- Some 42; return (halfIfPossible v1) --> " + result);
    }

    private void testBind12() {
        _<OptionOf, Integer> result =
                Do.with(monad)
                .assign1(OptionOf.some(42))
                .assign2(OptionOf.some(20))
                .bind12(minusIfPossible)
                .return_();
        System.out.println("do v1 <- Some 42; v2 <- 20; return (minusIfPossible v1 v2) --> " + result);
    }
    
    public DoTest() {
        testSomeSome();
        testNoneSome();
        testSomeReturn();
        testNoneReturn();
        testAssign1();
        testBind1();
        testBind12();
    }

    public static void main(String[] args) {
        new DoTest();
    }
}
