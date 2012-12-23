package org.highj.typeclass.monad;

import org.highj.data.tuple.T0;
import org.highj._;
import org.highj.data.collection.Maybe;
import org.highj.function.F1;
import org.highj.function.repo.Strings;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.highj.data.collection.Maybe.*;

public class FunctorTest {

    private final Functor<µ> functor = monad;

    @Test
    public void testLeft$() {
        //3 <$ Just "x"
        //-- Just 3
        Maybe<String> justX = Just("x");
        Maybe<Integer> three = narrow(functor.left$(3, justX));
        assertEquals("Just(3)", three.toString());

        //3 <$ Nothing
        //-- Nothing
        Maybe<String> nothingString = Nothing();
        Maybe<Integer> nothingInt = narrow(functor.left$(3, nothingString));
        assertEquals("Nothing", nothingInt.toString());
    }

    @Test
    public void testVoidF() {
        //void $ Just "x"
        //-- Just ()
        Maybe<String> justX = Just("x");
        Maybe<T0> justUnit = narrow(functor.voidF(justX));
        assertEquals("Just(())", justUnit.toString());

        //void $ Nothing
        //-- Nothing
        Maybe<String> nothingString = Nothing();
        Maybe<T0> nothingUnit = narrow(functor.voidF(nothingString));
        assertEquals("Nothing", nothingUnit.toString());
    }

    @Test
    public void testFlip() {
        //flip (Just length) "foo"
        //-- Just 3
        Maybe<F1<String, Integer>> justStringLength = Just(Strings.length);
        Maybe<Integer> justThree = narrow(functor.flip(justStringLength, "foo"));
        assertEquals("Just(3)", justThree.toString());
        
        //flip Nothing "foo"
        //-- Nothing
        Maybe<F1<String, Integer>> nothingFn = Nothing();
        Maybe<Integer> nothingInt = narrow(functor.flip(nothingFn, "foo"));
        assertEquals("Nothing", nothingInt.toString());
    }
    
    @Test
    public void testLift() {
        F1<_<µ,String>,_<µ,Integer>> liftedFn = functor.lift(Strings.length);

        //liftM length $ Just "foo"
        //-- Just 3
        Maybe<String> justString = Just("foo");
        Maybe<Integer> justThree = narrow(liftedFn.$(justString));
        assertEquals("Just(3)", justThree.toString());

        //liftM length Nothing
        //-- Nothing
        Maybe<String> nothingString = Nothing();
        Maybe<Integer> nothingInt = narrow(liftedFn.$(nothingString));
        assertEquals("Nothing", nothingInt.toString());
    }
}
