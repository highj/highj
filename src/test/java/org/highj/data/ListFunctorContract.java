package org.highj.data;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.highj.typeclass1.functor.Functor;
import org.highj.typeclass1.functor.FunctorContract;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@Ignore
@RunWith(JUnitQuickcheck.class)
public class ListFunctorContract implements FunctorContract<List.µ> {

    @Override
    public Functor<List.µ> subject() {
        return List.monadPlus;
    }
}
